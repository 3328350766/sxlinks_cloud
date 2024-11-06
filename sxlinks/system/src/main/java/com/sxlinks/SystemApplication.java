package com.sxlinks;

import com.sxlinks.common.biz.EsUtil;
import lombok.extern.slf4j.Slf4j;
import com.sxlinks.common.util.oConvertUtils;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;

import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;



import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
* 单体启动类（采用此类启动为单体模式）
*/
@Slf4j
@SpringBootApplication
//@EnableCaching
//@SpringBootApplication(scanBasePackages = {"com.sxlinks.*","com.sxlinks.*"})
@ComponentScan(basePackages = {"com.sxlinks.config","com.sxlinks.interceptor","com.sxlinks.modules","com.sxlinks.dao","com.sxlinks.common",
        "com.sxlinks.plugin.es","com.sxlinks.storage"}
//        excludeFilters = {
        //要排除第二个redis
//                @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = {com.sxlinks.plugin.redis.RedisConfig.class})
//        }
)

@EntityScan(basePackages="com.sxlinks.bean.entity")
@EnableElasticsearchRepositories(basePackages = "com.sxlinks.storage.es")
@EnableScheduling
//@EnableJpaRepositories(basePackages= "com.sxlinks.dao")
//@EnableJpaAuditing

public class SystemApplication extends SpringBootServletInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(SystemApplication.class);
//    }

    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext application = SpringApplication.run(SystemApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = oConvertUtils.getString(env.getProperty("server.servlet.context-path"));
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Sxlinks-Cloud is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");

    }

    /**
     * 构造的时候传入，用于识别es的存储路径
     */
    @PostConstruct
    public void init(){
        EsUtil.setEnv("dev");
    }
}