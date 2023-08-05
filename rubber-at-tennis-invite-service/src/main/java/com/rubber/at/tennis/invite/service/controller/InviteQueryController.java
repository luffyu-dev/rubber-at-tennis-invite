package com.rubber.at.tennis.invite.service.controller;

import com.rubber.at.tennis.invite.api.ActivityInviteQueryApi;
import com.rubber.at.tennis.invite.api.dto.ActivityInviteDetailDto;
import com.rubber.at.tennis.invite.api.dto.ActivityInviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.req.InvitePageReq;
import com.rubber.base.components.util.annotation.NeedLogin;
import com.rubber.base.components.util.result.ResultMsg;
import com.rubber.base.components.util.result.page.ResultPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@RestController
@RequestMapping("/invite/query")
public class InviteQueryController {

    @Resource
    private ActivityInviteQueryApi activityInviteQueryApi;


    /**
     * 获取邀请详情
     * @param req 当前的请求入参
     * @return
     */
    @PostMapping("/info")
    public ResultMsg getInviteInfo(@RequestBody InviteInfoCodeReq req){
        ActivityInviteDetailDto inviteInfo = activityInviteQueryApi.getDetailInfo(req);
        return ResultMsg.success(inviteInfo);
    }


    /**
     * 获取当前用户的邀请详情
     * @param req 当前的邀请参数
     * @return 返回是否支持邀请
     */
    @NeedLogin
    @PostMapping("/user-list")
    public ResultMsg listPage(@RequestBody InvitePageReq req){
        ResultPage<ActivityInviteInfoDto> inviteInfo = activityInviteQueryApi.queryUserInvite(req);
        return ResultMsg.success(inviteInfo);
    }


}
