package com.rubber.at.tennis.invite.api.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 邀约详情表
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplyInviteInfoDto extends InviteInfoCodeReq {



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
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date endTime;

    /**
     * 开启结束时间描述
     */
    private String startEndTimeDesc;

    /**
     * 报名截止日期
     */
    private Integer joinDeadlineType;
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
     * 是否公开限制
     * 0:公开所有人可见
     * 1:仅邀请可见
     */
    private Integer showLimit;



    /**
     * 是否自动发布
     * 0表示否 1表示是
     */
    private Integer autoPublished;


    /**
     * 配置字段
     */
    private JSONObject configField;

}
