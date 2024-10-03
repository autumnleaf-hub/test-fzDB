package com.fzy.fzprj.service.impl;

import com.fzy.fzprj.bean.User;
import com.fzy.fzprj.mapper.UserMapper;
import com.fzy.fzprj.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fzy
 * @since 2024-09-29
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

}
