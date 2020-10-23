package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
//    @Select("selectSysUser")
//    List<SysUser> selectUserList(Pagination page, String state);
}
