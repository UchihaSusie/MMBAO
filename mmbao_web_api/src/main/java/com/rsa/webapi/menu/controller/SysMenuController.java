package com.rsa.webapi.menu.controller;



import com.rsa.webapi.common.ReturnResult;
import com.rsa.webapi.menu.service.SysMenuService;
import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rsa.webapi.menu.entity.SysMenu;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysMenu)表控制层
 *
 * @author makejava
 * @since 2024-06-12 20:19:06
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController{
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 查询菜单列表
     * @return 菜单列表
     */
    @GetMapping("/list/{rid}")
    public ResponseEntity<ReturnResult> menuListAll(@PathVariable Integer rid) {
        List<SysMenu> list = this.sysMenuService.getMenuByRoleId(rid);
        return ResponseEntity.ok(ReturnResult.ok(list));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        SysMenu  sysMenu = this.sysMenuService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok(sysMenu));
    }

    /**
     * 新增或修改数据
     *
     * @param sysMenu 实体对象
     * @return 新增结果
     */
    @PostMapping("save/update")
    public ResponseEntity<ReturnResult>  addOrUpdate(@RequestBody SysMenu sysMenu) {
         this.sysMenuService.saveOrUpdate(sysMenu);
         return ResponseEntity.ok(ReturnResult.ok(sysMenu));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult>  delete(@RequestParam("idList") List<Long> idList) {
        this.sysMenuService.removeByIds(idList);
        return ResponseEntity.ok(ReturnResult.ok());
    }
    
      /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ReturnResult> deleteById(@PathVariable Integer id) {
        this.sysMenuService.removeById(id);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

