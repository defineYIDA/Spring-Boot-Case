package com.example.hello.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.hello.Mapper.UserMapper;
import com.example.hello.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class helloController {
    @Autowired
    UserMapper userMapper;
    @RequestMapping
    public String hello() {
         List<User> list=userMapper.selectList(null);
        return "Hello Spring-Boot"+list.toString();
    }

    @RequestMapping("/info")
    public Map<String, String> getInfo(@RequestParam String name) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    @RequestMapping("/list")
    public List<Map<String, String>> getList() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = null;
        for (int i = 1; i <= 5; i++) {
            map = new HashMap<>();
            map.put("name", "YIDA-" + i);
            list.add(map);
        }
        return list;
    }
}
