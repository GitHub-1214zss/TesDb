package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Studengts;
import com.example.demo.mapper.StudentsMapper;
import com.example.demo.service.StudentsService;
import org.springframework.stereotype.Service;

@Service
public class StudentsServicesImp extends ServiceImpl<StudentsMapper, Studengts> implements StudentsService {
}
