package com.rubber.at.tennis.invite.api.enums;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

/**
 *
 {value:0,name:"公开所有人可见"},
 {value:1,name:"仅邀请可见"}
 *
 * @author luffyu
 * Created on 2023/9/28
 */
@Getter
public enum ShowLimitEnums {
    /**
     * 备注说明
     */
    ALL_USER("0","公开所有人可见"),
    INVITE_USER("1","仅邀请可见"),
    ;


    private final String value;

    private final String name;


    ShowLimitEnums(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static void getDesc(String value,  JSONObject configData){
        ShowLimitEnums tmp  = null;
        for (ShowLimitEnums enums:ShowLimitEnums.values()){
            if (enums.getValue().equals(value)){
                tmp = enums;
            }
        }
        if (tmp == null){
            return;
        }
        configData.put("showLimitDesc",tmp.getName());
    }
}
