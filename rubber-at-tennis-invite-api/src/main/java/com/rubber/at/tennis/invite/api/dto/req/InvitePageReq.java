package com.rubber.at.tennis.invite.api.dto.req;

import com.rubber.base.components.util.session.BaseLbsUserSession;
import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvitePageReq extends BaseLbsUserSession {

    private int page = 1;
    private int size = 20;

    /**
     * 查询类型
     * 0表示 自己创建的
     * 1表示 自己参与的
     */
    private Integer searchType = 0;

    /**
     * 搜索的uid
     */
    private Integer queryUid;


    /**
     * 搜索条件
     */
    private String searchValue;


    /**
     * 调用位置
     */
    private String invoker;
}
