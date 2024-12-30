package com.rsa.webapi.user.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.List;

import com.rsa.webapi.menu.entity.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (SysUser)表实体类
 *
 * @author makejava
 * @since 2024-06-12 17:42:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends Model<SysUser> {
    @TableId(type = IdType.AUTO)
    private Integer userId;
    //用户账号
    private String userAccount;
    //用户名称
    private String userName;
    //用户密码
    private String userPassword;
    //用户性别（1：男 0：女 2：未知）
    private Integer userGender;
    //手机
    private String userPhone;
    //用户邮箱
    private String userEmail;
    //用户状态（0：启用 1：禁用）
    private Integer userState;
    //备注
    private String remark;
    //岗位id外键
    private String posId;
    //部门id外键
    private Integer departId;
    //角色id外键
    private Integer roleId;
    //创建时间
    private Date createTime;
    
    private Date updateTime;

    @TableField(exist = false)
    private List<SysMenu> menusList;
}

