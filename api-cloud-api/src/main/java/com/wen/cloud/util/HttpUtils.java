package com.wen.cloud.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;


import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sgao20 on 7/11/2017.
 */
@Component
public class HttpUtils {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RestTemplate restTemplate;
	/**
	 *
	 * @param url
	 
	 * @param header
	 * @param query
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public  String doPostWithMap(String url, Map<String,String> header,Map<String,String> query,Map<String,String> body) throws IOException {
		//return normalRequest(url,HttpMethod.POST,getEntity(header,body),query);
		return normalRequest(handUrl(url,query),HttpMethod.POST,getEntity(header,body),query);
	}

	/**
	 *
	 * @param url
	 * @param header
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public  String doGetWithMap(String url, Map<String,String> header,Map<String,String> query) throws IOException {
		return normalRequest(handUrl(url,query),HttpMethod.GET,getEntity(header,null),query);
	}

	/**
	 *
	 * @param url
	 
	 * @param header
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public  String doPutWithMap(String url, Map<String,String> header,Map<String,String> query) throws IOException {
		return normalRequest(handUrl(url,query),HttpMethod.PUT,getEntity(header,null),query);
	}

	/**
	 *
	 * @param url
	 
	 * @param header
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public  String doDeleteWithMap(String url, Map<String,String> header,Map<String,String> query) throws IOException {
		return normalRequest(handUrl(url,query),HttpMethod.DELETE,getEntity(header,null),query);
	}
	/**
	 *
	 
	 * @param header
	 * @param body
	 * @return
	 */
	private  org.springframework.http.HttpEntity<String> getEntity( Map<String,String> header,Map<String,String> body){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		if(header!=null&&header.size()>0){
			for (Map.Entry<String, String> e : header.entrySet()) {
				headers.add(e.getKey(), e.getValue());
			}
		}
		if(body!=null&&body.size()>0){
			return new org.springframework.http.HttpEntity<>(JSON.toJSONString(body), headers);
		}else{
			return new org.springframework.http.HttpEntity<>(null, headers);
		}
	}
	/**
	 *
	 * @param url
	 * @param query
	 * @return
	 */
	private   String handUrl(String url,Map<String,String> query){
		if(query!=null&&query.size()>0){
			StringBuffer sb=new StringBuffer();
			sb.append("?");
			for (Map.Entry<String, String> e : query.entrySet()) {
				sb.append(e.getKey()+"={"+e.getKey()+"}&");
			}
			url=url+sb.toString().substring(0,sb.toString().length()-1);
		}
		return url;
	}
	/**
	 * @param url
	 * @param httpMethod
	 * @param request
	 * @return
	 * @throws Exception
	 */

	public  String normalRequest(String url, HttpMethod httpMethod,org.springframework.http.HttpEntity<String> request,Map<String,String> query) throws IOException {
		String result = "";
		if(query==null){
			query=new HashMap<String,String>();
		}
		ResponseEntity<String> res=	restTemplate.	exchange( url,  httpMethod,request, String.class, query);
		if (res.getStatusCode().value() == org.springframework.http.HttpStatus.OK.value()) {
			result = res.getBody();
		} else {
			logger.error("error, status code:{}, content:{}", res.getStatusCode(), res.getBody());
			throw new IOException("Http error code:" + res.getStatusCode() + res.getBody());
		}
		return result;
	}





	public  String getContent(final HttpEntity entity) throws IOException {
		final ContentType contentType = ContentType.get(entity);
		final InputStream instream = entity.getContent();
		if (instream == null) {
			return null;
		}
		Args.check(entity.getContentLength() <= Integer.MAX_VALUE, "HTTP entity too large to be buffered in memory");
		int i = (int) entity.getContentLength();
		if (i < 0) {
			i = 4096;
		}
		Charset charset = null;
		if (contentType != null) {
			charset = contentType.getCharset();
			if (charset == null) {
				final ContentType defaultContentType = ContentType.getByMimeType(contentType.getMimeType());
				charset = defaultContentType != null ? defaultContentType.getCharset() : null;
			}
		}
		if (charset == null) {
			charset = HTTP.DEF_CONTENT_CHARSET;
		}
		final Reader reader = new InputStreamReader(instream, charset);
		final CharArrayBuffer buffer = new CharArrayBuffer(i);
		final char[] tmp = new char[1024];
		int l;
		while ((l = reader.read(tmp)) != -1) {
			buffer.append(tmp, 0, l);
		}
		return buffer.toString();
	}

	private  String encodeUrlQueryString(String url) {
		int dataIndex = url.indexOf("?");
		if(dataIndex <= 0)
		{
			return url;
		}
		String encodedUrlQueryString = url.substring(dataIndex + 1);
		try {
			if (encodedUrlQueryString.contains("&")) {
				String[] keyValues = encodedUrlQueryString.split("&");
				StringBuffer params = new StringBuffer();
				for(String keyValue : keyValues) {
					String paramName = keyValue.substring(0, keyValue.indexOf("="));
					String paramValue = keyValue.substring(keyValue.indexOf("=") + 1);

					params.append("&").append(paramName).append("=").append(URLEncoder.encode(paramValue, "utf-8"));
					//params.append("&").append(paramName).append("=").append(paramValue);
				}
				encodedUrlQueryString = params.substring(1);
			} else {
				int index = encodedUrlQueryString.indexOf("=");
				encodedUrlQueryString = encodedUrlQueryString.substring(0,index + 1) + URLEncoder.encode(encodedUrlQueryString.substring(index + 1), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hostName = url.substring(0, dataIndex + 1);
		return hostName + encodedUrlQueryString;
	}
}
