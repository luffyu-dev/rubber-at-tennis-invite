package com.rubber.at.tennis.invite.api.enums;

import lombok.Getter;

/**
 * @author luffyu
 * Created on 2023/3/5
 */
@Getter
public enum  AtImgTagEnums {
    /**
     * 图片的tag
     */

    RECOMMEND("recommend","推荐");


    AtImgTagEnums(String tag, String desc) {
        this.tag = tag;
        this.desc = desc;
    }

    private String tag;

    private String desc;


}
