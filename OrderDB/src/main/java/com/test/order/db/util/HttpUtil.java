package com.test.order.db.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

public class HttpUtil {

	    private final static int HTTP_CONNECTION_TIMEOUT = 15 * 1000;
	    private final static int HTTP_SOCKET_TIMEOUT = 30 * 1000;
	    
	    // SSL Configure
	    public static final String KEY_STROE_PATH = "";
	    public static final String KEY_STROE_TYPE = "";
	    public static final String TRUSTED_STORE_PATH = "";
	    public static final String TRUSTED_STORE_TYPE = "";
	    public static final String KEY_STORE_PASSWORD = "";
	    public static final String ALGORITHM = KeyManagerFactory.getDefaultAlgorithm();
	    public static final String SSL_PROTOCOL = "SSL";
	    public static final int HTTPS_PORT = 443;
	    
	public static String doPost(String url, Map<String,String> paramsMap, String encoding){
		if(CommonUtil.isEmpty(url)){
			return "";
		}else if(CommonUtil.isEmpty(encoding)){
			encoding = "UTF-8";
		}
		String respString = "";
		HttpClient httpClient = new DefaultHttpClient();
		try{
			setHttpClientParametersWithDefaultValue(httpClient);
			HttpPost httpPost = new HttpPost(url);
			List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
	        
	        for (Map.Entry<String,String> me : paramsMap.entrySet()) {
	            paramsList.add(new BasicNameValuePair(me.getKey(), me.getValue()));
	        }
	        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsList, encoding);
	        httpPost.setEntity(entity);
	        HttpResponse response;
	        response = httpClient.execute(httpPost);
	        if (response.getEntity() != null) {
	            InputStream is = response.getEntity().getContent();
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            try {
	                byte[] buffer = new byte[1024];
	                int len;
	
	                while ((len = is.read(buffer)) > 0) {
	                    bos.write(buffer, 0, len);
	                }
	
	                respString = bos.toString(encoding);
	            } catch (IOException e) {
	                throw e;
	            } finally {
	                is.close();
	                bos.close();
	            }
	        }
        }catch(Exception e){
        	e.printStackTrace();
        }
		return respString;
	}
	public String doGet(){
		
		return "";
	}
	
	  /**
     * 设置HttpClient得链接属性
     * 
     * @param httpClient 
     */
    public static void setHttpClientParametersWithDefaultValue(HttpClient httpClient) {
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HTTP_CONNECTION_TIMEOUT);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, HTTP_SOCKET_TIMEOUT);
    }
}
