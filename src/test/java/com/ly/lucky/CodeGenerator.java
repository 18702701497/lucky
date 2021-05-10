package com.ly.lucky;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author ly
 * @return MP提供的代码生成器
 * @create 2021/3/20 12:18
 */
public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//获取到项目的工作路径（储存地址）
        gc.setOutputDir(projectPath + "/src/main/java");//生成代码的项目路径
        gc.setAuthor("liuyang");//会在生成类的注释中加上这个作者名
        gc.setOpen(false);//生成代码后是否打开这个文件夹
        gc.setServiceName("%sService");//service类自动生成时会默认在前面加‘i’,这里设置一下
        //gc.setDateType(DateType.ONLY_DATE);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置，这个都懂
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/mp_spingboot0307?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("accp");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.ly");//这里是包的前缀，小项目我只有lucky一个包，多个包执行多次
        mpg.setPackageInfo(pc);

        // 自定义配置；参数、模板自定义时使用，基本上绝大部分需求都能满足，此处不用
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/dao.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {//配置生成xml文件的路径，不写默认到main/java下
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/dao/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileCreate(new IFileCreate() {//覆盖方法，正常第一次使用注释掉即可，默认存在就不覆盖
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                return true;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//表名带下划线的会转驼峰，实体类首字母大写
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//设置表中字段的，与上边同理

        if(scanner("是否继承基类").equalsIgnoreCase("y")){//判断是否需要父类实体；把实体类中相同的字段全放在一个父类中避免代码冗余
            strategy.setSuperEntityClass("com.ly.lucky.entity.BaseEntity");//父类实体的路径
            //写于父类的字段，即实体类中相同的字段
            strategy.setSuperEntityColumns("create_time","modified_time","create_account_id","modified_account_id","deleted");
        }

        strategy.setEntityLombokModel(true);//是否使用lambok模式生成实体
        //使用RestController注解会导致Controller中的方法全返回json格式，就无法返回jsp页面了
        strategy.setRestControllerStyle(false);//是否使用@RestController注解
        // Controller的公共父类，有没有抽象的一些方法
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");

        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);//生成的controllerRequstMapping中是否是驼峰的
        //设置逻辑删除字段，如果生成的实体类中有deleted属性，就会在上面加上逻辑删除注解
        strategy.setLogicDeleteFieldName("deleted");

        ArrayList<TableFill> tableFills = new ArrayList<>();
        //这是是设置这四个属性在插入和修改的时候自动填充，设置完会在对应的属性上加上相应的注解
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill modifiedTime = new TableFill("modified_time", FieldFill.UPDATE);
        TableFill createAccountId = new TableFill("create_account_id", FieldFill.INSERT);
        TableFill modifiedAccountId = new TableFill("modified_account_id", FieldFill.UPDATE);
        tableFills.add(createTime);
        tableFills.add(modifiedTime);
        tableFills.add(createAccountId);
        tableFills.add(modifiedAccountId);
        strategy.setTableFillList(tableFills);


        //strategy.setTablePrefix(pc.getModuleName() + "_");//设置表名前缀
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());//使用什么模板引擎，使用的是Freemarker
        mpg.execute();
    }
}
