package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Record;
import com.example.demo.mapper.RecordMapper;
import com.example.demo.service.RecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordServicesImp extends ServiceImpl<RecordMapper, Record> implements RecordService {
}
