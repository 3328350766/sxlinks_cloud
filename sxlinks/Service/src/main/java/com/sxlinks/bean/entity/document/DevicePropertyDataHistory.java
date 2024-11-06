package com.sxlinks.bean.entity.document;


import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device_property_data_history")
@Data
@Proxy(lazy = false)
@EntityListeners(AuditingEntityListener.class)
public class DevicePropertyDataHistory {
    @Id
    @GeneratedValue
    private Long id;    //编号
    @Column
    private String name;    //名称
    @Column
    private String data;    //数据内容
    @Column(name = "device_name")
    private String deviceName;    //设备名称
    @Column(name = "machine_name")
    private String machineName;    //机器名称
    @Column(name = "data_type")
    private String dataType;    //数据类型
    @Column
    private String state;    //状态
    @Column(name = "machine_id")
    private Long machineId;    //机器ID
    @Column(name = "device_id")
    private Long deviceId;    //设备ID
    @Column(name = "create_time")
    private Date createTime;    //创建时间
    @Column(name = "create_by")
    private Long createBy;  //创建人
    @Column(name = "modify_time")
    private Date modifyTime;    //修改时间
    @Column(name = "modify_by")
    private Long modifyBy;  //修改人

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }
}
