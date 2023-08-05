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
 * 邀约表
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_activity_invite_info")
public class ActivityInviteInfoEntity extends BaseEntity {

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
    @TableField("Fuid")
    private Integer uid;

    /**
     * 发起人名称
     */
    @TableField("Fuser_name")
    private String userName;

    /**
     * 邀请标题
     */
    @TableField("Finvite_title")
    private String inviteTitle;

    /**
     * 邀请人主图
     */
    @TableField("Finvite_home_img")
    private String inviteHomeImg;

    /**
     * 邀请相关图片
     */
    @TableField("Finvite_img")
    private String inviteImg;

    /**
     * 邀请人数
     */
    @TableField("Finvite_number")
    private Integer inviteNumber;

    /**
     * 已参与人数
     */
    @TableField("Fjoin_number")
    private Integer joinNumber;

    /**
     * 报名截止时间
     */
    @TableField("Fjoin_deadline")
    private Date joinDeadline;

    /**
     * 开始时间
     */
    @TableField("Fstart_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField("Fend_time")
    private Date endTime;

    /**
     * 排序优先级
     */
    @TableField("Fseq")
    private Integer seq;

    /**
     * 关联的球场code
     */
    @TableField("Fcourt_code")
    private String courtCode;

    /**
     * 场地所在省
     */
    @TableField("Fcourt_province")
    private String courtProvince;

    /**
     * 球场所在市
     */
    @TableField("Fcourt_city")
    private String courtCity;

    /**
     * 球场所在区
     */
    @TableField("Fcourt_district")
    private String courtDistrict;

    /**
     * 球场详细地址
     */
    @TableField("Fcourt_address")
    private String courtAddress;

    /**
     * 球场场地所在纬度
     */
    @TableField("Fcourt_latitude")
    private String courtLatitude;

    /**
     * 球场场地所在经度
     */
    @TableField("Fcourt_longitude")
    private String courtLongitude;

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
     * 状态 10表示待发布 20表示已发布，30表示取消
     */
    @TableField("Fstatus")
    private Integer status;

    /**
     * 参与状态 10表示报名中 20表示名额已满 30表示报名截止 40活动结束
     */
    @TableField("Fjoin_status")
    private Integer joinStatus;

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
