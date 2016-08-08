package com.simple.weixin.pay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.util.MD5;

public class PayCommonUtil implements Serializable{

	private static final long serialVersionUID = 1L;

	public static String createSign(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v) 
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + EnvPropertiesConfiger.getValue("weixin_api_key"));
        String sign = MD5.stringMD5(sb.toString()).toUpperCase();
        return sign;
    }
	
	public static String getRequestXml(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }else {
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
	
	 public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
	        try {
	            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	            TrustManager[] tm = { new MyX509TrustManager() };
	            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	            sslContext.init(null, tm, new java.security.SecureRandom());
	            // 从上述SSLContext对象中得到SSLSocketFactory对象
	            SSLSocketFactory ssf = sslContext.getSocketFactory();
	            URL url = new URL(requestUrl);
	            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	            conn.setSSLSocketFactory(ssf);
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            // 设置请求方式（GET/POST）
	            conn.setRequestMethod(requestMethod);
	            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
	            // 当outputStr不为null时向输出流写数据
	            if (null != outputStr) {
	                OutputStream outputStream = conn.getOutputStream();
	                // 注意编码格式
	                outputStream.write(outputStr.getBytes("UTF-8"));
	                outputStream.close();
	            }
	            // 从输入流读取返回内容
	            InputStream inputStream = conn.getInputStream();
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String str = null;
	            StringBuffer buffer = new StringBuffer();
	            while ((str = bufferedReader.readLine()) != null) {
	                buffer.append(str);
	            }
	            // 释放资源
	            bufferedReader.close();
	            inputStreamReader.close();
	            inputStream.close();
	            inputStream = null;
	            conn.disconnect();
	            return buffer.toString();
	        } catch (ConnectException ce) {
	        	ce.printStackTrace();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        return null;
	    }
	 
	 /**
		* 获取ip地址
		* @param request
		* @return
		*/
	public static String getIpAddr(HttpServletRequest request) { 
		 InetAddress addr = null; 
		 try { 
			 addr = InetAddress.getLocalHost(); 
		 } catch (UnknownHostException e) { 
			 return request.getRemoteAddr(); 
		 } 
		 byte[] ipAddr = addr.getAddress(); 
		 String ipAddrStr = ""; 
		 for (int i = 0; i < ipAddr.length; i++) { 
			 if (i > 0 ) { 
			  ipAddrStr += "."; 
			 } 
			 ipAddrStr += ipAddr[i]; 
		 } 
	 	return ipAddrStr; 
	}
	
	public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";

	}
}
