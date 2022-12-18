package com.rubber.at.tennis.invite.api.enums;

import lombok.Getter;

/**
 * @author luffyu
 * Created on 2022/12/18
 */
@Getter
public enum  NtrpEnums {
    /**
     * 网球运动员的能力水平
     */
    LEVEL_1("1.0","初学者"),
    LEVEL_1_5("1.5","1.5"),
    LEVEL_2("2.0","2.0"),
    LEVEL_2_5("2.5","2.5"),
    LEVEL_3("3.0","3.0"),
    LEVEL_3_5("3.5","3.5"),
    LEVEL_4("4.0","4.0"),
    LEVEL_4_5("4.5","4.5"),
    LEVEL_5("5.0","5.0"),
    LEVEL_5_5("5.5","5.5"),
    LEVEL_6("6.0","国内选手"),
    LEVEL_7("7.0","国际选手"),

    ;


    NtrpEnums(String ntrp, String label) {
        this.ntrp = ntrp;
        this.label = label;
    }

    private final String ntrp;

    private final String label;


    /**
     * 查询等级级别
     * @param ntrp 当前的能力水平
     * @return 返回枚举类
     */
    public static  NtrpEnums getByLevel(String ntrp){
        for (NtrpEnums ntrpEnums:NtrpEnums.values()){
            if (ntrpEnums.ntrp.equals(ntrp)){
                return ntrpEnums;
            }
        }
        return null;
    }
}
