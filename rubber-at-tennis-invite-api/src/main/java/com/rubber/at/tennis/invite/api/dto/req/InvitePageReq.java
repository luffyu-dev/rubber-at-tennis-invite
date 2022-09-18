package com.rubber.at.tennis.invite.api.dto.req;

import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvitePageReq extends BaseUserSession {

    private int page = 1;
    private int size = 20;
}
