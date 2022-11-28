package com.rubber.at.tennis.invite.api.dto;

import lombok.Data;

/**
 * @author luffyu
 * Created on 2022/11/27
 */
@Data
public class UserTrainInfo {


    /**
     * 总训练时间
     */
    private Integer allHours = 0;

    /**
     * 本周训练时间
     */
    private Integer weekHours = 0;
}
