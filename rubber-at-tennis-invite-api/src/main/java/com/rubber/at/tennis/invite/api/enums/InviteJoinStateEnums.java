package com.rubber.at.tennis.invite.api.enums;

import lombok.Getter;

/**
 * @author luffyu
 * Created on 2022/9/1
 */
@Getter
public enum InviteJoinStateEnums {

    /**
     * 邀请的状态
     */
    CLOSE(10,"已取消"),

    SUCCESS(20,"正常"),

    ;

    InviteJoinStateEnums(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    private final Integer state;

    private final String desc;



    public static InviteJoinStateEnums getState(Integer state){
        for (InviteJoinStateEnums stateEnums: InviteJoinStateEnums.values()){
            if (stateEnums.getState().equals(state)){
                return stateEnums;
            }
        }
        return null;
    }




}
