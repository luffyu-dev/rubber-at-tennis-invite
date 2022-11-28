package com.rubber.at.tennis.invite.api.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author luffyu
 * Created on 2022/11/25
 */
@Data
public class UserTennisDetail {


    /**
     * uid
     */
    private Integer uid;

    /**
     * 能力水平 2.5 / 3.0 / 3.5 等
     */
    private String ntrp = "2.5";

    /**
     * 开始打球的时间
     */
    private Date startPlayDate;

    /**
     * 网龄-年
     */
    private Integer yearDate = 0;

    /**
     * 网龄-月
     */
    private Integer monthDate = 0;


    /**
     * 能力矩阵
     */
    private LevelMatrixDto levelMatrix = new LevelMatrixDto();


    /**
     * 用户的训练信息
     */
    private UserTrainInfo userTrainInfo = new UserTrainInfo();


    /**
     * 有网球的日期
     */
    private List<String> tennisDate =  new ArrayList<>();

}
