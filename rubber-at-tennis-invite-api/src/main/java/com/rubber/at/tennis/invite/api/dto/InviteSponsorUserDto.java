package com.rubber.at.tennis.invite.api.dto;

import lombok.Data;

/**
 * @author luffyu
 * Created on 2023/8/9
 */
@Data
public class InviteSponsorUserDto {

    /**
     * uid
     */
    private Integer uid;

    /**
     * 用户昵称
     */
    private String userNick;

    /**
     * 用户头像地址
     */
    private String userAvatar;



    /**
     * 0表示男 1表示女 2表示未知
     */
    private Integer userSex;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 用户微信号
     */
    private String userWx;

    /**
     * 联系人信息补充标记
     */
    private Integer contactFlag;
}
