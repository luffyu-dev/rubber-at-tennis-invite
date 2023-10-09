package com.rubber.at.tennis.invite.api;

import com.rubber.at.tennis.invite.api.dto.ApplyInviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.CancelJoinInviteReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteJoinReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteCodeResponse;

/**
 * @author luffyu
 * Created on 2023/8/2
 */
public interface ActivityInviteApplyApi {


    /**
     * 保存活动邀约信息
     */
    InviteCodeResponse saveActivityInviteInfo(ApplyInviteInfoDto inviteInfoDto);



    /**
     * 发布活动
     */
    InviteCodeResponse publishInvite(InviteInfoCodeReq req);



    /**
     * 取消活动
     */
    InviteCodeResponse closeInvite(InviteInfoCodeReq req);



    /**
     * 加入活动
     * 带多个人参加
     */
    InviteCodeResponse joinInvite(InviteJoinReq req);


    /**
     * 取消加入活动
     */
    InviteCodeResponse cancelJoinInvite(CancelJoinInviteReq req);

}
