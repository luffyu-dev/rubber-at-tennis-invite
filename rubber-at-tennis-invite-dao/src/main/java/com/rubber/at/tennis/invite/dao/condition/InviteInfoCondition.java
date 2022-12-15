package com.rubber.at.tennis.invite.dao.condition;


import lombok.Data;

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


}
