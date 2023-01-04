package com.rubber.at.tennis.invite.api.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luffyu
 * Created on 2023/1/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InviteJoinReq extends InviteInfoCodeReq{

    /**
     * 取消类型
     */
    private Integer cancelType;


    /**
     * 参与的uid
     */
    private Integer joinUid;

}
