package com.rubber.at.tennis.invite.service.controller;

import com.rubber.at.tennis.invite.api.InviteInfoJoinApi;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.base.components.util.annotation.NeedLogin;
import com.rubber.base.components.util.result.ResultMsg;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@RestController
@RequestMapping("/invite/join")
public class InviteJoinController {

    @Resource
    private InviteInfoJoinApi inviteInfoJoinApi;

    @NeedLogin
    @PostMapping("/submit")
    public ResultMsg joinInvite(@RequestBody InviteInfoCodeReq dto){
        inviteInfoJoinApi.join(dto);
        return ResultMsg.success();
    }

}

