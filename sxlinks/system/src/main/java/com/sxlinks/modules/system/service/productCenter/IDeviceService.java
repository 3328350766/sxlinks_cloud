package com.sxlinks.modules.system.service.productCenter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.common.domain.dto.response.device.DeviceRtResDto;
import com.sxlinks.modules.system.entity.productCenter.Device;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
public interface IDeviceService extends IService<Device> {

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
//    public boolean deleteProduct(String roleid);
//
//    /**
//     * 批量删除角色
//     * @param roleids
//     * @return
//     */
//    public boolean deleteBatchProduct(String[] roleids);
        public Map<String, List<DeviceRtResDto>> queryRtByDevCodes(List<String> deviceCodes);
    public List<DeviceRtResDto> queryRtByDevCode(String deviceCode) ;
}
