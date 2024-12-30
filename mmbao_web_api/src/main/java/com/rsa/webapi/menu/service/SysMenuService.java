package com.rsa.webapi.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.menu.entity.SysMenu;

import java.util.List;

/**
 * (SysMenu)表服务接口
 *
 * @author makejava
 * @since 2024-06-12 20:19:06
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> getMenuByRoleId(Integer roleId);
}

