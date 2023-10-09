package com.rubber.at.tennis.invite.dao.dal.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.dao.entity.InviteConfigFieldEntity;
import com.rubber.at.tennis.invite.dao.mapper.InviteConfigFieldMapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteConfigFieldDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import com.rubber.base.components.util.result.code.SysCode;
import com.rubber.base.components.util.result.exception.BaseResultRunTimeException;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 邀约详情配置表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Slf4j
@Service
public class InviteConfigFieldDalImpl extends BaseAdminService<InviteConfigFieldMapper, InviteConfigFieldEntity> implements IInviteConfigFieldDal {

    /**
     * @param inviteCode
     * @param inviteConfigFieldEntityList
     */
    @Override
    @Transactional(
            rollbackFor = Exception.class
    )
    public void removeAndSaveList(String inviteCode, List<InviteConfigFieldEntity> inviteConfigFieldEntityList) {
        LambdaQueryWrapper<InviteConfigFieldEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteConfigFieldEntity::getInviteCode,inviteCode);
        if(count(lqw) > 0 && !this.remove(lqw)){
            throw new BaseResultRunTimeException(SysCode.SYSTEM_BUS);
        }
        if(CollUtil.isNotEmpty(inviteConfigFieldEntityList) &&  !this.saveBatch(inviteConfigFieldEntityList)){
            throw new BaseResultRunTimeException(SysCode.SYSTEM_BUS);
        }
    }

    /**
     * @param code
     * @return
     */
    @Override
    public List<InviteConfigFieldEntity> queryByCode(String code) {
        LambdaQueryWrapper<InviteConfigFieldEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteConfigFieldEntity::getInviteCode,code);
        return list(lqw);
    }

    /**
     * @param code
     * @param key
     * @return
     */
    @Override
    public InviteConfigFieldEntity queryByCode(String code, String key) {
        LambdaQueryWrapper<InviteConfigFieldEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteConfigFieldEntity::getInviteCode,code)
                .eq(InviteConfigFieldEntity::getInviteField,key);
        return getOne(lqw);
    }
}
