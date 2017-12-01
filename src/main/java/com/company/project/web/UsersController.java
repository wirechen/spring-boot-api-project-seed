package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Users;
import com.company.project.service.UsersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* Created by CodeGenerator on 2017/11/30.
*/
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Resource
    private UsersService usersService;

    @PostMapping("/regist")
    public Result add(@RequestBody Users users) {
        usersService.regist(users);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        String loginName = jsonObject.getString("login_name");
        String encryptedPassword = jsonObject.getString("encrypted_password");
        usersService.login(response, loginName, encryptedPassword);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        usersService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody Users users) {
        usersService.update(users);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Users users = usersService.findById(id);
        return ResultGenerator.genSuccessResult(users);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Users> list = usersService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
