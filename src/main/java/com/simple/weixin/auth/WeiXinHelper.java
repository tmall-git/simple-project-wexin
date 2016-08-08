package com.simple.weixin.auth;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.weixin.util.HttpUtil;

public class WeiXinHelper {

	/**网页授权获，code换取网页授权access_token*/
	public static final String oauth_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
	/**网页授权获，取用户信息*/
	public static final String oauth_user_info_url = "https://api.weixin.qq.com/sns/userinfo";
	/**获取用户信息地址，该接口需要用户关注服务号，否则拉取不到信息*/
	public static final String user_info_is_follow_url = "http://api.weixin.qq.com/cgi-bin/user/info";
	
	
	/**
	 * 
	 * @Description: 网页授权，通过code换取网页授权access_token
	 * @param code
	 * @return
	 */
	public static OAuthAccessToken getOAuthAccessToken(String code) {
		OAuthAccessToken oaat = null;
		StringBuilder getUrl = new StringBuilder();
		getUrl.append(oauth_access_token_url)
			.append("?appid=").append(EnvPropertiesConfiger.getValue("weixin_appid"))
			.append("&secret=").append(EnvPropertiesConfiger.getValue("weixin_secret"))
			.append("&code=").append(code)
			.append("&grant_type=authorization_code");
		try {
			HttpGet httpget = new HttpGet(getUrl.toString());
			HttpResponse response = HttpUtil.execute(httpget);
			String result = EntityUtils.toString(response.getEntity());
			//logger.info("request success, result = {}.", result);
			oaat = OAuthAccessToken.analyticToken(result);
		} catch (Exception e) {
			//logger.error("请求腾讯获取oauth_access_token失败:" + e.toString(), e);
		}
		return oaat;
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
			String result = EntityUtils.toString(response.getEntity());
			//logger.info("request success, result = {}.", result);
			userinfo = OAuthUserInfo.analytic(result);
		} catch (Exception e) {
			//logger.error("请求腾讯获取oauth_access_token失败:" + e.toString(), e);
		}
		return userinfo;
	}
}
