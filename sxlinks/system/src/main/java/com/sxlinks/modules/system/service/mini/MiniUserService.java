package com.sxlinks.modules.system.service.mini;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.exception.Asserts;
import com.sxlinks.common.exception.MiniException;
import com.sxlinks.common.system.util.HashIdsUtils;
import com.sxlinks.common.system.util.JwtUtil;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.DateUtils;
import com.sxlinks.common.util.RedisUtil;
import com.sxlinks.modules.api.controller.miniProgram.vo.req.MiniEncryptionReqVo;
import com.sxlinks.modules.api.controller.miniProgram.vo.req.MiniPerfectInfoReqVo;
import com.sxlinks.modules.api.controller.miniProgram.vo.resp.MiniLoginRespVo;
import com.sxlinks.modules.api.controller.miniProgram.vo.resp.MiniPerfectInfoRespVo;
import com.sxlinks.modules.enums.*;
import com.sxlinks.modules.system.entity.SysUser;
//import com.sxlinks.modules.system.entity.project.Project;
//import com.sxlinks.modules.system.entity.project.ProjectNotice;
//import com.sxlinks.modules.system.entity.project.ProjectNoticeRead;
//import com.sxlinks.modules.system.entity.project.ProjectUserRole;
import com.sxlinks.modules.system.mapper.SysUserMapper;
//import com.sxlinks.modules.system.mapper.project.ProjectMapper;
//import com.sxlinks.modules.system.mapper.project.ProjectNoticeMapper;
//import com.sxlinks.modules.system.mapper.project.ProjectNoticeReadMapper;
//import com.sxlinks.modules.system.mapper.project.ProjectUserRoleMapper;
import com.sxlinks.modules.system.util.RegexUtil;
import com.sxlinks.modules.system.util.sms.CodeTypeEnum;
import com.sxlinks.modules.system.util.sms.SmsUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/20
 */
@Service
public class MiniUserService {
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;
    private static final long CACHE_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000 - (5 * 60 * 1000);
    /**
     * 临时TOKEN
     */
    private static final long TEMPORARY_TOKEN_EXPIRE_TIME = 5 * 60 * 1000;
    private final String USER_SESSION_KEY = "USER:SESSION_KEY";

    private final String JWT_SECRET = "SafeEValUateMINijWtUser";
    /**
     * 临时SECRET
     */
    private final String TEMPORARY_JWT_SECRET = "TEmPOrARySafeEValUateMINijWtUser";

    /**
     * 临时TOKEN_KEY
     */
    private final String TEMPORARY_USER_SESSION_KEY = "TEMPORARY_USER_SESSION_KEY:";

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private WxMaService wxMaService;

    @Resource
    private RedisUtil redisUtil;

//    @Resource
//    private ProjectMapper projectMapper;
//
//    @Resource
//    private ProjectUserRoleMapper projectUserRoleMapper;
//
//    @Resource
//    private ProjectNoticeMapper projectNoticeMapper;
//
//    @Resource
//    private ProjectNoticeReadMapper projectNoticeReadMapper;


    public String miniLogin(String code) {
        try {
            WxMaJscode2SessionResult wxResult = wxMaService.jsCode2SessionInfo(code);
            String openId = wxResult.getOpenid();
            String sessionKey = wxResult.getSessionKey();

            //生成临时TOKEN
            String token = JwtUtil.sign(openId, TEMPORARY_JWT_SECRET, TEMPORARY_TOKEN_EXPIRE_TIME);
            redisUtil.set(TEMPORARY_USER_SESSION_KEY + openId, sessionKey, TEMPORARY_TOKEN_EXPIRE_TIME);

            return token;
        } catch (WxErrorException e) {
            throw new MiniException(501, "微信登录失败:" + e.getMessage());
        }
    }

