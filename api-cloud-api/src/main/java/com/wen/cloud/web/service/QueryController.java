package com.wen.cloud.web.service;

import com.alibaba.fastjson.JSON;
import com.wen.cloud.Bean.IPInfo;
import com.wen.cloud.Bean.ResultBean;
import com.wen.cloud.service.SearchService;
import com.wen.cloud.util.BaseResponse;
import com.wen.cloud.util.DataResponse;
import com.wen.cloud.util.HttpUtils;
import com.wen.cloud.util.IConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/service/query")
public class QueryController {
    @Value("${token.header}")
    private String tokenHeader;

    @Value("${token.name}")
    private String tokenName;

    @Value("${test.url}")
    private String testUrl;

    @Autowired
    private SearchService searchService;


    @Autowired
    private HttpUtils httpUtils;

    @ApiOperation("全国3000多个省市的实时天气预报，未来7天、未来24小时天气，穿衣、运动、洗车、感冒、空气污染扩散、紫外线等指数查询接口，可按地名、经纬度、IP查询，30分钟更新一次，中国气象局权威数据")
    @GetMapping("/getWeather")
    public   DataResponse<String> getWeather(HttpServletRequest request, @RequestParam(required = true)@ApiParam("要查询的城市名称 如南京") String city){

        String authToken = request.getHeader(this.tokenHeader);
        DataResponse<String> dataResponse=new DataResponse<String>();
        dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_ERROR);
        dataResponse.setStatusMsg("service is busy,please try again later");
        if(StringUtils.isBlank(city)){
            dataResponse.setStatusMsg("city is empty");
        }
        Map<String,String> header=new HashMap<String,String>();
        Map<String,String> query=new HashMap<String,String>();
        header.put("Authorization",authToken);
        header.put("token",authToken);
        query.put("city",city);
        try {
            String result=httpUtils.doGetWithMap(testUrl+"/getWeather",header,query);
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

    @ApiOperation("根据用户提供的IP地址，快速查询出该IP地址的地理相关信息，包括国家、省、市和运营商。")
    @GetMapping("/getIpInfo")
    public DataResponse<String> getIpInfo(@RequestParam(required = true)@ApiParam("要查询的IP地址如 192.168.1.1")String ip){
        return searchService.getIpInfo(ip);
    }

    @ApiOperation("最新新闻头条，各类社会、国内、国际、体育、娱乐、科技等资讯，更新周期5-30分钟\n类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)")
    @GetMapping("/getNews")
    public DataResponse<String> getNews(@ApiParam("查询的新闻分类 默认 top")@RequestParam(defaultValue = "top") String type)
    {
        return searchService.getTouTiaoNews(type);
    }
    @ApiOperation("QQ音乐榜行榜，以及根据歌名或人名查询歌曲。可获得url链接导航到QQ网站  ")
    @GetMapping("/getQQMusic")
    public DataResponse<String> getQQMusic(@ApiParam("榜行榜id 3=欧美 5=内地 6=港台 16=韩国 17=日本 18=民谣 19=摇滚 23=销量 26=热歌")@RequestParam(required = false) String topId,@ApiParam("关键词检索当关键词不为空时 只按照关键词检索")@RequestParam(required = false)String keyword)
    {
        return searchService.getQQMusic(topId,keyword);
    }

    @ApiOperation("智能问答机器人，上知天文，下知地理。输入要提问的问题，获取回复的内容及相关信息")

    @GetMapping("/getTalk")
    public DataResponse<String> getTalk(@ApiParam("问题的内容") @RequestParam(required = true)String question)
    {
        return searchService.getTalk(question);
    }

    @ApiOperation("万种菜谱，包含主料、辅料，制作流程。可按分类、关键词检索")

    @GetMapping("/getRecipe")
    public DataResponse<String> getRecipe( @ApiParam("要查询菜的关键词如 红烧肉")@RequestParam(required = true)String keyword,@ApiParam("返回的数量默认10 最大10")@RequestParam(defaultValue = "10")int num,@ApiParam("从那一条开始默认0") @RequestParam(defaultValue = "0") int start)
    {
        if(num>10){
            num=10;
        }
        return searchService.getRecipe(keyword,num,start);
    }

    @ApiOperation("全国快递查询接口，提供包括申通、顺丰、圆通、韵达、中通、汇通、EMS、天天、国通、德邦、宅急送等几百家快递物流公司单号查询接口。2.与官网实时同步更新。3.自动识别快递公司。")
    @GetMapping("/getExpress")
    public DataResponse<String> getExpress(@ApiParam("快递单号") @RequestParam(required = true) String number,@ApiParam("快递公司 默认自动识别")@RequestParam(defaultValue = "auto") String type)
    {
        return searchService.getExpress(number,type);
    }
}
