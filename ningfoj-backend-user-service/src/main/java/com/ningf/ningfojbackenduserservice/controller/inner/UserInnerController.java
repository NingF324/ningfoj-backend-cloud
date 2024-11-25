package com.ningf.ningfojbackenduserservice.controller.inner;

import com.ningf.ningfojbackendmodel.model.entity.User;
import com.ningf.ningfojbackendserviceclient.service.UserFeignClient;
import com.ningf.ningfojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @description: 仅内部调用的接口
 * @author: Lenovo
 * @time: 2024/11/22 上午10:30
 */
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;

    @Override
    @GetMapping("/get/id")
    public User getById(long userId) {
        return userService.getById(userId);
    }

    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
