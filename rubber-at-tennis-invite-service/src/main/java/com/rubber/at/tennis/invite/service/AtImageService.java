package com.rubber.at.tennis.invite.service;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.invite.api.AtImageApi;
import com.rubber.at.tennis.invite.api.dto.req.InvitePageReq;
import com.rubber.at.tennis.invite.api.enums.AtImgTagEnums;
import com.rubber.at.tennis.invite.dao.dal.IAtImgDal;
import com.rubber.at.tennis.invite.dao.entity.AtImgEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2023/3/5
 */
@Slf4j
@Service
public class AtImageService implements AtImageApi {


    @Autowired
    private IAtImgDal iAtImgDal;


    /**
     * 图片的缓存
     * 60分钟
     */
    private FIFOCache<String,List<String>> imgCache = CacheUtil.newFIFOCache(10,60 * 60 * 1000);


    /**
     * 查询推荐的图片
     *
     * @param req
     * @return
     */
    @Override
    public List<String> queryInviteRecommendImg(InvitePageReq req) {
        List<String> result = null;
        String cacheKey = "INVITE_RECOMMEND";
        if (req.getPage() == 1){
            result = imgCache.get(cacheKey);
        }
        if (CollUtil.isNotEmpty(result)){
            return result;
        }
        Page<AtImgEntity> page = new Page<>();
        page.setCurrent(req.getPage());
        page.setSize(req.getSize());
        page.setSearchCount(false);
        iAtImgDal.queryByType(page, AtImgTagEnums.RECOMMEND.getTag());
        if (CollUtil.isEmpty(page.getRecords())){
            return null;
        }
        result = page.getRecords().stream().map(AtImgEntity::getImgUrl).collect(Collectors.toList());
        imgCache.put(cacheKey,result);
        return result;
    }
}
