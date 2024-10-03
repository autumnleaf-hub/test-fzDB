package com.fzy.fzprj;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SpringBootTest
public class GenerateCode {
    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;


    @Test
    void testGenerate() {
        FastAutoGenerator.create(url, username, password)
                .dataSourceConfig(builder ->{
                    builder.typeConvertHandler(((globalConfig, typeRegistry, metaInfo) -> {   // 自定义类型转换
                        IColumnType columnType = typeRegistry.getColumnType(metaInfo);
                        if (DbColumnType.BYTE.equals(columnType)) {  // 将 TINYINT 转换为 INTEGER
                            return DbColumnType.INTEGER;
                        }
                        return columnType;
                    }));
                })
                .globalConfig(builder -> {
                    builder.author("fzy") // 设置作者
                            .disableOpenDir() // 关闭运行成功后自动跳出的任务管理器窗口
                            //.enableSwagger()  // 开启 Swagger 模式
                            .outputDir(".\\src\\main\\java"); // 输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.fzy") // 设置父包名
                            .moduleName("fzprj") // 设置模块名
                            .entity("bean") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .controller("controller") // 设置 Controller 包名
                            .xml("mappers"); // 设置 Mapper XML 文件包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user", "competition", "user_comp") // 设置需要生成的表名
                            .addTablePrefix() // 增加过滤表前缀
                            .addFieldSuffix() // 增加过滤字段后缀
                            .entityBuilder()
                            .enableFileOverride() // 开启文件覆盖
                            .enableActiveRecord() // 开启 ActiveRecord 模式
                            .idType(IdType.ASSIGN_ID) // 设置主键类型
                            .versionColumnName("version") // 设置乐观锁字段名
                            .logicDeleteColumnName("status") // 设置逻辑删除字段名
                            .addTableFills(List.of(
                                    new Property("createTime", FieldFill.INSERT),
                                    new Property("updateTime", FieldFill.INSERT_UPDATE),
                                    new Property("status", FieldFill.INSERT),
                                    new Property("position", FieldFill.INSERT)
                            )) // 设置自动填充字段
                            .enableLombok() // 启用 Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .enableRestStyle() // 启用 REST 风格
                            .controllerBuilder()
                            .enableFileOverride() // 开启文件覆盖
                            .enableRestStyle() // 自动生成 @RestController 控制器
                            .serviceBuilder()
                            .enableFileOverride()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImp")
                            .mapperBuilder()
                            .enableFileOverride()
                            .enableMapperAnnotation()  // 开启 @Mapper 注解
                    ;
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }
}