package com.sxlinks.common.util.security.entity;

import lombok.Data;

@Data
public class SecurityReq {
    private String data;
    private String pubKey;
    private String signData;
    private String aesKey;
}
