package com.example.demo.domain.Vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.domain.Course;
import lombok.Data;

@Data
@TableName("course")
public class CourseVo extends Course {
    private Integer teacherId;
    private Integer classId;
}
