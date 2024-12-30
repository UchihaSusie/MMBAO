package com.rsa.webapi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.common.RedisKeys;
import com.rsa.webapi.menu.entity.SysMenu;
import com.rsa.webapi.menu.service.SysMenuService;
import com.rsa.webapi.user.dao.SysUserDao;
import com.rsa.webapi.user.entity.SysUser;
import com.rsa.webapi.user.service.SysUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2024-06-12 17:42:05
 */
@Service("sysUserService")
public class SysUserServiceImpl extends  ServiceImpl <SysUserDao, SysUser> implements
    SysUserService {


    @Resource
    SysMenuService menuService;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    ThreadPoolTaskExecutor taskExecutor;

    @Override
    public SysUser doLoginByUid(String uid) {
        //从redis拿
        String UidKey = String.format(RedisKeys.UserUidCode,uid);
        Object o = redisTemplate.opsForValue().get(UidKey);
        SysUser user;
        if( o != null){
            user = (SysUser) o;
        }else{
            //从数据库拿
            Integer id = Integer.parseInt(uid);
            user = getById(id);
            if (user !=null && user.getUserId()!=null){
                //通过sysUser拿到roleId找到对应的menu
                List<SysMenu> menusList = menuService.getMenuByRoleId(user.getRoleId());
                user.setMenusList(menusList);
                //2 存入到redis中
                SysUser finalUser = user;
                taskExecutor.execute(
                        ()->{
                            new Thread(()->{
                                String uidKey = String.format(RedisKeys.UserUidCode, finalUser.getUserId());
                                redisTemplate.opsForValue().set(uidKey, finalUser);
                                String unameKey = String.format(RedisKeys.UserNameCode, finalUser.getUserName());
                                redisTemplate.opsForValue().set(unameKey, finalUser);
                            }).start();
                        }
                );
            }
        }
        return user;
    }

    @Override
    public SysUser doUnameLogin(String username, String password) {
        //从redis拿
        String UidKey = String.format(RedisKeys.UserNameCode,username);
        Object o = redisTemplate.opsForValue().get(UidKey);
        SysUser user = null;
        if( o != null){
            user = (SysUser) o;
            if (user.getUserPassword().equals(password)){
                return user;
            }
        } else{
            //从数据库拿
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name",username);
            queryWrapper.eq("user_password",password);
            user = getOne(queryWrapper);
            if (user !=null && user.getUserId()!=null ){
                //通过sysUser拿到roleId找到对应的menu
                List<SysMenu> menusList = menuService.getMenuByRoleId(user.getRoleId());
                user.setMenusList(menusList);
                //2 存入到redis中
                SysUser finalUser = user;
                taskExecutor.execute(
                        ()->{
                            new Thread(()->{
                                String uidKey = String.format(RedisKeys.UserUidCode, finalUser.getUserId());
                                redisTemplate.opsForValue().set(uidKey, finalUser);
                                String nameKey = String.format(RedisKeys.UserNameCode, finalUser.getUserName());
                                redisTemplate.opsForValue().set(nameKey, finalUser);
                            }).start();
                        }
                );
                return user;
            }
        }
        return null;
    }

    @Override
    public List<SysUser> getListWithReviewer() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", 1, 2);
        List<SysUser> list = list(queryWrapper);
        return list;
    }
}

