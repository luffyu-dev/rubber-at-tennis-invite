package com.rubber.at.tennis.invite.api;

import cn.hutool.db.PageResult;
import com.rubber.at.tennis.invite.api.dto.ActivityInviteDetailDto;
import com.rubber.at.tennis.invite.api.dto.ActivityInviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.ApplyInviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.InviteJoinUserDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.base.components.util.result.page.ResultPage;
import com.rubber.base.components.util.session.BaseLbsUserSession;

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
    ResultPage<ActivityInviteInfoDto> queryRecommendPage();


    /**
     * 查询加入的活动
     */
    ResultPage<ActivityInviteInfoDto> queryJoinPage(BaseLbsUserSession session);


    /**
     * 查询用户自己的创建的活动
     */
    ResultPage<ActivityInviteInfoDto> queryUserInvite(BaseLbsUserSession session);

}
