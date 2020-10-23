package com.example.demo.domain.Query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RclassQo",description = "查询提交对象")
public class RclassQo  {
    @ApiModelProperty("班级名称--模糊查询")
    private String name;

    @ApiModelProperty("班级代码")
    private Integer code;

    @ApiModelProperty("页码")
    private Integer pageIndex;

    @ApiModelProperty("数量")
    private Integer pageSize;
}
