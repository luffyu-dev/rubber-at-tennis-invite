package com.rubber.at.tennis.invite.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 邀约人详情表
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Data
public class InviteJoinUserDto  {


    /**
     * 自增id
     */
    private Integer id;

    /**
     * 邀请code
     */
    private String inviteCode;

    /**
     * 参与人id
     */
    private Integer joinUid;

    /**
     * 参与人id昵称
     */
    private String joinUserNick;


    /**
     * 参与人头像
     */
    private String joinUserAvatar;


    /**
     * 关联的数据版本
     */
    private Integer dataVersion;

    /**
     * 报名时间
     */
    private Date createTime;


    /**
     * 参与日期
     */
    private String joinTimeDesc;



}
