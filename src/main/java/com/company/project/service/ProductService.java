package com.company.project.service;
import com.company.project.model.Product;
import com.company.project.core.Service;


/**
 * Created by CodeGenerator on 2017/11/26.
 */
public interface ProductService extends Service<Product> {

    String createProduct(String productName, String desc);

    String updateProduct(int id, String productName, String desc);

    void publishToIot(String productKey, String deviceName, String content);
}
