package com.sales.main.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/make")
public class CreatePwController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/pass")
    public Map<String, Object> generateKey(@RequestParam(value = "key") String key)  {

        Map<String, Object> resultMap = new HashMap<>();

        try {
            String password = passwordEncoder.encode(key);
            resultMap.put("password", password);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }
}
