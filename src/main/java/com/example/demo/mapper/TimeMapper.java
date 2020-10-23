package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.Timetable;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TimeMapper extends BaseMapper<Timetable> {
}
