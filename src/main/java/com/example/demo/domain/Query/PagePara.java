package com.example.demo.domain.Query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PagePara",description = "分页参数")
public class PagePara {
    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Integer pageIndex;
    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Integer pageSize;
}
