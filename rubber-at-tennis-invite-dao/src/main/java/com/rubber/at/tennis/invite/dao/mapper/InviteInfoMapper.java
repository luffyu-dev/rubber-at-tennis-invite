package com.rubber.at.tennis.invite.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.invite.dao.condition.InviteInfoCondition;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 邀约详情表 Mapper 接口
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
public interface InviteInfoMapper extends BaseMapper<InviteInfoEntity> {

    /**
     * 查询条件
     * @param page
     * @param condition
     * @return
     */
    Page<InviteInfoEntity> queryPageByJoin(Page<InviteInfoEntity> page, @Param("condition") InviteInfoCondition condition);
}
