package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Product;
import com.company.project.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2017/11/26.
*/
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Resource
    private ProductService productService;

    @PostMapping
    public Result add(@RequestBody JSONObject object) {
        String productName = object.getString("product_name");
        String desc = object.getString("desc");
        String result = productService.createProduct(productName, desc);
        if ("success".equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        productService.deleteById(id);
//        return ResultGenerator.genSuccessResult();
//    }

    @PutMapping
    public Result update(@RequestBody JSONObject object) {
        String productName = object.getString("product_name");
        String desc = object.getString("desc");
        int id = object.getInteger("id");
        String result = productService.updateProduct(id, productName, desc);
        if ("success".equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }

    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return ResultGenerator.genSuccessResult(product);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Product> list = productService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
