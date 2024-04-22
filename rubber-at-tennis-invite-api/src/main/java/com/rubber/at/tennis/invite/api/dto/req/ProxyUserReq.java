package com.rubber.at.tennis.invite.api.dto.req;

import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;

/**
 * @author luffyu
 * Created on 2024/4/13
 */
@Data
public class ProxyUserReq extends BaseUserSession {

    private Integer proxyUid;
}