    public MiniLoginRespVo authorizationBindPhone(MiniEncryptionReqVo encryptionVo) {
        String code = encryptionVo.getCode();
        Long projectId = encryptionVo.getProjectId();

        String phone;
        try {
            WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getNewPhoneNoInfo(code);
            phone = phoneNoInfo.getPhoneNumber();
        } catch (Exception e) {
            throw new MiniException(500, "获取微信手机号失败");
        }
        Asserts.hasLength(phone, 500, "获取微信手机号失败");

        return loginByPhone(projectId, phone);
    }

//    public MiniLoginRespVo authorizationBindPhone(MiniEncryptionReqVo encryptionVo) {
//        String encryptedData = encryptionVo.getEncryptedData();
//        String iv = encryptionVo.getIv();
//        String temporaryToken = encryptionVo.getTemporaryToken();
//
//        Long projectId = encryptionVo.getProjectId();
//
//        String username = JwtUtil.getUsername(temporaryToken);
//        Asserts.hasLength(username, 501, "TOKEN无效");
//
//        boolean verifyFlag = JwtUtil.verify(temporaryToken, username, TEMPORARY_JWT_SECRET);
//        Asserts.isTrue(verifyFlag, 501, "TOKEN无效");
//
//        String key = TEMPORARY_USER_SESSION_KEY + username;
//
//        String sessionKey = String.valueOf(redisUtil.get(key));
//        redisUtil.del(key);
//
//        Asserts.hasLength(sessionKey, 501, "TOKEN无效");
//
//        String phone;
//        try {
//            WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
//
//            phone = phoneNoInfo.getPhoneNumber();
//        } catch (Exception e) {
//            throw new MiniException(501, "TOKEN无效");
//        }
//        Asserts.hasLength(phone, 501, "TOKEN无效");
//
//        return loginByPhone(projectId, phone);
//    }

