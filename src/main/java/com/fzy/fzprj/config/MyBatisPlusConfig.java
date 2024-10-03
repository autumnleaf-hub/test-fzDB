package com.fzy.fzprj.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fzy.fzprj.mpinterceptor.SqlInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@EnableTransactionManagement
@MapperScan("com.fzy.fzprj.mapper")
public class MyBatisPlusConfig {
    /**
     * 添加内置插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 防止全表更新与删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 分页插件
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        // 如果配置多个插件, 切记分页最后添加
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.addInterceptor(new SqlInterceptor());
        };
    }

    /**
     * jackson 配置。使用枚举类自己的 toString 方法，而不是直接使用枚举类的名字
     * 想要配置生效，还要给 enum 编写 toString 方法
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }


    /**
     * 自动填充处理器
     * 会对所有的实体类进行自动填充，因此如果某类中没有这些字段，需要添加判断
     * @return
     */
    @Bean
    public MetaObjectHandler getMetaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                setFieldValByName("createTime", LocalDateTime.now(), metaObject);
                setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
                setFieldValByName("status", 1, metaObject);
                // 判断是否有这个字段
                if (metaObject.hasGetter("position") && metaObject.getValue("position") == null) {
                    setFieldValByName("position", 0, metaObject);
                }
                if (metaObject.hasGetter("isOver") && metaObject.getValue("isOver") == null) {
                    setFieldValByName("isOver", 0, metaObject);
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            }
        };
    }
}
