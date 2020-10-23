package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("rolefunc")
public class RoleFunc extends Model<RoleFunc> {
    private static final long serialVersionUID = 1L;
    protected Serializable pkVal(){
        return this.funcId;
    }
    /*
     * 主键id,自增方式处理
     * */
    @TableId(type = IdType.AUTO)
    private Integer funcId;
    /*
     * roleid
     * */
    @TableField
    private Integer roleId;

}
