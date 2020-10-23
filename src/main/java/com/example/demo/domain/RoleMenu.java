package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("rolemenu")
public class RoleMenu extends Model<RoleMenu> {
    private static final long serialVersionUID = 1L;
    /*
    *
    * 序列化*/
    protected Serializable pkVal(){
        return this.serialVersionUID;
    }
    /*
     * 主键id,自增方式处理
     * */
    @TableField
    private Integer menuId;
    /*
     * roleid
     * */
    @TableField
    private Integer roleId;
}
