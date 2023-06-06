package com.rubber.at.tennis.invite.dao.condition;


import lombok.Data;

import java.util.Date;

/**
 * @author luffyu
 * Created on 2022/12/15
 */
@Data
public class InviteInfoCondition {

    /**
     * 发起人id
     */
    private Integer joinUid;


    /**
     * 搜索条件
     */
    private String searchValue;


    /**
     * 结束时间
     */
    private Date engTimeLine;


    /**
     * 创建截止时间
     */
    private Date createTimeLine;

}
