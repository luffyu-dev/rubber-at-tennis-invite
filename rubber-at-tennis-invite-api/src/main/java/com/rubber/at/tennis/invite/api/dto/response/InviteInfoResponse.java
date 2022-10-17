package com.rubber.at.tennis.invite.api.dto.response;

import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.InviteUserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InviteInfoResponse extends InviteInfoDto {

    /**
     * 发起人的用户信息
     */
    private InviteUserDto sponsorInfo;

    /**
     * 关联的用户信息
     */
    private List<InviteUserDto>  joinUserList;


}
