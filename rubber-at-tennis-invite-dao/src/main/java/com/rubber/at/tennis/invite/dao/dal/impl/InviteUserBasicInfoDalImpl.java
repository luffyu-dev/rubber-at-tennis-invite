package com.rubber.at.tennis.invite.dao.dal.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.dao.entity.UserBasicInfoEntity;
import com.rubber.at.tennis.invite.dao.mapper.InviteUserBasicInfoMapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserBasicInfoDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户基础信息表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2022-10-16
 */
@Service
public class InviteUserBasicInfoDalImpl extends BaseAdminService<InviteUserBasicInfoMapper, UserBasicInfoEntity> implements IInviteUserBasicInfoDal {

    /**
     * 通过uid 查询用户的基础信息
     *
     * @param uid 当前的uid
     * @return 返回用户的基础信息
     */
    @Override
    public UserBasicInfoEntity getByUid(Integer uid) {
        LambdaQueryWrapper<UserBasicInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserBasicInfoEntity::getUid,uid);
        return getOne(lqw);
    }

    /**
     * 联系信息
     *
     * @param uid
     * @param userPhone
     * @param userWx
     * @return
     */
    @Override
    public boolean updateContact(Integer uid, String userPhone, String userWx) {
        if (StrUtil.isEmpty(userWx) && StrUtil.isEmpty(userPhone)){
            return false;
        }
        UserBasicInfoEntity userBasicInfo = getByUid(uid);
        if (userBasicInfo == null){
            return false;
        }
        boolean  needUpdate  =false;
        if (userWx != null && !userWx.equals(userBasicInfo.getUserWx())){
            userBasicInfo.setUserWx(userWx);
            needUpdate = true;
        }
        if (userPhone != null && !userPhone.equals(userBasicInfo.getUserPhone())){
            userBasicInfo.setUserPhone(userPhone);
            needUpdate = true;
        }
        if (!needUpdate){
            return false;
        }
        return updateById(userBasicInfo);
    }

    /**
     * 查询用户的基础信息列表
     *
     * @param uids 当前的uid集合
     * @return 返回符合要求的用户信息
     */
    @Override
    public List<UserBasicInfoEntity> queryByUid(Collection<Integer> uids) {
        LambdaQueryWrapper<UserBasicInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.in(UserBasicInfoEntity::getUid,uids);
        return list(lqw);
    }
}
