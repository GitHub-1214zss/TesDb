package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Staff;
import com.example.demo.domain.Timetable;
import com.example.demo.mapper.StaffMapper;
import com.example.demo.service.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StaffServicesImp extends ServiceImpl<StaffMapper, Staff> implements StaffService {
    @Resource
    private StaffMapper staffMapper;
    @Override
    public Staff queryStaff(Timetable timetable) {
        QueryWrapper<Staff> q=new QueryWrapper<>();
        q.eq("id",timetable.getTeacherid());
        Staff staff=staffMapper.selectOne(q);
        return staff;
    }

    @Override
    public Staff queryId(String name) {
        QueryWrapper<Staff> q=new QueryWrapper<>();
        q.eq("name",name);
        Staff staff=staffMapper.selectOne(q);
        return staff;
    }

    @Override
    public Staff queryName(Integer name) {
        QueryWrapper<Staff> q=new QueryWrapper<>();
        q.eq("id",name);
        Staff staff=staffMapper.selectOne(q);
        return staff;
    }
}
