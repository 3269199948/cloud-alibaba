package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.entity.PmsCategory;
import com.demo.mapper.PmsCategoryMapper;
import com.demo.service.IPmsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <p>
 * 分类信息
 * </p>
 *
 * @author zhang
 * @since 2021-06-01
 */
@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements IPmsCategoryService {

    /**
     * 线程池
     */
    @Autowired
    private ThreadPoolExecutor executor;

    /**
     * 查询所有分类数据，并组装成树形结构
     *
     * @return 结果
     */
    @Override
    public List<PmsCategory> listTree() {
        //1.查出所有信息
        List<PmsCategory> entities = baseMapper.selectList(null);
        //2.组装结构
        return entities.stream().filter(category ->
                "0".equals(category.getParentCid())
        ).peek((menu) -> menu.setChildren(getChildren(menu, entities))).sorted(Comparator.comparingInt(menu ->
                (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
    }

    /**
     * 一个多线程任务
     *
     * @return 信息
     */
    @Override
    public String thread() {
        //随便做一些事情
        CompletableFuture<PmsCategory> categoryFuture = CompletableFuture.supplyAsync(() -> {
            PmsCategory pmsCategory = new PmsCategory();
            pmsCategory.setCatId("666");
            System.out.println("执行：categoryFuture 中...");
            //模拟耗时
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return pmsCategory;
        }, executor);
        //等待categoryFuture结果然后执行
        CompletableFuture<Void> testFuture = categoryFuture.thenAcceptAsync(res -> {
            System.out.println("categoryFuture 执行完成，通过其中数据我拿到了其他信息...");
        }, executor);
        //直接执行，无需等待其他
        CompletableFuture<Void> lastFuture = CompletableFuture.runAsync(() -> {
            System.out.println("我直接就可以执行，不必等待其他方法...");
        }, executor);
        try {
            CompletableFuture.allOf(categoryFuture, testFuture, lastFuture).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "我执行完了，请看控制台";
    }


    /**
     * 查询所有子分类
     *
     * @param root 当前分类
     * @param all  所有分类
     * @return 结果
     */
    private List<PmsCategory> getChildren(PmsCategory root, List<PmsCategory> all) {
        return all.stream().filter(category -> category.getParentCid().equals(root.getCatId())).peek(category -> {
            //1.找到子菜单
            category.setChildren(getChildren(category, all));
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
    }
}
