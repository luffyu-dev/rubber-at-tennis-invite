package com.rubber.at.tennis.invite.service.component;

import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.enums.InviteJoinStateEnums;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.at.tennis.invite.service.common.exception.ErrorCodeEnums;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.at.tennis.invite.service.model.InviteJoinModel;
import com.rubber.base.components.util.result.code.SysCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@Slf4j
@Component
public class InviteJoinComponent {

    @Autowired
    private IInviteUserDal iInviteUserDal;

    @Autowired
    private InviteApplyComponent inviteApplyComponent;

    /**
     * 用户报名
     * @param joinModel
     */
    public void joinInvite(InviteJoinModel joinModel){
        try {
            InviteInfoCodeReq req = joinModel.getReq();
            InviteInfoEntity infoEntity = joinModel.getInfoEntity();
            int oldJoinIndex = infoEntity.getJoinNumber();

            InviteUserEntity userEntity = iInviteUserDal.getInviteJoinUser(req.getInviteCode(), req.getUid());
            if (userEntity != null && InviteJoinStateEnums.SUCCESS.getState().equals(userEntity.getStatus())){
                // 报名重入
                return;
            }
            if (userEntity == null){
                userEntity = new InviteUserEntity();
            }
            Date now = new Date();
            userEntity.setJoinUid(req.getUid());
            userEntity.setInviteCode(infoEntity.getInviteCode());
            userEntity.setDataVersion(oldJoinIndex + 1);
            userEntity.setCreateTime(now);
            userEntity.setUpdateTime(now);
            userEntity.setStatus(InviteJoinStateEnums.SUCCESS.getState());
            if (inviteApplyComponent.updateInviteForJoin(infoEntity, oldJoinIndex, userEntity.getDataVersion())
                    && iInviteUserDal.saveOrUpdate(userEntity)) {
                return;
            }
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }catch (Exception e){
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }
    }


    /**
     * 取消报名
     */
    public void cancelJoinInvite(Integer uid,InviteInfoEntity inviteInfoEntity){
        // 建议用户是否有参与
        InviteUserEntity joinUser = iInviteUserDal.getInviteJoinUser(inviteInfoEntity.getInviteCode(), uid);
        if (joinUser == null){
            throw new RubberServiceException(ErrorCodeEnums.USER_NOT_JOINED);
        }
        joinUser.setUpdateTime(new Date());
        joinUser.setStatus(InviteJoinStateEnums.CLOSE.getState());
        joinUser.setDataVersion(joinUser.getJoinUid());
        int oldJoinNumber = inviteInfoEntity.getJoinNumber();
        if (inviteApplyComponent.updateInviteForJoin(inviteInfoEntity, oldJoinNumber, Math.max(oldJoinNumber-1,0))
                && iInviteUserDal.updateById(joinUser)) {
            return;
        }
        throw new RubberServiceException(SysCode.SYSTEM_BUS);
    }
}
