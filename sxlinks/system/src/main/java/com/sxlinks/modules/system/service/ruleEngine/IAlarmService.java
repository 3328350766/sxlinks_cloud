package com.sxlinks.modules.system.service.ruleEngine;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.ruleEngine.Alarm;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
public interface IAlarmService extends IService<Alarm> {

//    /**
//     * 导入 excel ，检查 roleCode 的唯一性
//     *
//     * @param file
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    Result importExcelCheckRoleCode(MultipartFile file, ImportParams params) throws Exception;
//
//    /**
//     * 删除角色
//     * @param roleid
//     * @return
//     */
//    public boolean deleteAlarm(String roleid);
//
//    /**
//     * 批量删除角色
//     * @param roleids
//     * @return
//     */
//    public boolean deleteBatchAlarm(String[] roleids);

    IPage<Alarm> custom(IPage<Alarm> iPage,Alarm alarm);

    Alarm getByAlarmId(Long alarmId);


    Integer del(Long alarmId);
}
