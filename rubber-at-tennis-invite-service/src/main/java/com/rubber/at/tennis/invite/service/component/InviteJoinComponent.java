package com.rubber.at.tennis.invite.service.component;

import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
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
     * 邀请好友
     * @param joinModel
     */
    public void joinInvite(InviteJoinModel joinModel){
        try {
            InviteInfoCodeReq req = joinModel.getReq();
            InviteInfoEntity infoEntity = joinModel.getInfoEntity();
            int oldJoinIndex = infoEntity.getJoinNumber();

            Date now = new Date();
            InviteUserEntity userEntity = new InviteUserEntity();
            userEntity.setJoinUid(req.getUid());
            userEntity.setInviteCode(infoEntity.getInviteCode());
            userEntity.setDataVersion(oldJoinIndex + 1);
            userEntity.setCreateTime(now);
            userEntity.setUpdateTime(now);
            userEntity.setStatus(10);
            userEntity.setVersion(0);
            if (inviteApplyComponent.updateInviteForJoin(infoEntity, oldJoinIndex, userEntity.getDataVersion()) && iInviteUserDal.save(userEntity)) {
                return;
            }
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }catch (Exception e){
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }
    }


}
