package com.rubber.at.tennis.invite.api;

import com.rubber.at.tennis.invite.api.dto.req.InvitePageReq;

import java.util.List;

/**
 * @author luffyu
 * Created on 2023/3/5
 */
public interface AtImageApi {

    /**
     * 查询推荐的图片
     * @param req
     * @return
     */
    List<String> queryInviteRecommendImg(InvitePageReq req);
}
