package com.sxlinks.modules.api.controller.gateway;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CacheConstant;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.system.api.ISysBaseAPI;
import com.sxlinks.common.system.util.JwtUtil;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.*;
import com.sxlinks.common.util.encryption.EncryptedString;

import com.sxlinks.modules.base.service.BaseCommonService;

import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.SysUser;

import com.sxlinks.modules.system.model.SysLoginModel;
import com.sxlinks.modules.system.service.ISysDepartService;
import com.sxlinks.modules.system.service.ISysDictService;
import com.sxlinks.modules.system.service.ISysLogService;
import com.sxlinks.modules.system.service.ISysUserService;

import com.sxlinks.modules.system.util.RandImageUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author gaoliang
 * @since 2021-9-17
 */
@RestController
@RequestMapping("/gateway/api/user")
@Api(value = "网关管理", tags = {"公共接口"})
@Slf4j
public class GatewayLoginController {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private ISysLogService logService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysDepartService sysDepartService;
    @Autowired
    private ISysDictService sysDictService;

    @Resource
    private BaseCommonService baseCommonService;

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @ApiOperation(value = "登录", tags = "公共接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel) {
        Result<JSONObject> result;

        String username = sysLoginModel.getUsername();
        if (StringUtils.isBlank(username)) {
            username = sysLoginModel.getUsername();
        }
        String password = sysLoginModel.getPassword();
        //1. 校验用户是否有效
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        //queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.eq(SysUser::getDelFlag, 0);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if(sysUser==null){ //当用户名不存在的时候，启用手机号登录 //修改时间:2023-03-20
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getPhone,username);
            sysUser = sysUserService.getOne(queryWrapper);
        }
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        //2. 校验用户名或密码是否正确
        String userpassword = PasswordUtil.encrypt(sysUser.getUsername(), password, sysUser.getSalt());
        String syspassword = sysUser.getPassword();
        if (!syspassword.equals(userpassword)) {
            result.error500("用户名或密码错误");
            return result;
        }

        //用户登录信息
        userInfo(sysUser, result);

        if (result.isSuccess()) {
            //用户操作权限

            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(sysUser, loginUser);
            baseCommonService.addLog("用户名: " + username + ",登录成功！", CommonConstant.LOG_TYPE_1, null, loginUser);
//            sysUserService.updateLastLoginTime(sysUser.getId());
//            sysUserService.updateOnlineStatus(sysUser.getId(), true);
//            areaPlanUserService.recordLastLoginTime(loginUser);
//
//            //首次登录是否需要重置密码
//            boolean needRestPassword = redisUtil.sHasKey(Constants.RESET_PASSWORD_KEY, sysUser.getId());
//            result.getResult().put("needRestPassword", needRestPassword);
        }

        return result;
    }

    //退出登录
    @ApiOperation(value = "退出登录", tags = "公共接口")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (oConvertUtils.isEmpty(token)) {
            return Result.ok("退出登录成功！");
        }

        String username = JwtUtil.getUsername(token);
        LoginUser sysUser = sysBaseAPI.getUserByName(username);
        if (sysUser != null) {
            //update-begin--Author:wangshuai  Date:20200714  for：登出日志没有记录人员
            baseCommonService.addLog("用户名: " + sysUser.getRealname() + ",退出成功！", CommonConstant.LOG_TYPE_1, null, sysUser);
            //update-end--Author:wangshuai  Date:20200714  for：登出日志没有记录人员
            log.info(" 用户名:  " + sysUser.getRealname() + ",退出成功！ ");
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户登录Shiro权限缓存
            redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + sysUser.getId());
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));
            //调用shiro的logout
            SecurityUtils.getSubject().logout();

            //sysUserService.updateOnlineStatus(sysUser.getId(), false);
        }
        return Result.ok("退出登录成功！");
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("loginfo")
    public Result<JSONObject> loginfo() {
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject obj = new JSONObject();
        //update-begin--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        // 获取一天的开始和结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date dayEnd = calendar.getTime();
        // 获取系统访问记录
        Long totalVisitCount = logService.findTotalVisitCount();
        obj.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = logService.findTodayVisitCount(dayStart, dayEnd);
        obj.put("todayVisitCount", todayVisitCount);
        Long todayIp = logService.findTodayIp(dayStart, dayEnd);
        //update-end--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        obj.put("todayIp", todayIp);
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }


//    @ApiOperation("用户注册")
//    @PostMapping("/register")
//    public Result<JSONObject> register(@ApiParam(value = "APP用户注册参数信息") @Valid @RequestBody AppRegisterParam registerParam) {
//        Result<JSONObject> result = new Result<>();
//
//        String phone = registerParam.getPhone();
//        SysUser temp = sysUserService.getUserByPhone(phone);
//        if (temp != null) {
//            result.setMessage("该手机号已注册");
//            result.setSuccess(false);
//            return result;
//        }
//
//
//
//            result.success("注册成功");
//
//        return result;
//    }
    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private Result<JSONObject> userInfo(SysUser sysUser, Result<JSONObject> result) {
        String syspassword = sysUser.getPassword();
        String username = sysUser.getUsername();
        String token = JwtUtil.sign(username, syspassword);
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);

        // 获取用户部门信息
        JSONObject obj = new JSONObject();

        if (sysUser != null) {


            obj.put("token", token);
            obj.put("userInfo", sysUser);


        } else {
            result.error500("用户未配置区划信息");
            return result;
        }


//        obj.put("sysAllDictItems", sysDictService.queryAllDictItems());
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }

}