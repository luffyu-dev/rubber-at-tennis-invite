package com.rubber.at.tennis.invite.api;

import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.base.components.util.session.BaseLbsUserSession;

/**
 * @author luffyu
 * Created on 2023/8/2
 */
public interface ActivityInviteQueryApi {

    /**
     * 查询单个活动详情接口
     */
    InviteInfoDto getDetailInfo(InviteInfoCodeReq req);


    /**
     * 查询单个活动的邀请详情
     */
    void getInviteJoinList(InviteInfoCodeReq req);


    /**
     * 查询推荐的活动信息
     */
    void queryRecommendPage();


    /**
     * 查询加入的活动
     */
    void queryJoinPage(BaseLbsUserSession session);


    /**
     * 查询用户自己的创建的活动
     */
    void queryUserInvite(BaseLbsUserSession session);

}
