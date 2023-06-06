package com.rubber.at.tennis.invite.api.enums;

import lombok.Getter;

/**
 * @author luffyu
 * Created on 2022/9/1
 */
@Getter
public enum  InviteInfoStateEnums {

    /**
     * 邀请的状态
     */

    INIT(0,"待发布"),

    CLOSE(10,"已取消"),

    EXPIRED(11,"已截止"),

    INVITING(50,"报名中"),

    FINISHED(70,"已满员"),

    ;

    InviteInfoStateEnums(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    private final Integer state;

    private final String desc;



    public static InviteInfoStateEnums getState(Integer state){
        for (InviteInfoStateEnums stateEnums:InviteInfoStateEnums.values()){
            if (stateEnums.getState().equals(state)){
                return stateEnums;
            }
        }
        return null;
    }


    public static boolean isForUpdate(Integer state){
        InviteInfoStateEnums stateEnums = getState(state);
        if (stateEnums == null){
            return false;
        }
        switch (stateEnums){
            case INIT:
            case INVITING:
            case FINISHED:
                return true;
            default:
                return false;
        }
    }



    public static boolean isForPublish(Integer state){
        InviteInfoStateEnums stateEnums = getState(state);
        if (stateEnums == null){
            return false;
        }
        switch (stateEnums){
            case INIT:
            case FINISHED:
            case EXPIRED:
                return true;
            default:
                return false;
        }
    }



}
