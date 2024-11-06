package com.sxlinks.modules.system.service.pole.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.modules.system.entity.pole.SlpPoleType;
import com.sxlinks.modules.system.mapper.pole.SlpPoleTypeMapper;
import com.sxlinks.modules.system.service.pole.ISlpPoleTypeService;
import org.apache.shiro.SecurityUtils;
import org.jeecgframework.minidao.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 灯杆类型Service业务层处理
 *
 * @author wll
 * @date 2022-12-13
 */
@Service
public class SlpPoleTypeServiceImpl extends ServiceImpl<SlpPoleTypeMapper, SlpPoleType> implements ISlpPoleTypeService {

    @Autowired
    private SlpPoleTypeMapper slpPoleTypeMapper;

    /**
     * 查询灯杆类型
     *
     * @param poleTypeId 灯杆类型主键
     * @return 灯杆类型
     */
    @Override
    public SlpPoleType selectSlpPoleTypeByPoleTypeId(Long poleTypeId) {
        return slpPoleTypeMapper.selectSlpPoleTypeByPoleTypeId(poleTypeId);
    }

    /**
     * 查询灯杆类型列表
     *
     * @param slpPoleType 灯杆类型
     * @return 灯杆类型
     */
    @Override
    public List<SlpPoleType> selectSlpPoleTypeList(SlpPoleType slpPoleType) {
        return slpPoleTypeMapper.selectSlpPoleTypeList(slpPoleType);
    }

    /**
     * 新增灯杆类型
     *
     * @param slpPoleType 灯杆类型
     * @return 结果
     */
    @Override
    public int insertSlpPoleType(SlpPoleType slpPoleType) {
        //雪花序列id
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        slpPoleType.setPoleTypeId(idWorker.nextId()+"");
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        slpPoleType.setCreateBy(loginUser.getUsername());
        return slpPoleTypeMapper.insertSlpPoleType(slpPoleType);
    }

    /**
     * 修改灯杆类型
     *
     * @param slpPoleType 灯杆类型
     * @return 结果
     */
    @Override
    public int updateSlpPoleType(SlpPoleType slpPoleType) {
        return slpPoleTypeMapper.updateSlpPoleType(slpPoleType);
    }

    /**
     * 批量删除灯杆类型
     *
     * @param poleTypeIds 需要删除的灯杆类型主键
     * @return 结果
     */
    @Override
    public int deleteSlpPoleTypeByPoleTypeIds(Long[] poleTypeIds) {
        return slpPoleTypeMapper.deleteSlpPoleTypeByPoleTypeIds(poleTypeIds);
    }

    /**
     * 删除灯杆类型信息
     *
     * @param poleTypeId 灯杆类型主键
     * @return 结果
     */
    @Override
    public int deleteSlpPoleTypeByPoleTypeId(Long poleTypeId) {
        return slpPoleTypeMapper.deleteSlpPoleTypeByPoleTypeId(poleTypeId);
    }
}
