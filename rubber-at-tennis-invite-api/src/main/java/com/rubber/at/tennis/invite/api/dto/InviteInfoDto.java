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
     * 状态 10表示初始化 20表示已发布，30表示已完成
     */
    private Integer status;
    private String statusDesc;


    /**
     * 高级设置
     * 1表示设置 0表示没有
     */
    private Integer majorSettings = 0;


    /**
     * 单打 / 双打
     */
    private String playType;


    /**
     * 球员水平要求
     */
    private String playerNtrp;


    /**
     * 免责声明
     * 1表示开启 0表示没有
     */
    private Integer disclaimerFlag = 0;
    private String disclaimerAdditional;


    /**
     * 人均费用
     * 费用类型 和 人均费用
     */
    private Integer costType;
    private Double peopleCost;


    /**
     * 消息通知开关
     * 0表示没有，1表示打开
     */
    private Integer notifyFlag = 0;


    /**
     * 是否允许取消
     * 0表示没有，1表示打开
     */
    private Integer allowCancel = 1;



}
