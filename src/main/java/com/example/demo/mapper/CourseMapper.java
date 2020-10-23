package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.Course;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
