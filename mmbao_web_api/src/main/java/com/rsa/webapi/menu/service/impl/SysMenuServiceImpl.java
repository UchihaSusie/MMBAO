package com.rsa.webapi.menu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.menu.dao.SysMenuDao;
import com.rsa.webapi.menu.entity.SysMenu;
import com.rsa.webapi.menu.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-06-12 20:19:06
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends  ServiceImpl <SysMenuDao, SysMenu> implements
    SysMenuService {

    @Resource
    SysMenuDao menuDao;
    @Override
    public List<SysMenu> getMenuByRoleId(Integer roleId) {
        List<SysMenu> list = menuDao.getMenuByRoleId(roleId);
        //对数据 进行处理,分出1级菜单 和 2级菜单
        //pid == 0  是1级菜单
        List<SysMenu> firstList = list.stream().filter(Objects::nonNull)
                .filter(SysMenu::checkFirst)
                .collect(Collectors.toList());
        //筛选出子菜单
        firstList.forEach(firstMenu ->{
            //firstMenu.getMid == 循环的菜单.pid
            List<SysMenu> childList = list.stream().filter(Objects::nonNull)
                    .filter(menu -> firstMenu.getId().equals(menu.getPid()))
                    .collect(Collectors.toList());
            firstMenu.setChildList(childList);
        });
        return firstList;
    }
}

