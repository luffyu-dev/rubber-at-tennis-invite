package com.rubber.at.tennis.invite.api.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author luffyu
 * Created on 2022/11/27
 */
@Data
public class UserTrainInfo {


    /**
     * 总训练时间
     */
    private double allHours = 0;

    /**
     * 本周训练时间
     */
    private double weekHours = 0;


    public void setAllHours(Integer allHours) {
        if (allHours == null || allHours <= 0){
            return;
        }
        BigDecimal bg = new BigDecimal(allHours);
        this.allHours = bg.divide(new BigDecimal(60),1,2).doubleValue();
    }

    public void setWeekHours(Integer weekHours) {
        if (weekHours == null || weekHours <= 0){
            return;
        }
        BigDecimal bg = new BigDecimal(weekHours);
        this.weekHours = bg.divide(new BigDecimal(60),1,2).doubleValue();
    }
}
