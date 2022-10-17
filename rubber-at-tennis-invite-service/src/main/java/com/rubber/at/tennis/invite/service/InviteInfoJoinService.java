package com.rubber.at.tennis.invite.service;

import com.rubber.at.tennis.invite.api.InviteInfoJoinApi;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteCodeResponse;
import com.rubber.at.tennis.invite.api.enums.InviteInfoStateEnums;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.at.tennis.invite.service.common.exception.ErrorCodeEnums;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.at.tennis.invite.service.component.InviteApplyComponent;
import com.rubber.at.tennis.invite.service.component.InviteJoinComponent;
import com.rubber.at.tennis.invite.service.component.InviteQueryComponent;
import com.rubber.at.tennis.invite.service.model.InviteJoinModel;
import com.rubber.base.components.util.result.code.SysCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@Slf4j
@Service
public class InviteInfoJoinService implements InviteInfoJoinApi {

    @Autowired
    private InviteQueryComponent inviteQueryComponent;

    @Autowired
    private InviteApplyComponent inviteApplyComponent;

    @Autowired
    private InviteJoinComponent inviteJoinComponent;


    /**
     * 新增一个邀请
     *
     * @param req 当前的请求
     * @return 返回邀请信息
     */
    @Override
    public InviteCodeResponse join(InviteInfoCodeReq req) {
        // 获取邀请详情
        InviteInfoEntity infoEntity = inviteQueryComponent.getAndCheck(req.getInviteCode());
        // 校验是否已经完成
        this.doCheckInviteInfo(infoEntity);

        // 数据邀请对象
        InviteJoinModel joinModel = new InviteJoinModel(req,infoEntity);
        inviteJoinComponent.joinInvite(joinModel);
        // 返回
        return new InviteCodeResponse(req.getInviteCode());
    }

    /**
     * 参与人取消报名
     *
     * @param req
     * @return
     */
    @Override
    public InviteCodeResponse cancelJoin(InviteInfoCodeReq req) {
        // 校验活动是否存在
        InviteInfoEntity inviteInfoEntity = inviteQueryComponent.getAndCheck(req.getInviteCode());
        // 校验是否可以编辑
        if (InviteInfoStateEnums.CLOSE.getState().equals(inviteInfoEntity.getStatus())){
            throw new RubberServiceException(ErrorCodeEnums.INVITE_CLOSE);
        }
        inviteJoinComponent.cancelJoinInvite(req,inviteInfoEntity);
        return new InviteCodeResponse(req.getInviteCode());
    }


    /**
     * 校验邀请事件是否可参与
     * @param infoEntity 当前的邀请对象
     */
    private void doCheckInviteInfo(InviteInfoEntity infoEntity){
        // 校验是否已经完成
        if (InviteInfoStateEnums.CLOSE.getState().equals(infoEntity.getStatus())){
            throw new RubberServiceException(ErrorCodeEnums.INVITE_CLOSE);
        }
        // 校验是否已经完成
        if (InviteInfoStateEnums.FINISHED.getState().equals(infoEntity.getStatus())){
            throw new RubberServiceException(ErrorCodeEnums.INVITE_FINISHED);
        }
        if (infoEntity.getJoinNumber() >= infoEntity.getInviteNumber()){
            // 邀请人员已完成
            throw new RubberServiceException(ErrorCodeEnums.USER_IS_FULL);
        }
        Date now = new Date();
        if (now.getTime() > infoEntity.getJoinDeadline().getTime()){
            throw new RubberServiceException(ErrorCodeEnums.INVITE_TIME_JOIN_DEADLINE);
        }

    }




}





