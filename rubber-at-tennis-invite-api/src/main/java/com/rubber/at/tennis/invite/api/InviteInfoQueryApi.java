package com.rubber.at.tennis.invite.api;

import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InvitePageReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteInfoResponse;
import com.rubber.base.components.util.result.page.ResultPage;

/**
 * @author luffyu
 * Created on 2022/8/29
 */
public interface InviteInfoQueryApi {


    /**
     * 获取一个邀请详情
     * @param req 当前的请求如此
     * @return 返回一个邀请的dto
     */
    InviteInfoResponse getInviteInfo(InviteInfoCodeReq req);


    /**
     * 查询当前用户的登录session
     * @param req 当前的session请求
     * @return 返回是否登录
     */
    ResultPage<InviteInfoDto> listPage(InvitePageReq req);
}
