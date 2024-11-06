package com.sxlinks.service.task.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sxlinks.service.task.JobExecuter;
import com.sxlinks.utils.DateUtil;
import com.sxlinks.utils.HttpKit;
import com.sxlinks.utils.StringUtils;
import com.sxlinks.dao.cache.impl.RedisDaoImpl;

import com.sxlinks.dao.system.CfgRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
将本地数据库表缓存到redis中
 */
@Component
public class ProxyIpToRedisJob extends JobExecuter {
    @Autowired
    private CfgRepository cfgRepository;
    @Autowired
    RedisDaoImpl redisDao ;
    @Override
    public void execute(Map<String, Object> dataMap) throws Exception {

        /**
        Cfg cfg = cfgRepository.findById(1L).get();
        cfg.setCfgDesc(cfg.getCfgDesc()+"update by "+ DateUtil.getTime());
        cfgRepository.save(cfg);
        System.out.println("hello :"+JSON.toJSONString(dataMap));
         **/
        System.out.println("开始获取代理IP到redis 开始时间:"+ DateUtil.getTime());
        //清除无效代理IP
        clearProxyIP();
        //提取有效代理IP
        extractIP();
        System.out.println("结束获取代理IP到redis 结束时间:"+DateUtil.getTime());
    }


    //提取代理IP
    public void extractIP(){
        //http://api.shenlongip.com/ip?key=a07cva5p&pattern=json&count=1

        String url=cfgRepository.findByCfgName("proxy.url").getCfgValue();
        Map m=new HashMap();
        m.put("key",cfgRepository.findByCfgName("proxy.key").getCfgValue()); //赋参数值
        m.put("pattern",cfgRepository.findByCfgName("proxy.pattern").getCfgValue());
        m.put("count",cfgRepository.findByCfgName("proxy.count").getCfgValue());
        //json返回格式{"code":200,"data":[{"ip":"175.168.241.49","port":24035}]}
        String content= HttpKit.sendGet(url,m);
        if(StringUtils.isNotNullOrEmpty(content)) {    //消息列表不为空
            JSONObject jo = JSON.parseObject(content);
            JSONArray ja = jo.getJSONArray("data");
            for(int i=0;i<ja.size();i++) {
                JSONObject ja1 = ja.getJSONObject(i);
                if (StringUtils.isNotNullOrEmpty(ja1)) {
                    ProxyIp p = new ProxyIp();
                    p.setIp(ja1.getString("ip"));
                    p.setPort(ja1.getString("port"));
                    p.setValidTime(DateUtil.getTime());
                    redisDao.hPut("proxyip",DateUtil.getTime(),JSON.toJSONString(p));
                    System.out.println("已添加新的有效-代理ip:"+p.getIp()+",port:"+p.getPort());
                }
            }
        }
//        Map m=new HashMap();
//        long count=wordAuditDao.count();
//        int start=0;
//        int size=1000;    //每次取1000条数
//        for(int i=0;i<count;i=i+size){
//            List lsWordAudit=wordAuditDao.findAllByStart(start,size);
//            for(int j=0;j<lsWordAudit.size();j++){
//                WordAudit wa=(WordAudit)lsWordAudit.get(j);
//                m.put(wa.getId().toString(),JSON.toJSONString(wa));
//            }
//            redisDao.hPutAll("word_audit",m);
//            start=start+size;
//        }

    }
    public void clearProxyIP(){
        //getSecondSub-获取距离当前时间的IP秒数
        HashMap m = (HashMap) redisDao.hGetAll("proxyip");
        for (Object mm : m.entrySet()) {
            String value = ((Map.Entry) mm).getValue().toString();
            ProxyIp info = JSONObject.parseObject(value, ProxyIp.class);
            if(DateUtil.getSecondSub(info.getValidTime(),DateUtil.getTime())>=150){ //大于当前时间150秒的IP，全部删掉
                redisDao.hDelete("proxyip",((Map.Entry) mm).getKey());  //删除当前IP
            }
            System.out.println("正在清理无效-代理ip:"+info.getIp()+",port:"+info.getPort());
        }
    }


}
