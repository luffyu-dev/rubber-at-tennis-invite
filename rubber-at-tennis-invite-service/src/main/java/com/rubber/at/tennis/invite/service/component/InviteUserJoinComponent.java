package com.rubber.at.tennis.invite.service.component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.enums.InviteJoinStateEnums;
import com.rubber.at.tennis.invite.dao.dal.IActivityInviteInfoDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteJoinUserDal;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteJoinUserEntity;
import com.rubber.at.tennis.invite.service.common.exception.ErrorCodeEnums;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.at.tennis.invite.service.model.InviteJoinModel;
import com.rubber.base.components.util.result.code.SysCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@Slf4j
@Component
public class InviteUserJoinComponent {

    @Autowired
    private IInviteJoinUserDal iInviteJoinUserDal;

    @Autowired
    private IActivityInviteInfoDal iActivityInviteInfoDal;


    /**
     * 用户报名
     * @param joinModel
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    public void joinInvite(InviteJoinModel joinModel){
        try {
            InviteInfoCodeReq req = joinModel.getReq();
            ActivityInviteInfoEntity infoEntity = joinModel.getActivityInviteInfoEntity();
            int oldJoinIndex = infoEntity.getJoinNumber();

            InviteJoinUserEntity userEntity = iInviteJoinUserDal.getInviteJoinUser(req.getInviteCode(), req.getUid());
            if (userEntity != null && InviteJoinStateEnums.SUCCESS.getState().equals(userEntity.getStatus())){
                // 报名重入
                return;
            }
            if (userEntity == null){
                userEntity = new InviteJoinUserEntity();
            }
            Date now = new Date();
            userEntity.setJoinUid(req.getUid());
            userEntity.setInviteCode(infoEntity.getInviteCode());
            userEntity.setDataVersion(oldJoinIndex + 1);
            userEntity.setCreateTime(now);
            userEntity.setUpdateTime(now);
            userEntity.setStatus(InviteJoinStateEnums.SUCCESS.getState());
            if (this.updateInviteForJoin(infoEntity, oldJoinIndex, userEntity.getDataVersion())
                    && iInviteJoinUserDal.saveOrUpdate(userEntity)) {
                return ;
            }
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }catch (Exception e){
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }
    }

    /**
     * 取消报名
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    public void cancelJoinInvite(Integer uid,ActivityInviteInfoEntity inviteInfoEntity){
        // 建议用户是否有参与
        InviteJoinUserEntity joinUser = iInviteJoinUserDal.getInviteJoinUser(inviteInfoEntity.getInviteCode(), uid);
        if (joinUser == null){
            throw new RubberServiceException(ErrorCodeEnums.USER_NOT_JOINED);
        }
        if (InviteJoinStateEnums.CLOSE.getState().equals(joinUser.getStatus())){
            // 已取消
            return;
        }
        joinUser.setUpdateTime(new Date());
        joinUser.setStatus(InviteJoinStateEnums.CLOSE.getState());
        joinUser.setDataVersion(joinUser.getJoinUid());
        int oldJoinNumber = inviteInfoEntity.getJoinNumber();
        if (this.updateInviteForJoin(inviteInfoEntity, oldJoinNumber, Math.max(oldJoinNumber-1,0))
                && iInviteJoinUserDal.updateById(joinUser)) {
            return;
        }
        throw new RubberServiceException(SysCode.SYSTEM_BUS);
    }




    private boolean updateInviteForJoin(ActivityInviteInfoEntity inviteInfoEntity,Integer oldJoinIndex,Integer newJoinIndex){
        Date now = new Date();
        inviteInfoEntity.setUpdateTime(now);
        inviteInfoEntity.setJoinNumber(newJoinIndex);
        LambdaQueryWrapper<ActivityInviteInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ActivityInviteInfoEntity::getId,inviteInfoEntity.getId())
                .eq(ActivityInviteInfoEntity::getJoinNumber,oldJoinIndex)
                .ge(ActivityInviteInfoEntity::getInviteNumber,newJoinIndex);
        return iActivityInviteInfoDal.update(inviteInfoEntity,lqw);
    }
}
