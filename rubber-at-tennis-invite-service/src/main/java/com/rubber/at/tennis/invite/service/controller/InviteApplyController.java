package com.rubber.at.tennis.invite.service.controller;

import cn.hutool.core.util.StrUtil;
import com.rubber.at.tennis.invite.api.InviteInfoApplyApi;
import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
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
    private InviteInfoApplyApi inviteInfoApplyApi;

    /**
     * 新增或者编辑一个邀请
     */
    @NeedLogin
    @PostMapping("/edit")
    public ResultMsg editInvite(@RequestBody InviteInfoDto dto){
        InviteCodeResponse response;
        if (StrUtil.isEmpty(dto.getInviteCode())){
            response = inviteInfoApplyApi.addInviteInfo(dto);
        }else {
            response = inviteInfoApplyApi.editInviteInfo(dto);
        }
        return ResultMsg.success(response);
    }


    /**
     * 发布邀请
     */
    @NeedLogin
    @PostMapping("/published")
    public ResultMsg published(@RequestBody InviteInfoCodeReq dto){
        InviteCodeResponse response = inviteInfoApplyApi.published(dto);
        return ResultMsg.success(response);
    }


    /**
     * 取消邀请
     */
    @NeedLogin
    @PostMapping("/close")
    public ResultMsg close(@RequestBody InviteInfoCodeReq dto){
        InviteCodeResponse response = inviteInfoApplyApi.closeInvite(dto);
        return ResultMsg.success(response);
    }
}

