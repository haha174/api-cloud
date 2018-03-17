package com.wen.cloud.service;

import com.wen.cloud.auth.JavaWebToken;
import com.wen.cloud.repository.RedisHandle;
import com.wen.cloud.util.DataResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisHandle redis;
    public boolean checkToken(String token){
        String toeknRe=(String)redis.get(token);
        if(StringUtils.isNotBlank(toeknRe)&&token.equals(toeknRe)){
            return true;
        }else{
            return false;
        }
    }
    public DataResponse<String> login(String username, String password){
        DataResponse<String> response =new  DataResponse<String>();

        String pass=(String)redis.get(username);
        if(StringUtils.isNotBlank(pass)&&pass.equals(password)){
            Map map=new HashMap<>();
            map.put("id",1);
            String token= JavaWebToken.createJavaWebToken(map);
            redis.set(token,token);
            logger.info("login success"+username);
            response.setValue(token);
        }else{
            logger.info("login error");
        }
        return response;
    }

}
