package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetaolVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("iProductServiceImpl")
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {
            //判断子图是否为空
            if (StringUtils.isNotBlank(product.getSubImages())) {
                //取子图的第一张图片赋值给主图，当作主图使用
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }

            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccess("更新产品成功");
                } else {
                    return ServerResponse.createByErrorMessage("更新产品失败");
                }
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                return ServerResponse.createByErrorMessage("新增产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或者更新产品参数不正确");
    }

    public ServerResponse<String> setSalaStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

    public ServerResponse<ProductDetaolVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架，或者删除");
        }
        ProductDetaolVo productDetaolVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetaolVo);

    }

    private ProductDetaolVo assembleProductDetailVo(Product product) {
        ProductDetaolVo productDetaolVo = new ProductDetaolVo();
        productDetaolVo.setId(product.getId());
        productDetaolVo.setSubtitle(product.getSubtitle());
        productDetaolVo.setPrice(product.getPrice());
        productDetaolVo.setMainImage(product.getMainImage());
        productDetaolVo.setSubImages(product.getSubImages());
        productDetaolVo.setCategoryId(product.getCategoryId());
        productDetaolVo.setDetail(product.getDetail());
        productDetaolVo.setName(product.getName());
        productDetaolVo.setStatus(product.getStatus());
        productDetaolVo.setStock(product.getStock());

        productDetaolVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            productDetaolVo.setParentCategoryId(0);//默认是根节点
        } else {
            productDetaolVo.setParentCategoryId(category.getParentId());
        }
        productDetaolVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetaolVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetaolVo;
    }

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        //调用pageHelper的分页插件
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = new ArrayList<>();
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);

    }

    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    public ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        if (StringUtils.isNotBlank(productName)){
            //在这里就相当于%productName%，如果直接拼接字符串的话相当于newString在堆内存上开辟新空间
            //但是用stringbuilder的append方法拼接就不会开辟新内存空间，效率更高
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);
        List<ProductListVo> productListVoList = new ArrayList<>();
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);

    }
}
