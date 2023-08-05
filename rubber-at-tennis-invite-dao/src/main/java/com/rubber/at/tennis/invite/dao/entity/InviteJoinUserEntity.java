package com.rubber.at.tennis.invite.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.base.components.mysql.plugins.admin.bean.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 邀约人详情表
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_invite_join_user")
public class InviteJoinUserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "Fid", type = IdType.AUTO)
    private Integer id;

    /**
     * 邀请code
     */
    @TableField("Finvite_code")
    private String inviteCode;

    /**
     * 参与人id
     */
    @TableField("Fjoin_uid")
    private Integer joinUid;

    /**
     * 参与人id昵称
     */
    @TableField("Fjoin_user_nick")
    private String joinUserNick;


    /**
     * 参与人头像
     */
    @TableField("Fjoin_user_avatar")
    private String joinUserAvatar;


    /**
     * 关联的数据版本
     */
    @TableField("Fdata_version")
    private Integer dataVersion;

    /**
     * 版本号
     */
    @TableField("Fversion")
    private Integer version;

    /**
     * 状态 10表示报名成功 20表示退出
     */
    @TableField("Fstatus")
    private Integer status;

    /**
     * 报名时间
     */
    @TableField("Fcreate_time")
    private Date createTime;

    /**
     * 报名时间
     */
    @TableField("Fupdate_time")
    private Date updateTime;


}
