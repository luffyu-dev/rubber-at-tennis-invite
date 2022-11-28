package com.rubber.at.tennis.invite.api.dto;

import lombok.Data;

/**
 * @author luffyu
 * Created on 2022/11/26
 */
@Data
public class LevelMatrixDto {

    /**
     * 正手
     */
    private Integer forehand = 0;

    /**
     * 反手
     */
    private Integer backhand = 0;

    /**
     * 切削
     */
    private Integer cutting = 0;

    /**
     * 发球
     */
    private Integer serve = 0;

    /**
     * 网前
     */
    private Integer netFront = 0;


}
