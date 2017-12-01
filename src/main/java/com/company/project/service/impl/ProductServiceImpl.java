package com.company.project.service.impl;

import com.company.project.aliyun.IotHub;
import com.company.project.dao.ProductMapper;
import com.company.project.model.Product;
import com.company.project.service.ProductService;
import com.company.project.core.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


/**
 * Created by CodeGenerator on 2017/11/26.
 */
@Service
@Transactional
public class ProductServiceImpl extends AbstractService<Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;
    @Autowired
    private IotHub iotHub;

    @Override
    public String createProduct(String productName, String desc) {

        //IOT创建product
        Map<String, Object> map = iotHub.createNewProduct(productName, desc);
        String info = map.get("info").toString();
        if ("success".equals(info)) {
            //同步到本地数据库
            Product product = new Product();
            product.setProductName(productName);
            product.setProductDesc(desc);
            product.setProductKey(map.get("key").toString());
            product.setCreateTime(new Date());
            save(product);
        }
        return info;
    }

    @Override
    public String updateProduct(int id, String productName, String desc) {

        //查找productKey
        Product product = findById(id);
        String productKey = product.getProductKey();

        //IOT修改product
        String result = iotHub.updateProduct(productKey, productName, desc);

        if ("success".equals(result)) {
            //同步本地数据库
            product.setProductName(productName);
            product.setProductDesc(desc);
            product.setModifyTime(new Date());
            update(product);
        }
        return result;
    }
}
