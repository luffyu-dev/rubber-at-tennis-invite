package com.rubber.at.tennis.invite.api.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
public class ActivityInviteDetailDto extends ActivityInviteInfoDto {


    /**
     * 已参与的用户信息
     */
    private List<InviteJoinUserDto> joinedUserList;



    /**
     * 配置字段
     */
    private JSONObject configField;

}
