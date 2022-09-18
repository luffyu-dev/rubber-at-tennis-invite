package com.rubber.at.tennis.invite.api.dto.req;

import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 邀约详情表
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InviteInfoCodeReq extends BaseUserSession {


    /**
     * 邀请code
     */
    private String inviteCode;




}
