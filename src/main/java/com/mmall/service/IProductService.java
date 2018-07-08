package com.mmall.service;

import com.github.pagehelper.PageInfo;
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

    /**
     * 分页查找产品信息（调用pageHelper插件）
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    /**
     * 根据id或者产品名查找产品信息（调用pageHelper插件）
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);
}
