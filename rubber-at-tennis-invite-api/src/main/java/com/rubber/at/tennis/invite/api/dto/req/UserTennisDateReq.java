package com.rubber.at.tennis.invite.api.dto.req;

import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luffyu
 * Created on 2022/11/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserTennisDateReq extends BaseUserSession {

    /**
     * 记录的日期
     * 格式为 yyyy/MM
     */
    private String recordDate;
}
