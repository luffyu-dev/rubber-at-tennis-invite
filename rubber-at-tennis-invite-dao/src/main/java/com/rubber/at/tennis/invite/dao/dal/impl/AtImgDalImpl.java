package com.rubber.at.tennis.invite.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.at.tennis.invite.dao.entity.AtImgEntity;
import com.rubber.at.tennis.invite.dao.mapper.AtImgMapper;
import com.rubber.at.tennis.invite.dao.dal.IAtImgDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 图片表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2023-03-05
 */
@Service
public class AtImgDalImpl extends BaseAdminService<AtImgMapper, AtImgEntity> implements IAtImgDal {

    @Override
    public IPage<AtImgEntity> queryByType(IPage<AtImgEntity> page, String tag) {
        LambdaQueryWrapper<AtImgEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AtImgEntity::getImgTag,tag)
                .eq(AtImgEntity::getStatus,1);
        lqw.orderByDesc(AtImgEntity::getId);
        return page(page,lqw);
    }
}
