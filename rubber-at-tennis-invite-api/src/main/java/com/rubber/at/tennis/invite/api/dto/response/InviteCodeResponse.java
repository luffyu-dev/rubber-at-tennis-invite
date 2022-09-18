package com.rubber.at.tennis.invite.api.dto.response;

import lombok.Data;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@Data
public class InviteCodeResponse  {

    /**
     * 邀请code
     */
    private String inviteCode;


    public InviteCodeResponse() {
    }

    public InviteCodeResponse(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
