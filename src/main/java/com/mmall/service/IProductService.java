package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetaolVo;

public interface IProductService {

    /**
     * 新增或更新产品参数
     * @param product
     * @return
     */
    ServerResponse saveOrUpdateProduct(Product product);

    /**
     * 更新产品销售状态（在售status1、下架status2、删除status3）
     * @param productId
     * @param status
     * @return
     */
    ServerResponse<String> setSalaStatus(Integer productId,Integer status);

    /**
     * 获取产品详情，也就是详细信息
     * @param productId
     * @return
     */
    ServerResponse<ProductDetaolVo> manageProductDetail(Integer productId);
}
