package com.rubber.at.tennis.invite.api.enums;

import lombok.Getter;

/**
 * @author luffyu
 * Created on 2022/9/1
 */
@Getter
public enum ActivityInviteJoinStateEnums {

    /**
     * 邀请的状态
     */

    INVITING(10,"报名中"),

    FINISHED(20,"名额已满"),

    TIME_LINE(30,"报名截止"),

    END(40,"活动结束"),

    ;

    ActivityInviteJoinStateEnums(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    private final Integer state;

    private final String desc;



    public static ActivityInviteJoinStateEnums getState(Integer state){
        for (ActivityInviteJoinStateEnums stateEnums: ActivityInviteJoinStateEnums.values()){
            if (stateEnums.getState().equals(state)){
                return stateEnums;
            }
        }
        return null;
    }




}
