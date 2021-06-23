package com.github.wxiaoqi.security.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class HuaweiPush {

		private static final Logger LOGGER = LoggerFactory.getLogger(HuaweiPush.class);
	    private static String appSecret = "d9d0368bf4535aef04cb92a0d99a6e30";
	    private static  String appId = "100701397";//用户在华为开发者联盟申请的appId和appSecret（会员中心->应用管理，点击应用名称的链接）
	    private static  String tokenUrl = "https://login.vmall.com/oauth2/token"; //获取认证Token的URL
	    private static  String apiUrl = "https://api.push.hicloud.com/pushsend.do"; //应用级消息下发API
	    private static  String accessToken;//下发通知消息的认证Token
	    private static  long tokenExpiredTime;  //accessToken的过期时间
	    
	    public static void main(String[] args) throws IOException
	    {
//	        refreshToken();
//	        sendPushMessage();
	    	
	    	HuaweiPushMessage message=new HuaweiPushMessage("警情通知：",
					"[阎村东]车站[安检口A]发现危险品！查看详情>>");
	    	String msg=sendPushMessage("0864779030717247300003454700CN01", message);
	    	System.out.println(msg);
	    	
	    }
	    
	    //获取下发通知消息的认证Token
	    private static  void refreshToken() throws IOException
	    {
	        String msgBody = MessageFormat.format(
	        		"grant_type=client_credentials&client_secret={0}&client_id={1}", 
	        		URLEncoder.encode(appSecret, "UTF-8"), appId);
	        String response = httpPost(tokenUrl, msgBody, 5000, 5000);
	        JSONObject obj = JSONObject.parseObject(response);
	        accessToken = obj.getString("access_token");
	        tokenExpiredTime = System.currentTimeMillis() + obj.getLong("expires_in") - 5*60*1000;
	    }
	    
	    
	    private static String refreshToken(String appId, String appSecret)
	    {
	    	try{
		        String msgBody = MessageFormat.format("grant_type=client_credentials&client_secret={0}&client_id={1}", URLEncoder.encode(appSecret, "UTF-8"), appId);
		        String response = httpPost(tokenUrl, msgBody, 5000, 5000);
		        JSONObject obj = JSONObject.parseObject(response);
		        return obj.getString("access_token");
	        }catch(Exception e){
	        	LOGGER.error("获取下发通知消息的认证Token异常:{}",e);
	        }
	    	return null;
	    }
	    //发送Push消息
	    private static  void sendPushMessage() throws IOException
	    {
	        if (tokenExpiredTime <= System.currentTimeMillis())
	        {
	            refreshToken();
	        }      
	        /*PushManager.requestToken为客户端申请token的方法，可以调用多次以防止申请token失败*/
	        /*PushToken不支持手动编写，需使用客户端的onToken方法获取*/
	        JSONArray deviceTokens = new JSONArray();//目标设备Token
	        deviceTokens.add("0864779030717247300003454700CN01");
	          
	        JSONObject body = new JSONObject();//仅通知栏消息需要设置标题和内容，透传消息key和value为用户自定义
	        body.put("title", "Push message title");//消息标题
	        body.put("content", "Push message content");//消息内容体
	        
	        JSONObject param = new JSONObject();
	        param.put("appPkgName", "com.huawei.hms.hmsdemo");//定义需要打开的appPkgName
	        
	        JSONObject action = new JSONObject();
	        action.put("type", 3);//类型3为打开APP，其他行为请参考接口文档设置
	        action.put("param", param);//消息点击动作参数
	        
	        JSONObject msg = new JSONObject();
	        msg.put("type", 3);//3: 通知栏消息，异步透传消息请根据接口文档设置
	        msg.put("action", action);//消息点击动作
	        msg.put("body", body);//通知栏消息body内容
	        
	        JSONObject ext = new JSONObject();//扩展信息，含BI消息统计，特定展示风格，消息折叠。
	        ext.put("biTag", "Trump");//设置消息标签，如果带了这个标签，会在回执中推送给CP用于检测某种类型消息的到达率和状态
	        ext.put("icon", "http://pic.qiantucdn.com/58pic/12/38/18/13758PIC4GV.jpg");//自定义推送消息在通知栏的图标,value为一个公网可以访问的URL
	        
	        JSONObject hps = new JSONObject();//华为PUSH消息总结构体
	        hps.put("msg", msg);
	        hps.put("ext", ext);
	        
	        JSONObject payload = new JSONObject();
	        payload.put("hps", hps);
	        
	        String postBody = MessageFormat.format(
	        	"access_token={0}&nsp_svc={1}&nsp_ts={2}&device_token_list={3}&payload={4}",
	            URLEncoder.encode(accessToken,"UTF-8"),
	            URLEncoder.encode("openpush.message.api.send","UTF-8"),
	            URLEncoder.encode(String.valueOf(System.currentTimeMillis() / 1000),"UTF-8"),
	            URLEncoder.encode(deviceTokens.toString(),"UTF-8"),
	            URLEncoder.encode(payload.toString(),"UTF-8"));
	        
	        String postUrl = apiUrl + "?nsp_ctx=" + URLEncoder.encode("{\"ver\":\"1\", \"appId\":\"" + appId + "\"}", "UTF-8");
	        httpPost(postUrl, postBody, 5000, 5000);
	    }
	    
	    public static String httpPost(String httpUrl, String data, int connectTimeout, int readTimeout) throws IOException
	    {
	        OutputStream outPut = null;
	        HttpURLConnection urlConnection = null;
	        InputStream in = null;
	        
	        try
	        {
	            URL url = new URL(httpUrl);
	            urlConnection = (HttpURLConnection)url.openConnection();          
	            urlConnection.setRequestMethod("POST");
	            urlConnection.setDoOutput(true);
	            urlConnection.setDoInput(true);
	            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	            urlConnection.setConnectTimeout(connectTimeout);
	            urlConnection.setReadTimeout(readTimeout);
	            urlConnection.connect();
	            
	            // POST data
	            outPut = urlConnection.getOutputStream();
	            outPut.write(data.getBytes("UTF-8"));
	            outPut.flush();
	            
	            // read response
	            if (urlConnection.getResponseCode() < 400)
	            {
	                in = urlConnection.getInputStream();
	            }
	            else
	            {
	                in = urlConnection.getErrorStream();
	            }
	            
	            List<String> lines = IOUtils.readLines(in, urlConnection.getContentEncoding());
	            StringBuffer strBuf = new StringBuffer();
	            for (String line : lines)
	            {
	                strBuf.append(line);
	            }
	            System.out.println(strBuf.toString());
	            return strBuf.toString();
	        }
	        finally
	        {
	            IOUtils.closeQuietly(outPut);
	            IOUtils.closeQuietly(in);
	            if (urlConnection != null)
	            {
	                urlConnection.disconnect();
	            }
	        }
	    }
	    
	    
	    public static String sendPushMessage(String devId, HuaweiPushMessage message) {
	    	
//	    	Map<String,Object> custom = new HashMap<String,Object>();
//	    	custom.put("intentValue", "你好");custom.put("intentact", "flag.eg.bd.act");
	    	
	    	if(null == message.getTitle()|| null == devId|| null ==message.getDescription() ) {
	    		LOGGER.error("参数错误",message);
			}
	    	
	    	String accessToken = refreshToken(appId,appSecret);//下发通知消息的认证Token
	    	String response = "华为推送异常，推送失败";
	    	try{
	        /*PushManager.requestToken为客户端申请token的方法，可以调用多次以防止申请token失败*/
	        /*PushToken不支持手动编写，需使用客户端的onToken方法获取*/
	        JSONArray deviceTokens = new JSONArray();//目标设备Token
	        deviceTokens.add(devId);
	          
	        JSONObject body = new JSONObject();//仅通知栏消息需要设置标题和内容，透传消息key和value为用户自定义
	        body.put("title",message.getTitle());//消息标题
	        body.put("content", message.getDescription());//消息内容体
	        
	        JSONObject param = new JSONObject();
	        param.put("appPkgName", "com.huawei.hms.hmsdemo");//定义需要打开的appPkgName
	        
	        JSONObject action = new JSONObject();
	        action.put("type", 3);//类型3为打开APP,2 打开URL,1 自定义行为   
	        action.put("param", param);//关于消息点击动作的参数
	        
	        JSONObject msg = new JSONObject();
	        msg.put("type", 3);//3: 通知栏消息，1 透传异步消息,2和4以后为保留后续扩展使用
	        msg.put("action", action);//消息点击动作
	        msg.put("body", body);//通知栏消息body内容
	        
	        
//	        List<Object> cusList = new ArrayList<Object>();
//	  		try{
//				if(null != custom) {
//					cusList.add(custom);
//				}
//			}catch(ClassCastException e){
//				LOGGER.error("华为custom类型格式不正确转换一次{}",e.getMessage());
//			}
	        
	        JSONObject ext = new JSONObject();//扩展信息，含BI消息统计，特定展示风格，消息折叠。
	        ext.put("biTag", "Trump");//设置消息标签，如果带了这个标签，会在回执中推送给CP用于检测某种类型消息的到达率和状态
	        ext.put("icon", "http://pic.qiantucdn.com/58pic/12/38/18/13758PIC4GV.jpg");//自定义推送消息在通知栏的图标,value为一个公网可以访问的URL
	        
	        JSONObject hps = new JSONObject();//华为PUSH消息总结构体
	        hps.put("msg", msg);
	        hps.put("ext", ext);
	        
	        JSONObject payload = new JSONObject();
	        payload.put("hps", hps);
	        
	        String postBody = MessageFormat.format(
	        	"access_token={0}&nsp_svc={1}&nsp_ts={2}&device_token_list={3}&payload={4}",
	            URLEncoder.encode(accessToken,"UTF-8"),
	            URLEncoder.encode("openpush.message.api.send","UTF-8"),
	            URLEncoder.encode(String.valueOf(System.currentTimeMillis() / 1000),"UTF-8"),
	            URLEncoder.encode(deviceTokens.toString(),"UTF-8"),
	            URLEncoder.encode(payload.toString(),"UTF-8"));
	        
	        String postUrl = apiUrl + "?nsp_ctx=" + URLEncoder.encode("{\"ver\":\"1\", \"appId\":\"" + appId + "\"}", "UTF-8");
	        response = httpPost(postUrl, postBody, 5000, 5000);
	    	}catch(Exception e){
	    		LOGGER.error("华为推送pushMessage:Exception异常，推送失败:{}",e);
	    	}
	        return response;
	    }
	    
	    
	    public static String sendPushMessage1(String devId, HuaweiPushMessage message) {
	    	
//	    	Map<String,Object> custom = new HashMap<String,Object>();
//	    	custom.put("intentValue", "你好");custom.put("intentact", "flag.eg.bd.act");
	    	
	    	if(null == message.getTitle()|| null == devId|| null ==message.getDescription() ) {
	    		LOGGER.error("参数错误",message);
			}
	    	
	    	String accessToken = refreshToken(appId,appSecret);//下发通知消息的认证Token
	    	String response = "华为推送异常，推送失败";
	    	try{
	        /*PushManager.requestToken为客户端申请token的方法，可以调用多次以防止申请token失败*/
	        /*PushToken不支持手动编写，需使用客户端的onToken方法获取*/
	        JSONArray deviceTokens = new JSONArray();//目标设备Token
	        deviceTokens.add(devId);
	          
	        JSONObject body = new JSONObject();//仅通知栏消息需要设置标题和内容，透传消息key和value为用户自定义
	        body.put("msg", message.getDescription());//消息内容体
	        
	        JSONObject msg = new JSONObject();
	        msg.put("type", 1);//3: 通知栏消息，1 透传异步消息,2和4以后为保留后续扩展使用
	        msg.put("body", body);//通知栏消息body内容
	        
	        
//	        List<Object> cusList = new ArrayList<Object>();
//	  		try{
//				if(null != custom) {
//					cusList.add(custom);
//				}
//			}catch(ClassCastException e){
//				LOGGER.error("华为custom类型格式不正确转换一次{}",e.getMessage());
//			}
	        
	        JSONObject ext = new JSONObject();//扩展信息，含BI消息统计，特定展示风格，消息折叠。
	        
	        JSONObject hps = new JSONObject();//华为PUSH消息总结构体
	        hps.put("msg", msg);
	        hps.put("ext", ext);
	        
	        JSONObject payload = new JSONObject();
	        payload.put("hps", hps);
	        
	        String postBody = MessageFormat.format(
	        	"access_token={0}&nsp_svc={1}&nsp_ts={2}&device_token_list={3}&payload={4}",
	            URLEncoder.encode(accessToken,"UTF-8"),
	            URLEncoder.encode("openpush.message.api.send","UTF-8"),
	            URLEncoder.encode(String.valueOf(System.currentTimeMillis() / 1000),"UTF-8"),
	            URLEncoder.encode(deviceTokens.toString(),"UTF-8"),
	            URLEncoder.encode(payload.toString(),"UTF-8"));
	        
	        String postUrl = apiUrl + "?nsp_ctx=" + URLEncoder.encode("{\"ver\":\"1\", \"appId\":\"" + appId + "\"}", "UTF-8");
	        response = httpPost(postUrl, postBody, 5000, 5000);
	    	}catch(Exception e){
	    		LOGGER.error("华为推送pushMessage:Exception异常，推送失败:{}",e);
	    	}
	        return response;
	    }
}
