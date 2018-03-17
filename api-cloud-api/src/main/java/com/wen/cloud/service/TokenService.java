package com.wen.cloud.service;


import com.alibaba.fastjson.JSON;
import com.wen.cloud.util.BaseResponse;
import com.wen.cloud.util.DataResponse;
import com.wen.cloud.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${token.app.name}")
    private String tokenAppName;

    @Value("${token.check.method}")
    private String tokenCheckMethod;


    @Autowired
    private HttpUtils httpUtils;
    public boolean checkToken(String token){
        Map<String,String> query=new HashMap<String,String>();
        query.put("token",token);
        try {
            String str=   httpUtils.doPostWithMap(gatewayUrl+"/"+tokenAppName+tokenCheckMethod,null,query,null);
            BaseResponse baseResponse= JSON.parseObject(str,BaseResponse.class);
            if(baseResponse!=null&&StringUtils.isNotBlank(baseResponse.getStatusMsg())&&baseResponse.getStatusCode().equalsIgnoreCase("200")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         return false;
    }

}
