package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Timetable;
import com.example.demo.mapper.TimeMapper;
import com.example.demo.service.TimetableService;
import org.springframework.stereotype.Service;

@Service
public class TimetableServicesImp extends ServiceImpl<TimeMapper, Timetable> implements TimetableService {
}
