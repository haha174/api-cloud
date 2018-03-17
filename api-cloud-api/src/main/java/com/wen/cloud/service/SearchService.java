package com.wen.cloud.service;

import com.alibaba.fastjson.JSON;
import com.wen.cloud.Bean.IPInfo;
import com.wen.cloud.Bean.ResultBean;
import com.wen.cloud.Bean.ShowAPIBean;
import com.wen.cloud.util.BaseResponse;
import com.wen.cloud.util.DataResponse;
import com.wen.cloud.util.HttpUtils;
import com.wen.cloud.util.IConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class SearchService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HttpUtils httpUtils;
    public   DataResponse<String>  getWeather(String city){
        DataResponse<String> dataResponse=new DataResponse<String>();
        dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_ERROR);
        dataResponse.setStatusMsg("service is busy,please try again later");
        if(StringUtils.isBlank(city)){
            dataResponse.setStatusMsg("city is empty");
        }
        Map<String,String> header=new HashMap<String,String>();
        Map<String,String> query=new HashMap<String,String>();
        header.put("Authorization",IConstants.AL_HEADER_FRONT+IConstants.AL_APPCODE);
        query.put("city",city);
        try {
            String result=httpUtils.doGetWithMap(IConstants.AL_WEATHER_URL,header,query);
            ResultBean resultBean= JSON.parseObject(result,ResultBean.class);
            if(resultBean.getStatus()==0){
                dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_SUCCESS);
                dataResponse.setStatusMsg("success");
                dataResponse.setValue(resultBean.getResult());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    public   DataResponse<String>  getTouTiaoNews(String type){
        DataResponse<String> dataResponse=new DataResponse<String>();
        dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_ERROR);
        dataResponse.setStatusMsg("service is busy,please try again later");
        Map<String,String> header=new HashMap<String,String>();
        Map<String,String> query=new HashMap<String,String>();
        header.put("Authorization",IConstants.AL_HEADER_FRONT+IConstants.AL_APPCODE);
        query.put("type",type);
        try {
            String result=httpUtils.doGetWithMap(IConstants.AL_TOUTIAO_URL,header,query);
            ResultBean resultBean= JSON.parseObject(result,ResultBean.class);
            if(resultBean.getStatus()==0){
                dataResponse.setValue(resultBean.getResult());
                dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_SUCCESS);

                dataResponse.setStatusMsg("success");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }


    public DataResponse<String> getIpInfo(String ip){
        Pattern pattern = Pattern.compile("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))");
        DataResponse<String> dataResponse=new DataResponse<String>();
        dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_ERROR);
        dataResponse.setStatusMsg("service is busy,please try again later");
        if(StringUtils.isBlank(ip)){
            dataResponse.setStatusMsg("ip is empty");
            return  dataResponse;
        }
        if(!pattern.matcher(ip).matches()){
            dataResponse.setStatusMsg("ip is Illegal");
            return  dataResponse;
        }
        Map<String,String> header=new HashMap<String,String>();
        Map<String,String> query=new HashMap<String,String>();
        header.put("Authorization",IConstants.AL_HEADER_FRONT+IConstants.AL_APPCODE);
        query.put("ip",ip);
        try {
            String result=httpUtils.doGetWithMap(IConstants.AL_IP_INFO_URL,header,query);
            IPInfo ipInfo= JSON.parseObject(result,IPInfo.class);
            if(ipInfo.getCode()==0){
                dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_SUCCESS);
                dataResponse.setValue(ipInfo.getData());
                dataResponse.setStatusMsg("success");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }
    public DataResponse<String> getQQMusic(String topid,String keyword){
        DataResponse<String> dataResponse=new DataResponse<String>();
        dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_ERROR);
        dataResponse.setStatusMsg("service is busy,please try again later");
        Map<String,String> header=new HashMap<String,String>();
        Map<String,String> query=new HashMap<String,String>();
        String     url=IConstants.AL_QQMUSIL_TOP_URL;
        query.put("topid",topid);
        if(StringUtils.isNotBlank(keyword)){
            query.put("keyword",keyword);
            url=IConstants.AL_QQMUSIL_KEYWORD_URL;
        }
        header.put("Authorization",IConstants.AL_HEADER_FRONT+IConstants.AL_APPCODE);
        try {
            String result=httpUtils.doGetWithMap(url,header,query);
            ShowAPIBean showAPIBean= JSON.parseObject(result,ShowAPIBean.class);
            if(showAPIBean.getShowapi_res_code()==0){
                dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_SUCCESS);
                dataResponse.setStatusMsg("success");
                dataResponse.setValue(showAPIBean.getShowapi_res_body());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }



    public DataResponse<String> getTalk(String question){
        DataResponse<String> dataResponse=new DataResponse<String>();
        dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_ERROR);
        dataResponse.setStatusMsg("service is busy,please try again later");
        if(StringUtils.isBlank(question)){
            dataResponse.setStatusMsg("question is not empty");
        }
        Map<String,String> header=new HashMap<String,String>();
        Map<String,String> query=new HashMap<String,String>();
        header.put("Authorization",IConstants.AL_HEADER_FRONT+IConstants.AL_APPCODE);
        query.put("question",question);
        try {
            String result=httpUtils.doGetWithMap(IConstants.AL_QUESTION_URL,header,query);
            ResultBean resultBean= JSON.parseObject(result,ResultBean.class);
            if(resultBean.getStatus()==0){
                dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_SUCCESS);
                dataResponse.setStatusMsg("success");
                dataResponse.setValue(resultBean.getResult());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }
    public DataResponse<String> getRecipe(String keyword,int num,int start){
        DataResponse<String> dataResponse=new DataResponse<String>();
        dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_ERROR);
        dataResponse.setStatusMsg("service is busy,please try again later");
        if(StringUtils.isBlank(keyword)){
            dataResponse.setStatusMsg("keyword is not empty");
        }
        Map<String,String> header=new HashMap<String,String>();
        Map<String,String> query=new HashMap<String,String>();
        header.put("Authorization",IConstants.AL_HEADER_FRONT+IConstants.AL_APPCODE);
        query.put("keyword",keyword);
        query.put("num",num+"");
        query.put("start",start+"");
        try {
            String result=httpUtils.doGetWithMap(IConstants.AL_RECIPE_URL,header,query);
            ResultBean resultBean= JSON.parseObject(result,ResultBean.class);
            if(resultBean.getStatus()==0){
                dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_SUCCESS);
                dataResponse.setStatusMsg("success");
                dataResponse.setValue(resultBean.getResult());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }


    public DataResponse<String> getExpress(String number,String type){
        DataResponse<String> dataResponse=new DataResponse<String>();
        dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_ERROR);
        dataResponse.setStatusMsg("service is busy,please try again later");
        if(StringUtils.isBlank(number)){
            dataResponse.setStatusMsg("express number is not empty");
        }
        Map<String,String> header=new HashMap<String,String>();
        Map<String,String> query=new HashMap<String,String>();
        header.put("Authorization",IConstants.AL_HEADER_FRONT+IConstants.AL_APPCODE);
        query.put("number",number);
        query.put("type",type+"");
        try {
            String result=httpUtils.doGetWithMap(IConstants.AL_EXPRESS_URL,header,query);
            ResultBean resultBean= JSON.parseObject(result,ResultBean.class);
            if(resultBean.getStatus()==0){
                dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_SUCCESS);
                dataResponse.setStatusMsg("success");
                dataResponse.setValue(resultBean.getResult());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

}
