package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.Studengts;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StudentsMapper extends BaseMapper<Studengts> {
}
