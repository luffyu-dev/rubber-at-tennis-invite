package com.rubber.at.tennis.invite.service.controller;

import com.rubber.at.tennis.invite.api.UserTennisApi;
import com.rubber.at.tennis.invite.api.dto.UserModifyTennisDto;
import com.rubber.at.tennis.invite.api.dto.req.UserTennisDateReq;
import com.rubber.base.components.util.annotation.NeedLogin;
import com.rubber.base.components.util.result.ResultMsg;
import com.rubber.base.components.util.session.BaseUserSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2022/11/27
 */
@RestController
@RequestMapping("/u/tennis")
public class UserTennisController {

    @Resource
    private UserTennisApi userTennisApi;


    /**
     * 查询用户的信息
     * @param baseUserSession  用户信息
     */
    @NeedLogin
    @PostMapping("/info")
    public ResultMsg getUserTennisInfo(@RequestBody  BaseUserSession baseUserSession){
        return ResultMsg.success(userTennisApi.getUserTennisInfo(baseUserSession));
    }


    /**
     * 更新用户的基本信息
     * @param dto  用户信息
     */
    @NeedLogin
    @PostMapping("/up")
    public ResultMsg getUserTennisInfo(@RequestBody UserModifyTennisDto dto){
        userTennisApi.updateUserTennis(dto);
        return ResultMsg.success();
    }




    /**
     * 查询用户的信息
     * @param req  用户信息
     */
    @NeedLogin
    @PostMapping("/date")
    public ResultMsg queryDate(@RequestBody UserTennisDateReq req){
        return ResultMsg.success(userTennisApi.queryAtTennisDate(req));
    }


}
