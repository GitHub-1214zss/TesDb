package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sysfunc")
public class SysFunc extends Model<SysFunc> {
    private static final long serialVersionUID = 1L;
    protected Serializable pkVal(){
        return this.id;
    }
    /*
     * 主键id,自增方式处理
     * */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /*
     *操作名称
     * */
    @TableField
    private String name;
    /*
     * 父菜单
     * */
    @TableField
    private Integer menuId;
    /*
     * 数字
     * */
    @TableField
    private Integer sortCode;
    /*
     * 菜单路劲
     * */
    @TableField
    private String appUrl;
    /*
     * 图片路径
     * */
    @TableField
    private String imgUrl;
}
