package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sysuser")//解决了与数据库表名对应
public class SysUser extends Model<SysUser> {
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
     * 账户
     * */
    @TableField
        private String name;
    /*
     * 名字
     * */
    @TableField
        private String account;
    /*
     * 密码
     * */
    @TableField
        private String password;
    /*
     * 状态0正常1禁用
     * */
    @TableField
        private Integer status;
}
