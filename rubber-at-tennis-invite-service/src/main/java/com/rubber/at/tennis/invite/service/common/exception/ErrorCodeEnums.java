package com.rubber.at.tennis.invite.service.common.exception;

import com.rubber.base.components.util.result.code.ICodeHandle;

/**
 * @author luffyu
 * Created on 2022/8/14
 */
public enum  ErrorCodeEnums implements ICodeHandle {


    /**
     * 系统的code 从 1-**-**-** 开始
     *
     * 1-10-**-** 开始
     *
     * 2-10-**-** 系统类型错误
     *
     * 3-10-**-** 用户行为错误
     *
     */



    /**
     * 2-10-**-** 表示服务系统异常的提示
     */



    /**
     * 3-10-**-** 表示用户的行为错误
     */
    INVITE_FINISHED("33001","活动已结束"),

    INVITE_TIME_JOIN_DEADLINE("33002","报名时间已截止"),
    USER_IS_FULL("33001","报名人员已满"),
    USER_IS_JOINED("33004","您已报名成功"),
    USER_NOT_JOINED("33005","您还未报名"),
    INVITE_CLOSE("33006","活动已取消"),
    INVITE_NUMBER_CHANGE_ERROR("33007","邀请人数不能小于已报名人数"),

    USER_IS_FULL_MAX("33008","报名数超过最大限制"),

    CANT_CANCEL_JOIN("33009","不可取消"),

    CANCEL_JOIN_DATA_LINE("33009","取消时间截止"),

    INVITE_STARTED("33001","活动已开始"),


    ;
    public final String code;

    public final String msg;


    ErrorCodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
