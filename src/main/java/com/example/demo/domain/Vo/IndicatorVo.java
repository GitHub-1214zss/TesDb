package com.example.demo.domain.Vo;

import com.example.demo.domain.Indicator;
import com.example.demo.domain.Rlternative;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "IndicatorVo",description = "指标的VO(含备选项)")
public class IndicatorVo extends Indicator {
    @ApiModelProperty(value = "指标备选项列表")
    private List<Rlternative> items;
    private String[] boxIds;
    private String[] boxType;
    private String[] boxType1;
}
