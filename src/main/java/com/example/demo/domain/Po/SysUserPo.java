package com.example.demo.domain.Po;


import com.example.demo.domain.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserPo extends SysUser {
    /*
    * 担任的角色Id集
    * */
    private List<Integer> roleIdList;
}
