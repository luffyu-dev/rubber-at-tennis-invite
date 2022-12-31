package com.rubber.at.tennis.invite.service.model;

import lombok.Data;

/**
 * @author luffyu
 * Created on 2022/12/19
 */
@Data
public class InviteCostInfo {

    /**
     * 0表示免费 1表示AA
     */
    private Integer costType;

    /**
     * 人均多少钱
     */
    private Double peopleCost;

}
