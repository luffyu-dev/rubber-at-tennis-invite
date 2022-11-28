package com.rubber.at.tennis.invite.service.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rubber.at.tennis.invite.service.common.constant.RecordTypeEnums;
import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;

import java.util.Date;

/**
 * @author luffyu
 * Created on 2022/11/27
 */
@Data
public class RecordTennisModel  {

    /**
     * 发起人id
     */
    private BaseUserSession userSession;

    /**
     * 记录名称
     */
    private String recordTitle;

    /**
     * 记录类型 1表示训练 2表示比赛
     */
    private RecordTypeEnums recordType;

    /**
     * 训练日期 天维度
     */
    private String recordDate;

    /**
     * 训练启始时间
     */
    private Date recordStart;

    /**
     * 训练结束时间
     */
    private Date recordEnd;

    /**
     * 训练时长，单位为分钟
     */
    private Integer recordDuration;

    /**
     * 备注
     */
    private String remark;
}
