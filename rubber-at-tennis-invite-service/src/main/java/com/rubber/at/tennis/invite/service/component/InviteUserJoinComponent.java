package com.rubber.at.tennis.invite.service.component;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.api.dto.req.CancelJoinInviteReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteJoinReq;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
            InviteJoinReq req = joinModel.getReq();
            ActivityInviteInfoEntity infoEntity = joinModel.getActivityInviteInfoEntity();
            int oldJoinIndex = infoEntity.getJoinNumber();

            InviteJoinUserEntity userEntity = iInviteJoinUserDal.getInviteJoinUser(req.getInviteCode(), req.getUid());
            if (userEntity != null && InviteJoinStateEnums.SUCCESS.getState().equals(userEntity.getStatus())){
                // 报名重入
                return;
            }

            List<InviteJoinUserEntity> entityList = new ArrayList<>();
            if (userEntity == null){
                userEntity = new InviteJoinUserEntity();
            }
            Date now = new Date();
            userEntity.setJoinUid(req.getUid());
            userEntity.setInviteCode(infoEntity.getInviteCode());
            userEntity.setJoinUserNick(joinModel.getJoinUserInfo().getUserNick());
            userEntity.setJoinUserAvatar(joinModel.getJoinUserInfo().getUserAvatar());
            userEntity.setJoinUserSex(joinModel.getJoinUserInfo().getUserSex());
            userEntity.setDataVersion(req.getUid());
            userEntity.setCreateTime(now);
            userEntity.setUpdateTime(now);
            userEntity.setStatus(InviteJoinStateEnums.SUCCESS.getState());
            entityList.add(userEntity);

            // 报名的人数
            int joinNum = 1 +  joinModel.getReq().getCarryFriendNum();
            List<InviteJoinUserEntity> friendList = new ArrayList<>();
            for (int i=0; i< joinModel.getReq().getCarryFriendNum();i++){
                InviteJoinUserEntity tmpEntity = new InviteJoinUserEntity();
                BeanUtils.copyProperties(userEntity,tmpEntity,"id");
                tmpEntity.setJoinUserNick(userEntity.getJoinUserNick()+"+"+(i+1));
                tmpEntity.setDataVersion(Integer.valueOf(req.getUid() + String.valueOf(i)));
                friendList.add(tmpEntity);
            }
            // 更新版本
            // 批量写入参与用户
            if (!this.updateInviteForJoin(infoEntity, oldJoinIndex, oldJoinIndex+joinNum)
                    || !iInviteJoinUserDal.saveOrUpdate(userEntity)
                    || (CollUtil.isNotEmpty(friendList) &&  !iInviteJoinUserDal.saveBatch(friendList))) {
                throw new RubberServiceException(SysCode.SYSTEM_BUS);
            }
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
    public void cancelJoinInvite(CancelJoinInviteReq req ,ActivityInviteInfoEntity inviteInfoEntity){
        List<InviteJoinUserEntity> joinUserList ;
        if (Integer.valueOf(1).equals(req.getCancelType())){
            joinUserList = iInviteJoinUserDal.getInviteJoinUserByIds(inviteInfoEntity.getInviteCode(), null,req.getCancelJoinUserIds());
        }else {
            joinUserList = iInviteJoinUserDal.getInviteJoinUserByIds(inviteInfoEntity.getInviteCode(), req.getUid(),req.getCancelJoinUserIds());
        }
        if (CollUtil.isEmpty(joinUserList)){
            throw new RubberServiceException(ErrorCodeEnums.USER_NOT_JOINED);
        }
        List<Integer> needUpdataList = joinUserList.stream().map(InviteJoinUserEntity::getId).collect(Collectors.toList());
        int trueCancelSize = needUpdataList.size();
        if (trueCancelSize <= 0){
            return;
        }
        int oldJoinNumber = inviteInfoEntity.getJoinNumber();
        if (this.updateInviteForJoin(inviteInfoEntity, oldJoinNumber, Math.max(oldJoinNumber-trueCancelSize,0))
                && iInviteJoinUserDal.removeByIds(needUpdataList)) {
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
