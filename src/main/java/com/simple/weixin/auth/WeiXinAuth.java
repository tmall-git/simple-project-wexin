package com.simple.weixin.auth;


public class WeiXinAuth {

	
	public static OAuthAccessToken getOAuthAccessToken(String code) {
		return WeiXinHelper.getOAuthAccessToken(code);
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
