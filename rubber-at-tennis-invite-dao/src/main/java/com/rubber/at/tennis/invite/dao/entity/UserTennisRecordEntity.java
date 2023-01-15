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
 * 用户网球训练记录
 * </p>
 *
 * @author rockyu
 * @since 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_tennis_record")
public class UserTennisRecordEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "Fid", type = IdType.AUTO)
    private Integer id;

    /**
     * 发起人id
     */
    @TableField("Fuid")
    private Integer uid;

    /**
     * 记录名称
     */
    @TableField("Frecord_title")
    private String recordTitle;

    /**
     * 记录类型 1表示训练 2表示比赛
     */
    @TableField("Frecord_type")
    private Integer recordType;

    /**
     * 训练日期 天维度
     */
    @TableField("Frecord_date")
    private String recordDate;

    /**
     * 训练启始时间
     */
    @TableField("Frecord_start")
    private Date recordStart;

    /**
     * 训练结束时间
     */
    @TableField("Frecord_end")
    private Date recordEnd;

    /**
     * 训练时长，单位为分钟
     */
    @TableField("Frecord_duration")
    private Integer recordDuration;

    /**
     * 备注
     */
    @TableField("Fremark")
    private String remark;

    /**
     * 版本号
     */
    @TableField("Fversion")
    private Integer version;

    /**
     * 状态 1表示正常 0表示废弃
     */
    @TableField("Fstatus")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("Fcreate_time")
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    @TableField("Fupdate_time")
    private Date updateTime;

    /**
     * 业务id
     */
    @TableField("Fbiz_id")
    private String bizId;

}
