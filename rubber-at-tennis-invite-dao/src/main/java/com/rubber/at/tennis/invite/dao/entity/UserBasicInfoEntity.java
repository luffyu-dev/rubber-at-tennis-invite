package com.rubber.at.tennis.invite.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.base.components.mysql.plugins.admin.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户基础信息表
 * </p>
 *
 * @author rockyu
 * @since 2022-10-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_basic_info")
public class UserBasicInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 0表示男 1表示女 2表示未知
     */
    private Integer userSex;

    /**
     * 生日
     */
    private Date userBirthday;

    /**
     * 用户地区
     */
    private String userArea;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date modifyTime;

    /**
     * 版本号
     */
    @Version
    private Integer version;


}
