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
 * 邀约详情配置表
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_invite_config_field")
public class InviteConfigFieldEntity extends BaseEntity {

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
     * 发起人id
     */
    @TableField("Finvite_field")
    private String inviteField;

    /**
     * 邀请标题
     */
    @TableField("Finvite_value")
    private String inviteValue;

    /**
     * 备注
     */
    @TableField("Fremark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("Fcreate_time")
    private Date createTime;

    /**
     * 状态 10表示正常 20表示被删除
     */
    @TableField("Fstatus")
    private Integer status;

    /**
     * 最后一次更新时间
     */
    @TableField("Fupdate_time")
    private Date updateTime;

    /**
     * 版本号
     */
    @TableField("Fversion")
    private Integer version;


}
