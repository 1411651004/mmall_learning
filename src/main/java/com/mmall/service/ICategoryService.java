package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

public interface ICategoryService {
    /**
     * 添加种类接口
     * @param categoryName
     * @param parentId
     * @return
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 更新品类名称接口
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 获取当前子节点的category（种类）信息接口，这里是平级的不递归
     * @param categoryId
     * @return
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 获取当前节点下的所有节点接口，包括子节点，用递归查找所有子节点
     * @param categoryId
     * @return
     */
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
