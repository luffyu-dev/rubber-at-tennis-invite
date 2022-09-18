package com.rubber.at.tennis.invite.service.model;

import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
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
    private InviteInfoEntity infoEntity;




    public InviteJoinModel() {
    }

    public InviteJoinModel(InviteInfoCodeReq req, InviteInfoEntity infoEntity) {
        this.req = req;
        this.infoEntity = infoEntity;
    }
}
