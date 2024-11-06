package com.sxlinks.storage.es;

import com.sxlinks.storage.entity.MessageReplyEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 用来处理消息回执
  * @author baba
  * @version Id: IMessageReplayService.java, v 0.1 2021-01-22  Exp $$
  */
public interface MessageReplyRepository extends ElasticsearchRepository<MessageReplyEntity, String> {

}
