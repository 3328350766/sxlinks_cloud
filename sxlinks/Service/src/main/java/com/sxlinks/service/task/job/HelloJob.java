package com.sxlinks.service.task.job;


import com.sxlinks.service.task.JobExecuter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * HelloJob
 *
 * @author zt
 * @version 2019/12/30 0030
 */
@Component
public class HelloJob extends JobExecuter {


    @Override
    public void execute(Map<String, Object> dataMap) throws Exception {

        /**
         Cfg cfg = cfgRepository.findById(1L).get();
         cfg.setCfgDesc(cfg.getCfgDesc()+"update by "+ DateUtil.getTime());
         cfgRepository.save(cfg);
         System.out.println("hello :"+JSON.toJSONString(dataMap));
         **/
        /**
        System.out.println("开始缓存数据到redis 开始时间:" + DateUtil.getTime());
        //缓存信息审核词库
        cacheWordAudit();
        System.out.println("结束缓存数据到redis 结束时间:" + DateUtil.getTime());
         **/
    }



}
