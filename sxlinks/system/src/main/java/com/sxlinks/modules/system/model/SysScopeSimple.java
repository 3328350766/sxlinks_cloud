package com.sxlinks.modules.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 区域表
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
@Data
public class SysScopeSimple implements Serializable {

    private Integer level;

    private String parentCode;

    private String value;

    private String label;

    private List<SysScopeSimple> children;
}
