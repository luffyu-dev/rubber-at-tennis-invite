package com.rubber.at.tennis.invite.service.model;

import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
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
    private InviteInfoCodeReq req;

    /**
     * 邀请人对象
     */
    private ActivityInviteInfoEntity activityInviteInfoEntity;



    public InviteJoinModel() {
    }



    public InviteJoinModel(InviteInfoCodeReq req, ActivityInviteInfoEntity infoEntity) {
        this.req = req;
        this.activityInviteInfoEntity = infoEntity;
    }
}
