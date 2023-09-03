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
public class InviteTemplateQueryReq extends ActivityInviteQueryReq{


    /**
     * 用户模版类型
     * 0表示自己创建的 1表示自己参与的
     */
   private Integer userTemplate;

}
