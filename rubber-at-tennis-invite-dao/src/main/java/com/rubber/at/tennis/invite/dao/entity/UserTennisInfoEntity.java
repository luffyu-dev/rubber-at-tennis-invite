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
 * 用户网球训练基础表
 * </p>
 *
 * @author rockyu
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_tennis_info")
public class UserTennisInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "Fid", type = IdType.AUTO)
    private Integer id;

    /**
     * uid
     */
    @TableField("Fuid")
    private Integer uid;

    /**
     * 能力水平
     */
    @TableField("Fntrp")
    private String ntrp;

    /**
     * 开始打球的时间
     */
    @TableField("Fstart_play_date")
    private Date startPlayDate;

    /**
     * 能力矩阵
     */
    @TableField("Flevel_matrix")
    private String levelMatrix;

    /**
     * 总训练时长(分钟)
     */
    @TableField("Fall_train_hours")
    private Integer allTrainHours;

    /**
     * 本周训练时长（分钟）
     */
    @TableField("Fweek_train_hours")
    private Integer weekTrainHours;

    /**
     * 备注
     */
    @TableField("Fremark")
    private String remark;

    /**
     * 版本号
     */
    @TableField("Fversion")
    @Version
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


}
