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
     * 邀请人数
     */
    private Integer inviteNumber;

    /**
     * 已参与的人数
     */
    private String joinNumber;

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

}
