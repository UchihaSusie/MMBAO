package com.rsa.webapi.menu.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (SysMenu)表实体类
 *
 * @author makejava
 * @since 2024-06-12 20:19:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu extends Model<SysMenu> {
    //菜单编号
    @TableId(type = IdType.AUTO)
    private Integer id;
    //菜单名称
    private String title;
    //显示排序
    private Integer showOrder;
    //请求地址
    private String path;
    //菜单类型     0目录   1菜单   2按钮
    private Integer menuType;
    //菜单状态  0显示1隐藏
    private Integer menuState;
    //菜单图标
    private String menuFlag;
    //父级菜单 id
    private Integer pid;
    //权限标识
    private String menuPermit;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //打开方式  0 页签  1新窗口
    private Integer openWay;
    @TableField(exist = false)
    private List<SysMenu> childList;
    public boolean checkFirst(){
        return pid == 0;
    }
}

