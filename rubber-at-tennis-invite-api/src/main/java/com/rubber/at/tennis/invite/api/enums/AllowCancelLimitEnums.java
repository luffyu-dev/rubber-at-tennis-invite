package com.rubber.at.tennis.invite.api.enums;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.util.Date;

/**
 * {value:10,name:"开始前随时可取消"},
 *           {value:20,name:"不可取消"},
 *           {value:30,name:"开始前24小时可取消"},
 *           {value:40,name:"开始前12小时可取消"},
 *           {value:50,name:"开始前6小时可取消"},
 *           {value:60,name:"开始前2小时可取消"}
 * @author luffyu
 * Created on 2023/9/28
 */
@Getter
public enum AllowCancelLimitEnums {

    /**
     *  备注说明
     */
    CAN_CANCEL("0","开始前可随时取消"),
    NOT_CANCEL("-1","不可取消"),
    CAN_CANCEL_FOR_24H("24","开始前24小时可取消"),
    CAN_CANCEL_FOR_12H("12","开始前12小时可取消"),
    CAN_CANCEL_FOR_6H("6","开始前6小时可取消"),
    CAN_CANCEL_FOR_2H("2","开始前2小时可取消"),
    CAN_CANCEL_FOR_1H("1","开始前1小时可取消"),
    ;


    private final String value;

    private final String name;


    AllowCancelLimitEnums(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * @param value
     * @return
     */
    public static void getDesc(String value, Date startTime, JSONObject configData) {
        if(StrUtil.isEmpty(value)){
            return;
        }
        AllowCancelLimitEnums tmp  = null;
        for (AllowCancelLimitEnums enums:AllowCancelLimitEnums.values()){
            if (enums.getValue().equals(value)){
                tmp = enums;
            }
        }
        if (tmp == null){
            return;
        }
        int index =  Integer.parseInt(tmp.getValue());
        Date cancal =  DateUtil.offsetHour(startTime,-index);

        configData.put("allowCancelDesc",tmp.getName());
        if (NOT_CANCEL.equals(tmp) || CAN_CANCEL.equals(tmp)) {
            configData.put("allowCancelDescChecked",tmp.getName());
        }else {
            configData.put("allowCancelDescChecked",DateUtil.format(cancal,"MM/dd HH:mm")+"前可取消");
        }
    }


    public static Date cancelDataLine(String value, Date startTime){
        if(StrUtil.isEmpty(value)){
            return startTime;
        }
        AllowCancelLimitEnums tmp  = null;
        for (AllowCancelLimitEnums enums:AllowCancelLimitEnums.values()){
            if (enums.getValue().equals(value)){
                tmp = enums;
            }
        }
        if (tmp == null){
            return startTime;
        }
        if (NOT_CANCEL.equals(tmp)){
            return null;
        }
        int index =  Integer.parseInt(tmp.getValue());
        return  DateUtil.offsetHour(startTime,-index);
    }

}
