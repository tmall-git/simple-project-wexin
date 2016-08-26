package com.simple.weixin.auth;


import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import com.simple.weixin.util.HttpUtil;

public class WeiXinAuth {

	private static final Log logger = LogFactory.getLog(WeiXinAuth.class);
	
	/**网页授权获，取用户信息*/
	public static final String oauth_user_info_url = "https://api.weixin.qq.com/sns/userinfo";
	
	private static String GLOBAL_ACCESS_TOKEN = null;//全局token
	private static Object lock = new Object();
	
	public static OAuthAccessToken getOAuthAccessToken(String code) {
		return WeiXinHelper.getOAuthAccessToken(code);
	}
	
	public static void installAcessToken() {
		GLOBAL_ACCESS_TOKEN = WeiXinHelper.getGlobalAccessToken();
	}
	
	public static String getGlobalAccessToken() {
		if (StringUtils.isEmpty(GLOBAL_ACCESS_TOKEN)) {
			synchronized (lock) {
				while (StringUtils.isEmpty(GLOBAL_ACCESS_TOKEN)) {
					installAcessToken(); 
				}
			}
		}
		return GLOBAL_ACCESS_TOKEN;
	}
	
	public static String getAuthUrl(String callBackUrl,boolean isAuth,String state) {
		try {
			if (isAuth) {
				return WeiXinHelper.getOAuthURL(callBackUrl, ScopeType.snsapi_userinfo, state);
			}else {
				return WeiXinHelper.getOAuthURL(callBackUrl, ScopeType.snsapi_base, state);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 网页授权，获取用户信息
	 * @param access_token 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openId
	 * @return
	 */
	public static OAuthUserInfo getOAuthUserInfo(String access_token, String openId) {
		OAuthUserInfo userinfo = null;
		StringBuilder getUrl = new StringBuilder();
		getUrl.append(oauth_user_info_url)
			.append("?access_token=").append(access_token)
			.append("&openid=").append(openId)
			.append("&lang=zh_CN");
		try {
			HttpGet httpget = new HttpGet(getUrl.toString());
			HttpResponse response = HttpUtil.execute(httpget);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			logger.info("request success, result = "+ result);
			userinfo = OAuthUserInfo.analytic(result);
		} catch (Exception e) {
			logger.error("请求腾讯获取oauth_access_token失败:" + e.toString(), e);
		}
		return userinfo;
	}
	
	
//	public static OAuthUserInfo authInfo(String code) {
//		OAuthAccessToken authAccessToken = getOAuthAccessToken(code);
//		if(authAccessToken != null) {
//			//获取用户信息
//			if(authAccessToken.getScope().equals(ScopeType.snsapi_userinfo)) {
//				OAuthUserInfo oAuthUserInfo = WeiXinHelper.getOAuthUserInfo(authAccessToken.getAccess_token(),
//						authAccessToken.getOpenid());
//				if(oAuthUserInfo != null) {
//					respMsg.setCode("1");
//					respMsg.setData(oAuthUserInfo);
//				} else {
//					respMsg.setCode("-3");
//					respMsg.setMsg("获取oAuthUserInfo失败");
//				}
//			} else {
//				UserInfoIsFollow userInfoIsFollow = wxApiService.getUserInfoIsFollow(authAccessToken.getOpenid());
//				if(userInfoIsFollow != null) {
//					respMsg.setCode("2");
//					respMsg.setData(userInfoIsFollow);
//				} else {
//					respMsg.setCode("-3");
//					respMsg.setMsg("获取userInfoIsFollow失败");
//				}
//			}
//		} else {
//			respMsg.setCode("-2");
//			respMsg.setMsg("code换取信息失败");
//		}
//	}
}
