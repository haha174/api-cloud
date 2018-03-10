package com.wen.cloud.web;

import com.wen.cloud.service.TokenService;
import com.wen.cloud.util.DataResponse;
import com.wen.cloud.util.IConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {
    @Autowired
    TokenService tokenService;
    @PostMapping("/login")
    public DataResponse<String> login(String userName, String password){
        return    tokenService.login(userName,password);
    }
    @PostMapping("/check")
    public DataResponse<String> check(String token){
        DataResponse<String> response= new DataResponse<>();
        response.setStatusCode(IConstants.RESPONSE_STATUS_CODE_FAILED);
        response.setStatusMsg("Access Token is expired. Please login again.");
        if(tokenService.checkToken(token)){
            response.setStatusCode(IConstants.RESPONSE_STATUS_CODE_SUCCESS);
            response.setStatusMsg(IConstants.RESPONSE_STATUS_CODE_FAILED_MSG);
            return response;
        }
        return   response;
    }

}
