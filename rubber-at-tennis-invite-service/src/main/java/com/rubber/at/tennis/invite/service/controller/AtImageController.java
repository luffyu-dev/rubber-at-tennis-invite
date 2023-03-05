package com.rubber.at.tennis.invite.service.controller;

import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.req.InvitePageReq;
import com.rubber.at.tennis.invite.service.AtImageService;
import com.rubber.base.components.util.annotation.NeedLogin;
import com.rubber.base.components.util.result.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luffyu
 * Created on 2023/3/5
 */
@Slf4j
@RestController
@RequestMapping("/invite/img")
public class AtImageController {


    @Autowired
    private AtImageService atImageService;

    /**
     * 报名参与
     */
    @NeedLogin
    @PostMapping("/recommend")
    public ResultMsg joinInvite(@RequestBody InvitePageReq req){
        return ResultMsg.success(atImageService.queryInviteRecommendImg(req));
    }
}
