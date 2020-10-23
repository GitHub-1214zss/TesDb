package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Course;
import com.example.demo.domain.Timetable;

public interface CourseService extends IService<Course> {
    Course queryCourse(Timetable timetable);
    Course queryId(String name );
}
