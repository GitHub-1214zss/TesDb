package com.example.demo.domain.Query;

import lombok.Data;

@Data
public class SysUserDao {
    /**
     * 页号
     */
    private Integer pageIndex;
    /**
     * 每页行数
     */
    private Integer pageSize;
    /**
     * 用户名--模糊查询
     */
    private String name;

}
