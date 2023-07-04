package com.shujisoo.config;
import com.github.dreamyoung.mprelation.AutoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置ApplicationContext及实体类所在包
 * @author yimocha
 */
@Configuration
public class AutoMapperConfig {

    @Bean
    public AutoMapper autoMapper() {
        // 配置实体类所在包,多个地方可用,号隔开
        return new AutoMapper(new String[] { "com.shujisoo.entity" });
    }

}

