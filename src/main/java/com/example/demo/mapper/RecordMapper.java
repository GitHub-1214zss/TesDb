package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.Record;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
