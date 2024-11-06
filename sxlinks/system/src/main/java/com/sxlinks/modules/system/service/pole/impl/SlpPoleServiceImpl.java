package com.sxlinks.modules.system.service.pole.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.modules.system.entity.pole.SlpPole;
import com.sxlinks.modules.system.mapper.pole.SlpPoleMapper;
import com.sxlinks.modules.system.service.pole.ISlpPoleService;
import org.apache.shiro.SecurityUtils;
import org.jeecgframework.minidao.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 灯杆信息Service业务层处理
 *
 * @author wll
 * @date 2022-12-13
 */
@Service
public class SlpPoleServiceImpl extends ServiceImpl<SlpPoleMapper, SlpPole> implements ISlpPoleService {

    @Autowired
    private SlpPoleMapper slpPoleMapper;

    /**
     * 查询灯杆信息
     *
     * @param poleId 灯杆信息主键
     * @return 灯杆信息
     */
    @Override
    public SlpPole selectSlpPoleByPoleId(Long poleId) {
        return slpPoleMapper.selectSlpPoleByPoleId(poleId);
    }

    /**
     * 查询灯杆信息列表
     *
     * @param slpPole 灯杆信息
     * @return 灯杆信息
     */
    @Override
    public List<SlpPole> selectSlpPoleList(SlpPole slpPole) {
        return slpPoleMapper.selectSlpPoleList(slpPole);
    }

    /**
     * 新增灯杆信息
     *
     * @param slpPole 灯杆信息
     * @return 结果
     */
    @Override
    public int insertSlpPole(SlpPole slpPole) {
        //雪花序列id
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        slpPole.setPoleId(idWorker.nextId() + "");
        //用户信息
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        slpPole.setCreateBy(loginUser.getUsername());
        slpPole.setCreateTime(new Date());
        return slpPoleMapper.insertSlpPole(slpPole);
    }

    /**
     * 修改灯杆信息
     *
     * @param slpPole 灯杆信息
     * @return 结果
     */
    @Override
    public int updateSlpPole(SlpPole slpPole) {
        return slpPoleMapper.updateSlpPole(slpPole);
    }

    /**
     * 批量删除灯杆信息
     *
     * @param poleIds 需要删除的灯杆信息主键
     * @return 结果
     */
    @Override
    public int deleteSlpPoleByPoleIds(Long[] poleIds) {
        return slpPoleMapper.deleteSlpPoleByPoleIds(poleIds);
    }

    /**
     * 删除灯杆信息信息
     *
     * @param poleId 灯杆信息主键
     * @return 结果
     */
    @Override
    public int deleteSlpPoleByPoleId(Long poleId) {
        return slpPoleMapper.deleteSlpPoleByPoleId(poleId);
    }
}
