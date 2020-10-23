package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("record")
public class Record extends Model<Record> {
    private static final long serialVersionUID = 1L;
    protected Serializable pkVal(){
        return this.id;
    }
    /*
     * 主键id,自增方式处理
     * */
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private Integer userid;
    @TableField
    private Integer teacherid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date evaluatetime;
    @TableField
    private Integer targetid;
    @TableField
    private Integer taskid;
    @TableField
    private Integer itemid;
    @TableField
    private Integer courseid;
}
