package com.rubber.at.tennis.invite.service.controller;

import com.rubber.at.tennis.invite.api.ActivityInviteApplyApi;
import com.rubber.at.tennis.invite.api.dto.ApplyInviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteCodeResponse;
import com.rubber.base.components.util.annotation.NeedLogin;
import com.rubber.base.components.util.result.ResultMsg;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@RestController
@RequestMapping("/invite/apply")
public class InviteApplyController {



    @Resource
    private ActivityInviteApplyApi activityInviteApplyApi;

    /**
     * 新增或者编辑一个邀请
     */
    @NeedLogin
    @PostMapping("/edit")
    public ResultMsg editInvite(@RequestBody ApplyInviteInfoDto dto){
        InviteCodeResponse response = activityInviteApplyApi.saveActivityInviteInfo(dto);
        return ResultMsg.success(response);
    }


    /**
     * 发布邀请
     */
    @NeedLogin
    @PostMapping("/published")
    public ResultMsg published(@RequestBody InviteInfoCodeReq dto){
        InviteCodeResponse response = activityInviteApplyApi.publishInvite(dto);
        return ResultMsg.success(response);
    }


    /**
     * 取消邀请
     */
    @NeedLogin
    @PostMapping("/close")
    public ResultMsg close(@RequestBody InviteInfoCodeReq dto){
        InviteCodeResponse response = activityInviteApplyApi.closeInvite(dto);
        return ResultMsg.success(response);
    }
}

