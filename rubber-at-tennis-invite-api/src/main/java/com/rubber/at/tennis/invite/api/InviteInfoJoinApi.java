package com.rubber.at.tennis.invite.api;

import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteJoinReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteCodeResponse;

/**
 * @author luffyu
 * Created on 2022/8/29
 */
@Deprecated
public interface InviteInfoJoinApi {


    /**
     * 新增一个邀请
     * @param req 当前的请求
     * @return 返回邀请信息
     */
    InviteCodeResponse join(InviteInfoCodeReq req);


    /**
     * 取消邀请
     * @param dto
     * @return
     */
    InviteCodeResponse cancelJoin(InviteJoinReq dto);

}
