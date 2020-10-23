package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Staff;
import com.example.demo.domain.Timetable;

public interface StaffService extends IService<Staff> {
    Staff queryStaff(Timetable timetable);
    Staff queryId(String name);
    Staff queryName(Integer name);
}
