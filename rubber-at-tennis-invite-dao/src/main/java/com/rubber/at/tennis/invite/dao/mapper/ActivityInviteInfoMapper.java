package com.rubber.at.tennis.invite.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.invite.dao.condition.InviteInfoCondition;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 邀约表 Mapper 接口
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
public interface ActivityInviteInfoMapper extends BaseMapper<ActivityInviteInfoEntity> {


    /**
     * 按照条件查询
     * @param condition
     */
    IPage<ActivityInviteInfoEntity> queryPageByJoin(IPage<ActivityInviteInfoEntity> page, @Param("condition") InviteInfoCondition condition);
}
