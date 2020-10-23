package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("class")
@ApiModel(value = "Rclass",description = "班级对象")
public class Rclass extends Model<Rclass> {
    private static final long serialVersionUID = 1L;
    protected Serializable pkVal(){
        return this.id;
    }
    /*
     * 主键id,自增方式处理
     * */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;
    @TableField
    @ApiModelProperty("班级名称")
    private String name;
    @TableField
    @ApiModelProperty("班级代码")
    private Integer code;
}
