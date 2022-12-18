package com.rubber.at.tennis.invite.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;

import java.util.Date;

/**
 * @author luffyu
 * Created on 2022/12/18
 */
@Data
public class UserModifyTennisDto extends BaseUserSession {

    /**
     * 能力水平 2.5 / 3.0 / 3.5 等
     */
    private String ntrp = "2.5";

    /**
     * 开始打球的时间
     */
    @JsonFormat()
    private Date startPlayDate;


    /**
     * 能力矩阵
     */
    private LevelMatrixDto levelMatrix;


}
