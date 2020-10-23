package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Department;
import com.example.demo.domain.Staff;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentServicesImp extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;
    @Override
    public Department queryDepartment(Staff staff) {
        QueryWrapper<Department> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",staff.getDepartmentid());
        Department department=departmentMapper.selectOne(queryWrapper);
        return department;
    }
}
