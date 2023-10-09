package com.rubber.at.tennis.invite.api.enums;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

/**
 *
 {value:999,name:"不限制"},
 {value:0,name:"不允许"},
 {value:1,name:"1人"},
 {value:2,name:"2人"},
 {value:3,name:"3人"},
 {value:4,name:"4人"}
 *
 * @author luffyu
 * Created on 2023/9/28
 */
@Getter
public enum FriendLimitEnums {

    /**
     * 备注说明
     */
    NOT_LIMIT("999","不限制"),
    NOT_ALLOW("0","不允许"),
    LIMIT_P1("1","1人"),
    LIMIT_P2("2","2人"),
    LIMIT_P3("3","3人"),
    LIMIT_P4("4","4人"),
    ;


    private final String value;

    private final String name;


    FriendLimitEnums(String value, String name) {
        this.value = value;
        this.name = name;
    }


    public static void getDesc(String value,  JSONObject configData){
        FriendLimitEnums tmp  = null;
        for (FriendLimitEnums enums:FriendLimitEnums.values()){
            if (enums.getValue().equals(value)){
                tmp = enums;
            }
        }
        if (tmp == null){
            return;
        }
        configData.put("friendLimitDesc",tmp.getName());
    }
}
