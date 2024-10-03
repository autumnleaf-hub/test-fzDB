package com.fzy.fzprj.mapper;

import com.fzy.fzprj.bean.UserComp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fzy
 * @since 2024-09-29
 */
@Mapper
public interface UserCompMapper extends BaseMapper<UserComp> {
    @Update("update user_comp set standing = #{standing} where uid = #{uid} and cid = #{cid}")
    int updateStandingByUidAndCid(UserComp userComp);
}
