package com.rubber.at.tennis.invite.api.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author luffyu
 * Created on 2023/10/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CancelJoinInviteReq extends InviteInfoCodeReq{


    /**
     * 用户取消的uid
     */
    private List<Integer> cancelJoinUserIds;


    /**
     * 取消类型
     * 0表示自己取消 1表示管理员取消
     */
    private Integer cancelType;


}
