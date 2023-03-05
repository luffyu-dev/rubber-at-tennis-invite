package com.rubber.at.tennis.invite.dao.dal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.at.tennis.invite.dao.entity.AtImgEntity;
import com.rubber.base.components.mysql.plugins.admin.IBaseAdminService;

import java.util.List;

/**
 * <p>
 * 图片表 服务类
 * </p>
 *
 * @author rockyu
 * @since 2023-03-05
 */
public interface IAtImgDal extends IBaseAdminService<AtImgEntity> {


    IPage<AtImgEntity> queryByType(IPage<AtImgEntity> page,String type);
}
