package com.rsa.webapi.user.controller;
import com.rsa.webapi.common.ExceptionEnum;
import com.rsa.webapi.common.JWTUtil;
import com.rsa.webapi.common.ParameterUtils;
import com.rsa.webapi.common.ReturnResult;
import com.rsa.webapi.common.exception.JavasmException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.rsa.webapi.user.entity.SysUser;
import com.rsa.webapi.user.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2024-06-12 17:42:04
 */
@RestController
@RequestMapping("/user")
public class SysUserController{
    /**
     * 服务对象
     */
    @Resource
    private SysUserService userService;
    @Resource
    HttpServletRequest request;
    Logger logger = LogManager.getLogger(SysUserController.class);

    /**
     * login with name pwd or token
     * */
    @PostMapping("/login")
    public ResponseEntity<ReturnResult> LoginToken(String username,String password){
        SysUser sysUser = null;
        String token = request.getHeader(JWTUtil.Admin_Token);
        if(ParameterUtils.checkParam(token)){
            String uid = JWTUtil.parse(token);
            sysUser = userService.doLoginByUid(uid);
        }else {
            String encodePassword = DigestUtils.md5Hex(password);
            if (!ParameterUtils.checkParam(username,password)){
                throw new JavasmException(ExceptionEnum.LOGINERROR);
            }
            sysUser = userService.doUnameLogin(username,encodePassword);
        }
        if (sysUser == null){
            //登陆失败
            return ResponseEntity.ok(ReturnResult.ok(ExceptionEnum.LOGINERROR));
        }
        //生成新的token值,放到Header中返回
        HttpHeaders httpHeaders = new HttpHeaders();
        String new_token = JWTUtil.generate(sysUser.getUserId());
        logger.info(new_token);

        httpHeaders.add(JWTUtil.Admin_Token, new_token);
        //浏览器有一个规则,自定义的key 不能被js识别获取
        //设置 让Axios 能识别key 让浏览器放过这个token的key
        httpHeaders.add("Access-Control-Expose-Headers",JWTUtil.Admin_Token);
        return new ResponseEntity<>(ReturnResult.ok(sysUser),httpHeaders, HttpStatus.OK);

    }
    /**
     * 查询所有有审核权限的账号
     */
    @GetMapping("/reviewer")
    public ResponseEntity<ReturnResult> selectWithReviewer() {
        List<SysUser> sysUserList = this.userService.getListWithReviewer();
        return ResponseEntity.ok(ReturnResult.ok(sysUserList));
    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        SysUser  sysUser = this.userService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok(sysUser));
    }

    /**
     * 新增或修改数据
     *
     * @param sysUser 实体对象
     * @return 新增结果
     */
    @PostMapping("save/update")
    public ResponseEntity<ReturnResult>  addOrUpdate(@RequestBody SysUser sysUser) {
         this.userService.saveOrUpdate(sysUser);
         return ResponseEntity.ok(ReturnResult.ok(sysUser));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult>  delete(@RequestParam("idList") List<Long> idList) {
        this.userService.removeByIds(idList);
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
        this.userService.removeById(id);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

