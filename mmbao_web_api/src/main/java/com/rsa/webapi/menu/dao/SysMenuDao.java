package com.rsa.webapi.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.webapi.menu.entity.SysMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-12 20:19:06
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {

    @Select("SELECT m.* FROM sys_menu m,menu_role r WHERE m.id = r.menu_id and r.role_id = #{roleId}")
    List<SysMenu> getMenuByRoleId(Integer roleId);
}

