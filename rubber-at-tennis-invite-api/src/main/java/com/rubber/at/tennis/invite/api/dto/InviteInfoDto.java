package com.rubber.at.tennis.invite.api.dto;

import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class InviteInfoDto extends BaseUserSession {


    /**
     * 邀请code
     */
    private String inviteCode;

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
     * 已报名人数
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
     * 状态 10表示初始化 20表示已发布，30表示已完成
     */
    private Integer status;
    private String statusDesc;



}
