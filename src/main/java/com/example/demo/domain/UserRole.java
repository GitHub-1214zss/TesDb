package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("userrole")
public class UserRole extends Model<UserRole> {
    private static final long serialVersionUID = 1L;
    protected Serializable pkVal(){
        return this.serialVersionUID;
    }
    /*
     * 主键id,自增方式处理
     * */
    @TableField("userid")
    private Integer userid;
    /*
     * roleid
     * */
    @TableField("roleId")
    private Integer roleId;
}
