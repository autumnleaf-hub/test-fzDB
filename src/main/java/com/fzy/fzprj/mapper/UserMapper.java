package com.fzy.fzprj.mapper;

import com.fzy.fzprj.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fzy
 * @since 2024-09-29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
