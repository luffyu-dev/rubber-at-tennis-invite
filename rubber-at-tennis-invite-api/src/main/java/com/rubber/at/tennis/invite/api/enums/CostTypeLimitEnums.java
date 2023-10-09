package com.rubber.at.tennis.invite.api.enums;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.util.Date;

/**
 *
 {value:"2",name:"现场结算"},
 {value:"1",name:"AA+"},
 {value:"0",name:"免费"}
 *
 * @author luffyu
 * Created on 2023/9/28
 */
@Getter
public enum CostTypeLimitEnums {

    /**
     * 枚举类型
     */
     FOR_FREE("0","免费"),

     FOR_AA("1","AA"),

     FOR_SITE("2","现场结算"),

    ;


    private final String value;

    private final String name;


    CostTypeLimitEnums(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static void getDesc(String value,  JSONObject configData){
        CostTypeLimitEnums tmp  = null;
        for (CostTypeLimitEnums enums:CostTypeLimitEnums.values()){
            if (enums.getValue().equals(value)){
                tmp = enums;
            }
        }
        if (tmp == null){
            return;
        }
        if (tmp.equals(FOR_FREE)){
            configData.put("costTypeLimitDesc",tmp.getName());
        }
        String peopleCost = configData.getString("peopleCost");
        switch (tmp){
            case FOR_AA:
                configData.put("costTypeLimitDesc","需支付"+peopleCost+"元");
                break;
            case FOR_SITE:
            case FOR_FREE:
            default:
                configData.put("costTypeLimitDesc",tmp.getName());
        }

    }
}
