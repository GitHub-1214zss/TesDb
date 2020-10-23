package com.example.demo.domain.Vo;


import com.example.demo.domain.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserVo extends SysUser {
    //状态描述
    private String statusName;
    //角色列表id集合
    private List<Integer> list;







}
