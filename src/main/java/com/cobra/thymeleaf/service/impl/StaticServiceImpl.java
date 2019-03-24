package com.cobra.thymeleaf.service.impl;

import com.cobra.thymeleaf.service.StaticService;
import com.cobra.thymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.net.URISyntaxException;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2019/3/24 18:12
 */
@Service
public class StaticServiceImpl implements StaticService {

    @Autowired
    private UserService userService;

    /**
     * 生成首页静态页面
     */
    @Override
    public void createIndexHtml() {
        try {

            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setPrefix("templates/");
            resolver.setSuffix(".html");
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);

            Context context = new Context();
            context.setVariable("users", userService.findAllUsers());

            /**获取输出目标文件输出流------开始*/
            String filepath = this.getClass().getResource("/").toURI().getPath() + "static/";
            File folder = new File(filepath);
            //如果文件夹不存在
            if (!folder.exists()) {
                folder.mkdir();
            }
            String indexFileName = "index.html";
            File indexHtml = new File(folder, indexFileName);
            //如果html文件不存在
            if (!indexHtml.exists()) {
                indexHtml.createNewFile();
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(indexHtml), "UTF-8"));
            /**获取输出目标文件输出流------结束*/

            templateEngine.process("list", context, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
