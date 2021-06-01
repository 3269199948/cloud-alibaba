package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.entity.PmsCategory;

import java.util.List;

/**
 * <p>
 * 分类信息
 * </p>
 *
 * @author zhang
 * @since 2021-06-01
 */
public interface IPmsCategoryService extends IService<PmsCategory> {

    /**
     * 查询所有分类数据，并组装成树形结构
     *
     * @return 结果
     */
    List<PmsCategory> listTree();

    /**
     * 一个多线程任务
     *
     * @return 信息
     */
    String thread();
}
