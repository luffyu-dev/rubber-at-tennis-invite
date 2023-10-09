package com.rubber.at.tennis.invite.api.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rockyu
 * @version 1.0
 * @description <p><p>
 * @ClassName ActivityInviteQueryReq
 * @date 2023/8/7 20:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityInviteQueryReq extends InviteInfoCodeReq{

    /**
     * 分页
     */
    private int page = 1;

    /**
     * 分页
     */
    private int size = 20;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 关联的球场code
     */
    private String courtCode;

    /**
     * 排序字段
     */
    private String orderByType;


    private String searchTime;


    private String searchValue;


    /**
     * 是否公开限制
     * 0:公开所有人可见
     * 1:仅邀请可见
     */
    private Integer showLimit;
}
