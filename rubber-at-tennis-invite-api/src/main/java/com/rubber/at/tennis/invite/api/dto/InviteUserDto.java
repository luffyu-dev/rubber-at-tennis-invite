package com.rubber.at.tennis.invite.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 邀约详情表
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
@Data
public class InviteUserDto  {


    /**
     * 外表关联的uid
     */
    private Integer uid;

    /**
     * 用户昵称
     */
    private String userNick;

    /**
     * 用户简介
     */
    private String userMotto;

    /**
     * 用户头像地址
     */
    private String userAvatar;


    /**
     * 关联的数据版本
     */
    private Integer dataVersion;

    /**
     * 备注
     */
    private String remark;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 状态 10表示报名成功 20表示退出
     */
    private Integer status;


    /**
     * 参与时间
     */
    private Date joinTime;

}
