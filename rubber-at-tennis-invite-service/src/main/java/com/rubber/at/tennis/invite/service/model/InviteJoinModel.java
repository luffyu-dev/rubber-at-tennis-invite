package com.rubber.at.tennis.invite.service.model;

import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteJoinReq;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.UserBasicInfoEntity;
import lombok.Data;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@Data
public class InviteJoinModel {

    /**
     * 邀请对象数据
     */
    private InviteJoinReq req;

    /**
     * 邀请人对象
     */
    private ActivityInviteInfoEntity activityInviteInfoEntity;


    /**
     * 参与人的详细信息
     */
    private UserBasicInfoEntity joinUserInfo;


    public InviteJoinModel() {
    }



    public InviteJoinModel(InviteJoinReq req, ActivityInviteInfoEntity infoEntity,UserBasicInfoEntity joinUserInfo) {
        this.req = req;
        this.activityInviteInfoEntity = infoEntity;
        this.joinUserInfo = joinUserInfo;
    }
}
