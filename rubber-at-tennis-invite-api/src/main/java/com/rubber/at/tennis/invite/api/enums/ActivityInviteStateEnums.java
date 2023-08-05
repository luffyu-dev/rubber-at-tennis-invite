package com.rubber.at.tennis.invite.api.enums;

import lombok.Getter;

/**
 * @author luffyu
 * Created on 2022/9/1
 */
@Getter
public enum ActivityInviteStateEnums {

    /**
     * 邀请的状态
     */

    INIT(10,"待发布"),

    PUBLISHED(20,"已发布"),

    CLOSE(30,"已取消")


    ;

    ActivityInviteStateEnums(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    private final Integer state;

    private final String desc;



    public static ActivityInviteStateEnums getState(Integer state){
        for (ActivityInviteStateEnums stateEnums: ActivityInviteStateEnums.values()){
            if (stateEnums.getState().equals(state)){
                return stateEnums;
            }
        }
        return null;
    }




}
