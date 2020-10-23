package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Course;
import com.example.demo.domain.Timetable;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServicesImp extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Resource
    private CourseMapper courseMapper;
    @Override
    public Course queryCourse(Timetable timetable) {

        QueryWrapper<Course> q1=new QueryWrapper<>();
        q1.eq("id",timetable.getCourseid());

        Course course = courseMapper.selectOne(q1);


        return course;
    }

    @Override
    public Course queryId(String name) {
        QueryWrapper<Course> q1=new QueryWrapper<>();
        q1.eq("name",name);
        Course course = courseMapper.selectOne(q1);
        return course;
    }
}
