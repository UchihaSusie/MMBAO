package com.rsa.webapi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.user.entity.SysUser;

import java.util.List;

/**
 * (SysUser)表服务接口
 *
 * @author makejava
 * @since 2024-06-12 17:42:05
 */
public interface SysUserService extends IService<SysUser> {

    SysUser doLoginByUid(String uid);

    SysUser doUnameLogin(String username, String password);

    List<SysUser> getListWithReviewer();

}

