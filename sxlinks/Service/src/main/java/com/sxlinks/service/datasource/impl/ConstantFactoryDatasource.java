//package com.sxlinks.service.datasource.impl;
//
//import com.sxlinks.bean.constant.cache.CacheKey;
//import com.sxlinks.utils.StringUtils;
//import com.sxlinks.utils.cache.TimeCacheMap;
//import com.sxlinks.bean.entity.datasource.Website;
//
//import com.sxlinks.bean.vo.SpringContextHolder;
//
//import com.sxlinks.dao.datasource.WebsiteRepository;
//import com.sxlinks.service.datasource.IConstantFactoryDatasource;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//
///**
// * 常量的生产工厂
// *
// * @author gaoliang
// * @date 2019年2月28日
// */
//@Component
//@DependsOn("springContextHolder")
//@CacheConfig
//public class ConstantFactoryDatasource implements IConstantFactoryDatasource {
//    public static TimeCacheMap<String, String> cache = new TimeCacheMap<String, String>(3600, 2);
//    private WebsiteRepository websiteRepository = SpringContextHolder.getBean(WebsiteRepository.class);
//
//    public static IConstantFactoryDatasource me() {
//        return SpringContextHolder.getBean("constantFactoryDatasource");
//    }
//
//    public String get(String key) {
//        return cache.get(key);
//    }
//
//    public void set(String key, String val) {
//        cache.put(key, val);
//
//    }
//
//    /**
//     * 根据网站栏目管理id获取网站管理名称
//     * @author gaoliang
//     * @Date 2019/5/28
//     */
//    @Override
//    public String getWebsiteById(Long websiteId) {
//        String val = get(CacheKey.SYS_USER_NAME + websiteId);
//        if (StringUtils.isNotEmpty(val)) {
//            return val;
//        }
//
//        Website website = getWebsite(websiteId);
//        if (website != null) {
//            val = website.getName();
//            set(CacheKey.SYS_USER_NAME + websiteId, val);
//            return val;
//        }
//
//
//        return "--";
//    }
//
//    private Website getWebsite(Long id) {
//        Optional<Website> optionalWebsite = websiteRepository.findById(id);
//        if (optionalWebsite.isPresent()) {
//            Website website = optionalWebsite.get();
//            return website;
//        }
//        return null;
//    }
//
//
//
//}
