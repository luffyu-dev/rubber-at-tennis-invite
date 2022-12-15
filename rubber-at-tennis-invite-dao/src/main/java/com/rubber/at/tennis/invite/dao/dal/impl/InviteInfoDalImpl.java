package com.rubber.at.tennis.invite.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.invite.dao.condition.InviteInfoCondition;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.mapper.InviteInfoMapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteInfoDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邀约详情表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
@Service
public class InviteInfoDalImpl extends BaseAdminService<InviteInfoMapper, InviteInfoEntity> implements IInviteInfoDal {

    /**
     * 通过code查询邀请的信息
     *
     * @param code 当前的code
     * @return 返回邀请的信息
     */
    @Override
    public InviteInfoEntity getByCode(String code) {
        LambdaQueryWrapper<InviteInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteInfoEntity::getInviteCode,code);
        return getOne(lqw);
    }

    /**
     * @param page
     * @param condition
     * @return
     */
    @Override
    public Page<InviteInfoEntity> queryPageByJoin(Page<InviteInfoEntity> page, InviteInfoCondition condition) {
        return getBaseMapper().queryPageByJoin(page,condition);
    }
}
