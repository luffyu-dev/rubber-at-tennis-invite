package com.rubber.at.tennis.invite.api;

import com.rubber.at.tennis.invite.api.dto.ActivityInviteDetailDto;
import com.rubber.at.tennis.invite.api.dto.ActivityInviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.InviteJoinUserDto;
import com.rubber.at.tennis.invite.api.dto.req.ActivityInviteQueryReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteTemplateQueryReq;
import com.rubber.base.components.util.result.page.ResultPage;

import java.util.List;

/**
 * @author luffyu
 * Created on 2023/8/2
 */
public interface ActivityInviteQueryApi {

    /**
     * 查询单个活动详情接口
     */
    ActivityInviteDetailDto getDetailInfo(InviteInfoCodeReq req);


    /**
     * 查询单个活动的邀请详情
     */
    List<InviteJoinUserDto> getInviteJoinList(InviteInfoCodeReq req);


    /**
     * 查询推荐的活动信息
     */
    ResultPage<ActivityInviteInfoDto> queryRecommendPage(ActivityInviteQueryReq req);


    /**
     * 查询加入的活动
     */
    ResultPage<ActivityInviteInfoDto> queryUserJoinPage(ActivityInviteQueryReq req);


    /**
     * 查询用户自己的创建的活动
     */
    ResultPage<ActivityInviteInfoDto> queryUserInvite(ActivityInviteQueryReq req);


    /**
     * 查询用户的模版
     * @param req
     * @return
     */
    ResultPage<ActivityInviteInfoDto> queryUserInviteTemplate(InviteTemplateQueryReq req);


    /**
     * 查询官方的模版
     * @param req
     * @return
     */
    ResultPage<ActivityInviteInfoDto> queryOfficialTemplate(ActivityInviteQueryReq req);

}
