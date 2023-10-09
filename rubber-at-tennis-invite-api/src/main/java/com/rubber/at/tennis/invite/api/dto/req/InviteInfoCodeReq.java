package com.rubber.at.tennis.invite.api.dto.req;

import com.rubber.base.components.util.session.BaseLbsUserSession;
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
public class InviteInfoCodeReq extends BaseLbsUserSession {


    /**
     * 邀请code
     */
    private String inviteCode;

    /**
     * 是否查询自己报名的所有用户
     * 携带的朋友
     */
    private boolean querySelfJoin;




}
