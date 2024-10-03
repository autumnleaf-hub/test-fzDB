package com.fzy.fzprj.service;

import com.fzy.fzprj.bean.UserComp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fzy
 * @since 2024-09-29
 */
public interface UserCompService extends IService<UserComp> {
    int updateBatchByUidAndCid(Collection<UserComp> userComps);
}
