package com.rubber.at.tennis.invite.api.dto;

import com.alibaba.fastjson.JSONObject;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 邀约分页展示的相关元素信息
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityInviteInfoDto extends InviteInfoCodeReq {


    /**
     * 邀请标题
     */
    private String inviteTitle;

    /**
     * 邀请人主图
     */
    private String inviteHomeImg;

    /**
     * 邀请相关图片
     */
    private String inviteImg;

    /**
     * 总邀请人数
     */
    private Integer inviteNumber;

    /**
     * 已参与的人数
     */
    private Integer joinNumber;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 报名截止日期
     */
    private Integer joinDeadlineType;

    /**
     * 截止时间
     */
    private Date joinDeadline;


    /**
     * 关联的球场code
     */
    private String courtCode;

    /**
     * 场地所在省
     */
    private String courtProvince;

    /**
     * 球场所在市
     */
    private String courtCity;

    /**
     * 球场所在区
     */
    private String courtDistrict;

    /**
     * 球场详细地址
     */
    private String courtAddress;

    /**
     * 球场场地所在纬度
     */
    private String courtLatitude;

    /**
     * 球场场地所在经度
     */
    private String courtLongitude;

    /**
     * 备注
     */
    private String remark;


    /**
     * 状态 10表示待发布 20表示已发布，30表示取消
     */
    private Integer status;

    /**
     * 参与状态 10表示报名中 20表示名额已满 30表示报名截止 40活动结束
     */
    private Integer joinStatus;

    /**
     * 邀约状态描述
     */
    private String inviteStatusDesc;


    /**
     * 邀请的时间描述
     */
    private String inviteTimeDesc;


    /**
     * 邀请的天时间猫叔
     */
    private String inviteTimeWeekDesc;


    /**
     * lbs的定位距离
     * 单位是米
     */
    private Integer lbsDistance = 0;

}
