package com.example.demo.domain.DuiXiang;

import com.example.demo.domain.Timetable;
import lombok.Data;

@Data
public class TeachType extends Timetable {
    private String classname;
    private String coursename;
    private String staffname;

    private Integer existHis;//0 没有评价记录 1 有评价记录
}
