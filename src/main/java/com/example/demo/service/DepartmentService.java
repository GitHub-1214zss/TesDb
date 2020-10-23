package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Department;
import com.example.demo.domain.Staff;

public interface DepartmentService extends IService<Department> {
    Department queryDepartment(Staff staff);
}
