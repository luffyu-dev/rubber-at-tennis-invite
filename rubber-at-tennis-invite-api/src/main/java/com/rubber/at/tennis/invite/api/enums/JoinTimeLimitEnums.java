package com.rubber.at.tennis.invite.api.enums;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.util.Date;

/**
 *
 *  {value:10,name:"立即报名"},
 *         {value:20,name:"活动前1天开始"},
 *         {value:30,name:"活动前2天开始"},
 *         {value:40,name:"活动前3天开始"},
 *         {value:50,name:"活动前1周开始"}
 *
 * @author luffyu
 * Created on 2023/9/28
 */
@Getter
public enum JoinTimeLimitEnums {
    /**
     * 备注说明
     */
    JOIN_DATA_H("0","截止活动开始前"),
    JOIN_DATA_H1("1","截止开始前1小时"),
    JOIN_DATA_H2("2","截止开始前2小时"),
    JOIN_DATA_H12("12","截止开始前12小时"),
    JOIN_DATA_D1("24","截止开始前1天"),
    JOIN_DATA_D3("72","截止开始前3天"),
    JOIN_DATA_D7("168","截止开始前1周")

    ;


    private final String value;

    private final String name;


    JoinTimeLimitEnums(String value, String name) {
        this.value = value;
        this.name = name;
    }


    public static void getDesc(String value, Date startTime, JSONObject configData){
        Date join = getJoinDateLineTime(value,startTime);
        configData.put("JoinTimeLimitDesc", DateUtil.format(join,"MM/dd HH:mm")+"前");
    }



    public static Date getJoinDateLineTime(String value, Date startTime){
        JoinTimeLimitEnums tmp  = null;
        for (JoinTimeLimitEnums enums:JoinTimeLimitEnums.values()){
            if (enums.getValue().equals(value)){
                tmp = enums;
            }
        }
        if (tmp == null){
            return startTime;
        }
        int x = Integer.parseInt(tmp.getValue());
       return DateUtil.offsetHour(startTime,-x);
    }
}
