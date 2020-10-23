package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.Staff;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StaffMapper extends BaseMapper<Staff> {
}
