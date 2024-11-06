package com.sxlinks.common.domain.dto.response.user;

import lombok.Data;

import java.util.List;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: UserInfoResDto.java, v 0.1 2020-12-31  Exp $$
  */
@Data
public class UserInfoResDto {
    List<String> roles;
    String introduction;
    String avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
    String name;

}
