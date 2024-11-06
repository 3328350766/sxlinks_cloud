package com.sxlinks.modules.api.controller.miniProgram;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.system.util.JwtUtil;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.PasswordUtil;
import com.sxlinks.common.util.RedisUtil;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.api.controller.miniProgram.vo.req.MiniEncryptionReqVo;
import com.sxlinks.modules.api.controller.miniProgram.vo.req.MiniPerfectInfoReqVo;
import com.sxlinks.modules.api.controller.miniProgram.vo.req.MiniSmsLoginReqVo;
import com.sxlinks.modules.api.controller.miniProgram.vo.resp.MiniLoginRespVo;
import com.sxlinks.modules.api.controller.miniProgram.vo.resp.MiniPerfectInfoRespVo;
import com.sxlinks.modules.api.controller.miniProgram.vo.resp.MiniUserInfoRespVo;
import com.sxlinks.modules.api.controller.miniProgram.vo.resp.MiniUserInfoVO;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.SysUser;
import com.sxlinks.modules.system.model.SysLoginModel;
import com.sxlinks.modules.system.service.ISysDepartService;
import com.sxlinks.modules.system.service.ISysDictService;
import com.sxlinks.modules.system.service.ISysUserService;
import com.sxlinks.modules.system.service.mini.MiniUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author gaoliang
 * @since 2018-12-17
 */
@RestController
@RequestMapping(value = "/miniProgram/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "小程序用户相关接口", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Validated
public class MiniUserController {

    @Resource
    private MiniUserService miniUserService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDepartService sysDepartService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysDictService sysDictService;
//    @ApiOperation("微信小程序-获取TOKEN(测试用)")
//    @GetMapping(value = "/dev")
//    public MiniLoginRespVo miniLoginForDev(@ApiParam(value = "手机号", required = true, example = "13100800900") @RequestParam String phone, @ApiParam(value = "地震项目ID", example = "1") @RequestParam(required = false) Long projectId) {
//        return miniUserService.miniLoginForDev(phone, projectId);
//    }

//    @ApiOperation("微信小程序登录接口-CODE换取临时TOKEN")
//    @PostMapping(value = "/login/{code}")
//    public String miniLogin(@ApiParam(value = "小程序CODE码") @PathVariable String code) {
//        return miniUserService.miniLogin(code);
//    }

    @ApiOperation("登录接口无验证码")
    @RequestMapping(value = "/loginNoCode", method = RequestMethod.POST)
    public Result<JSONObject> loginNoCode(@RequestBody SysLoginModel sysLoginModel){
        Result<JSONObject> result = new Result<JSONObject>();
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();

        //1. 校验用户是否有效
        //update-begin-author:baba date:20200601 for: 登录代码验证用户是否注销bug，if条件永远为false
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if(sysUser==null){ //当用户名不存在的时候，启用手机号登录 //修改时间:2023-03-20
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getPhone,username);
            sysUser = sysUserService.getOne(queryWrapper);
        }
        //update-end-author:baba date:20200601 for: 登录代码验证用户是否注销bug，if条件永远为false
        result = sysUserService.checkUserIsEffective(sysUser);
        if(!result.isSuccess()) {
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
        //update-begin--Author:liusq  Date:20210126  for：登录成功，删除redis中的验证码
        //redisUtil.del(realKey);
        //update-begin--Author:liusq  Date:20210126  for：登录成功，删除redis中的验证码
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);
        //baseCommonService.addLog("用户名: " + username + ",登录成功！", CommonConstant.LOG_TYPE_1, null,loginUser);
        //update-end--Author:baba  Date:20200714  for：登录日志没有记录人员
        return result;
    }

    @ApiOperation(value = "微信小程序授权手机号登录")
    @PostMapping("/phone")
    public MiniLoginRespVo authorizationBindPhone(@RequestBody @Valid MiniEncryptionReqVo encryptionVo) {
        return miniUserService.authorizationBindPhone(encryptionVo);
    }

//    @ApiOperation(value = "微信小程序授权手机号登录")
//    @PostMapping("/phone")
//    public MiniLoginRespVo authorizationBindPhone(@RequestBody @Valid MiniEncryptionReqVo encryptionVo) {
//        return miniUserService.authorizationBindPhone(encryptionVo);
//    }

    @ApiOperation(value = "获取短信验证码(测试环境-123456)")
    @GetMapping(value = "/sms")
    public void sendSms(@ApiParam(value = "手机号", defaultValue = "18888888888") @RequestParam(value = "phone") String phone) {
        miniUserService.sendSms(phone);
    }

    @ApiOperation(value = "微信小程序手机验证码登录")
    @PostMapping("/sms/login")
    public MiniLoginRespVo smsLogin(@RequestBody @Valid MiniSmsLoginReqVo smsLoginReqVo) {
        return miniUserService.smsLogin(smsLoginReqVo.getPhone(), smsLoginReqVo.getCode(), smsLoginReqVo.getProjectId());
    }

    @ApiOperation(value = "填写信息")
    @PostMapping("/perfect")
    public void perfectInfo(@RequestBody @Valid MiniPerfectInfoReqVo perfectInfoReqVo) {
        miniUserService.perfectInfo(perfectInfoReqVo);
    }

    @ApiOperation(value = "获取用户最后一次填写信息内容")
    @GetMapping("/latest/perfect")
    public MiniPerfectInfoRespVo latestPerfectInfo() {
        return miniUserService.latestPerfectInfo();
    }

//    @ApiOperation("微信小程序-获取用户信息")
//    @GetMapping(value = "/info")
//    public MiniUserInfoRespVo miniUserInfo(@ApiParam(value = "地震项目ID", example = "1") @RequestParam(required = false) Long projectId) {
//        return miniUserService.miniUserInfo(projectId);
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
        // 生成token
        String token = JwtUtil.sign(username, syspassword);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);

        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
        obj.put("departs", departs);
        if (departs == null || departs.size() == 0) {
            obj.put("multi_depart", 0);
        } else if (departs.size() == 1) {
            sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
            obj.put("multi_depart", 1);
        } else {
            //查询当前是否有登录部门
            // update-begin--Author:baba Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
            SysUser sysUserById = sysUserService.getById(sysUser.getId());
            if(oConvertUtils.isEmpty(sysUserById.getOrgCode())){
                sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
            }
            // update-end--Author:baba Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
            obj.put("multi_depart", 2);
        }
        obj.put("token", token);
        MiniUserInfoVO uv=new MiniUserInfoVO();
        BeanUtils.copyProperties(sysUser,uv);
        uv.setAccessToken(token);
        obj.put("userInfo", uv);
        obj.put("sysAllDictItems", sysDictService.queryAllDictItems());
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }

}