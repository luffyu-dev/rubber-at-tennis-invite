package com.rubber.at.tennis.invite.api;

import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteCodeResponse;

/**
 * @author luffyu
 * Created on 2022/8/29
 */
public interface InviteInfoApplyApi {


    /**
     * 新增一个邀请
     * @param dto 当前的请求
     * @return 返回邀请信息
     */
    InviteCodeResponse addInviteInfo(InviteInfoDto dto);



    /**
     * 修改一个邀请
     * @param dto 当前的请求
     * @return 返回邀请信息
     */
    InviteCodeResponse editInviteInfo(InviteInfoDto dto);


    /**
     * 发布邀请
     */
    InviteCodeResponse published(InviteInfoCodeReq dto);


    /**
     * 关闭邀请
     */
    InviteCodeResponse closeInvite(InviteInfoCodeReq dto);


}
