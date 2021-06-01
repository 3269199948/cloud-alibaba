package com.demo.web.control;


import com.demo.entity.PmsCategory;
import com.demo.service.IPmsCategoryService;
import com.demo.util.response.ResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 一个商品分类
 * </p>
 *
 * @author zhang
 * @since 2021-06-01
 */
@Api(tags = "试岗control")
@RefreshScope
@RestController
@RequestMapping(value = "index", produces = MediaType.APPLICATION_JSON_VALUE)
public class PmsCategoryController {

    @Value(value = "${user.name}")
    private String name;

    @Value(value = "${user.age}")
    private Integer age;

    /**
     * 分类信息
     */
    @Autowired
    private IPmsCategoryService iPmsCategoryService;

    /**
     * 一个测试方法
     *
     * @return 删除结果
     */
    @ApiOperation(value = "删除方法")
    @ResponseBody
    @GetMapping(value = "remove")
    public ResultSet<String> index() {
        return ResultSet.ok("删除成功");
    }

    /**
     * 查询树形数据结果
     *
     * @return 结构数据
     */
    @ApiOperation(value = "查询树形数据结果")
    @GetMapping(value = "query")
    public ResultSet<List<PmsCategory>> query() {
        return ResultSet.ok(iPmsCategoryService.listTree());
    }

    /**
     * 一个多线程任务
     *
     * @return 信息
     */
    @ApiOperation(value = "多线程任务")
    @GetMapping(value = "thread")
    public ResultSet<Object> thread() {
        return ResultSet.ok(iPmsCategoryService.thread());
    }

    /**
     * 获取配置中心数据
     * <p>
     * Data Id：demo-project.properties
     *
     * @return 信息
     */
    @ApiOperation(value = "获取配置中心数据")
    @GetMapping(value = "config")
    public ResultSet<Object> config() {
        System.out.println("姓名：" + name);
        System.out.println("年龄：" + age);
        return ResultSet.ok("获取完成，请查看控制台");
    }
}