    private MiniLoginRespVo loginByPhone(Long projectId, String phone) {

        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, phone));

        MiniLoginRespVo miniLoginRespVo = new MiniLoginRespVo();
        if (user == null) {
            //创建用户
            SysUser temp = new SysUser();
            temp.setPhone(phone);
            temp.setDelFlag(0);
            temp.setUserIdentity(UserIdentityEnum.User.getCode());
            temp.setStatus(1);
            sysUserMapper.insert(temp);

            miniLoginRespVo.setPhone(phone);
        } else {

            Asserts.isTrue(Objects.equals(user.getStatus(), 1), 500, "账号已被锁定,请联系管理员!");

//            MiniUserInfoRespVo miniUserInfoRespVo = miniUserInfoForLogin(projectId, user);
//            BeanUtils.copyProperties(miniUserInfoRespVo, miniLoginRespVo);
        }
        String token = buildAccessToken(phone);
        miniLoginRespVo.setToken(token);

        return miniLoginRespVo;
    }

    public MiniLoginRespVo smsLogin(String phone, String code, Long projectId) {
        validSmsCode(phone, code, CodeTypeEnum.Login);

        Integer isExist = sysUserMapper.selectCount(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, phone).eq(SysUser::getDelFlag, DelFlagEnum.NotDelete.getCode()));
        Asserts.isTrue(isExist > 0, 500, "手机号不存在或已被删除");

        return loginByPhone(projectId, phone);
    }

    public void sendSms(String phone) {
        Asserts.isTrue(RegexUtil.checkPhone(phone), 500, "手机号格式错误");

        //校验手机号是否存在
        Integer isExist = sysUserMapper.selectCount(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, phone).eq(SysUser::getDelFlag, DelFlagEnum.NotDelete.getCode()));
        Asserts.isTrue(isExist > 0, 500, "手机号不存在或已被删除");

        SmsUtils.validUserDayNum(phone);

        String code = "123456";
        if (SmsUtils.isProdMode()) {
            code = RandomStringUtils.randomNumeric(6);
            //TODO 发送短信
            SmsUtils.sendSms(phone, code, "login");
        }
        redisUtil.set(SmsUtils.generateCacheSmsKey(CodeTypeEnum.Login.name(), phone), code, SmsUtils.getExpires());
    }

    public void perfectInfo(MiniPerfectInfoReqVo perfectInfoReqVo) {
        //项目内角色
        Integer userType = perfectInfoReqVo.getUserType();
        UserTypeEnum userTypeEnum = UserTypeEnum.parse(userType);
        Asserts.isTrue(userTypeEnum == UserTypeEnum.Expert || userTypeEnum == UserTypeEnum.LocalContact || userTypeEnum == UserTypeEnum.LocalHousingContact, 500, "用户角色选择有误");

        String realname = perfectInfoReqVo.getRealname();
        Asserts.isTrue(realname.length() <= 10, 500, "姓名长度不能大于10");

        String post = perfectInfoReqVo.getPost();
        Asserts.isTrue(post.length() <= 30, 500, "职务长度不能大于30");

        //专家
        if (userTypeEnum == UserTypeEnum.Expert) {
            String unit = perfectInfoReqVo.getUnit();
            Asserts.hasLength(unit, 500, "单位不能为空");
            Asserts.isTrue(unit.length() <= 30, 500, "单位长度不能大于30");

            String major = perfectInfoReqVo.getMajor();
            Asserts.hasLength(major, 500, "专业不能为空");
            Asserts.isTrue(major.length() <= 30, 500, "专业长度不能大于30");

            String autograph = perfectInfoReqVo.getAutograph();
            Asserts.hasLength(autograph, 500, "签名不能为空");

            Integer age = perfectInfoReqVo.getAge();
            Asserts.isTrue(age != null && age <= 100, 500, "年龄填写有误");
        }

        SysUser user = getSysUserBySession();

        //校验地震状态(评估中)
//        Long projectId = perfectInfoReqVo.getProjectId();
//        Project project = projectMapper.selectById(projectId);
//        Asserts.isTrue(project != null && Objects.equals(project.getState(), ProjectStateEnum.Evaluating.getCode()), 500, "地震不存在或评估已完成");
//
//        ProjectUserRole temp = projectUserRoleMapper.selectOne(new LambdaQueryWrapper<ProjectUserRole>().eq(ProjectUserRole::getPhone,  user.getPhone())
//                .eq(ProjectUserRole::getProjectId, projectId)
//                .in(ProjectUserRole::getStatus, Lists.newArrayList(UserProjectStateEnum.WaitGrouped.getCode(),UserProjectStateEnum.Evaluating.getCode()))
//        );
//        Asserts.isNull(temp, 500, "不能在一个项目内担任多种角色");
//
//        ProjectUserRole projectUserRole = new ProjectUserRole();
//
//        projectUserRole.setProjectId(projectId);
//        projectUserRole.setUserId(user.getId());
//        projectUserRole.setUserType(userType);
//        projectUserRole.setRealName(realname);
//        projectUserRole.setPhone(user.getPhone());
//
//        if (userTypeEnum == UserTypeEnum.Expert) {
//            projectUserRole.setUnit(perfectInfoReqVo.getUnit());
//            projectUserRole.setMajor(perfectInfoReqVo.getMajor());
//            projectUserRole.setAutograph(perfectInfoReqVo.getAutograph());
//            projectUserRole.setAge(perfectInfoReqVo.getAge());
//        }
//        projectUserRole.setPost(post);
//
//        projectUserRoleMapper.insert(projectUserRole);
    }

    public MiniPerfectInfoRespVo latestPerfectInfo() {
        SysUser user = getSysUserBySession();
//        ProjectUserRole projectUserRole = getProjectUserRoleOrLatestProjectUserRole(null, user.getPhone());
//
//        if (projectUserRole != null) {
//            MiniPerfectInfoRespVo miniPerfectInfoRespVo = new MiniPerfectInfoRespVo();
//            miniPerfectInfoRespVo.setUserType(projectUserRole.getUserType());
//            miniPerfectInfoRespVo.setRealname(projectUserRole.getRealName());
//            miniPerfectInfoRespVo.setAge(projectUserRole.getAge());
//            miniPerfectInfoRespVo.setUnit(projectUserRole.getUnit());
//            miniPerfectInfoRespVo.setMajor(projectUserRole.getMajor());
//            miniPerfectInfoRespVo.setPost(projectUserRole.getPost());
//            miniPerfectInfoRespVo.setAutograph(projectUserRole.getAutograph());
//
//            return miniPerfectInfoRespVo;
//        }

        return null;
    }

    public void validSmsCode(String phone, String code, CodeTypeEnum codeType) {
        Asserts.isTrue(RegexUtil.checkPhone(phone), 500, "手机号格式错误");

        String key = SmsUtils.generateCacheSmsKey(codeType.name(), phone);
        String cacheCode = String.valueOf(redisUtil.get(key));
        Asserts.isTrue(StringUtils.equals(code, cacheCode), 500, "验证码错误");

        redisUtil.del(key);
    }

    public SysUser getSysUserBySession() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, loginUser.getPhone()));
        Asserts.notNull(sysUser, 501, "TOKEN无效");

        return sysUser;
    }

    public String getUserId() {
        return getSysUserBySession().getId();
    }

    private String buildAccessToken(String phone) {
        String encryptPhone = HashIdsUtils.userPhoneEncode(phone);
        String token = JwtUtil.sign(encryptPhone, JWT_SECRET, EXPIRE_TIME);
        redisUtil.set(CommonConstant.PREFIX_MINI_USER_TOKEN + ":" + phone + ":" + token, DateUtils.formatDateTime(), CACHE_EXPIRE_TIME);
        return token;
    }



}
