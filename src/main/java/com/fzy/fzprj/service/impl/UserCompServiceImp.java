package com.fzy.fzprj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fzy.fzprj.bean.UserComp;
import com.fzy.fzprj.mapper.UserCompMapper;
import com.fzy.fzprj.service.UserCompService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fzy
 * @since 2024-09-29
 */
@Service
public class UserCompServiceImp extends ServiceImpl<UserCompMapper, UserComp> implements UserCompService {

    @Resource
    UserCompMapper userCompMapper;

    @Override
    public int updateBatchByUidAndCid(Collection<UserComp> userComps) {
        int count = 0;
        for (UserComp userComp : userComps) {
            LambdaQueryWrapper<UserComp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserComp::getUid, userComp.getUid());
            wrapper.eq(UserComp::getCid, userComp.getCid());
            count += userCompMapper.update(userComp, wrapper);
        }
        return 0;
    }
}
