
package org.springboot.quick.weixin.third.sdk.bean.request;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * ProjectName: weixin-quick-component-sdk <br/>
 * ClassName: ApiQueryAuth <br/>
 * Date: 2016年10月24日 下午9:04:12 <br/>
 * 
 * @author chababa
 * @version
 * @since JDK 1.7
 * @see
 */
public class ApiQueryAuthResult {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private String authorizerAppId;
	private List<String> funcInfo;
	/**
	 * 授权方接口调用凭据（在授权的公众号具备API权限时，才有此返回值），也简称为令牌
	 */
	private String authorizerAccessToken;

	/**
	 * 接口调用凭据刷新令牌（在授权的公众号具备API权限时，才有此返回值），刷新令牌主要用于公众号第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。
	 * 一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
	 */
	private String authorizerRefreshToken;
	/**
	 * 有效期2小时（7200秒）（在授权的公众号具备API权限时，才有此返回值）
	 */
	private Long expiresIn;
	/**
	 * access_token 未来过期时间，一半都会设置提前5分钟过期，保证可用性，
	 */
	private Long accessTokenExpiresTime;

	public ApiQueryAuthResult() {
		super();
	}

	public ApiQueryAuthResult(JSONObject jsonObject) {
		// 授权信息
		String authorizationInfo = jsonObject.getString("authorization_info");
		if (logger.isDebugEnabled()) {
			logger.debug("authorization_info : {}", authorizationInfo);
		}

		//
		JSONObject authorizationInfoJSON = JSON.parseObject(authorizationInfo);
		// 授权方appid
		this.authorizerAppId = authorizationInfoJSON.getString("authorizer_appid");
		// 授权方接口调用凭据（在授权的公众号具备API权限时，才有此返回值），也简称为令牌
		this.authorizerAccessToken = authorizationInfoJSON.getString("authorizer_access_token");
		// 接口调用凭据刷新令牌（在授权的公众号具备API权限时，才有此返回值），
		// 刷新令牌主要用于公众号第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。
		// 一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
		this.authorizerRefreshToken = authorizationInfoJSON.getString("authorizer_refresh_token");
		// 有效期（在授权的公众号具备API权限时，才有此返回值）
		this.expiresIn = authorizationInfoJSON.getLong("expires_in");
		/**
		 * 公众号授权给开发者的权限集列表，ID为1到15时分别代表： 消息管理权限 用户管理权限 帐号服务权限 网页服务权限 微信小店权限
		 * 微信多客服权限 群发与通知权限 微信卡券权限 微信扫一扫权限 微信连WIFI权限 素材管理权限 微信摇周边权限 微信门店权限 微信支付权限
		 * 自定义菜单权限
		 * 
		 * 请注意：
		 * 1）该字段的返回不会考虑公众号是否具备该权限集的权限（因为可能部分具备），请根据公众号的帐号类型和认证情况，来判断公众号的接口权限。
		 */

		/**
		 * "func_info": [ { "funcscope_category": { "id": 1 } },
		 * {"funcscope_category": { "id": 2 } }, { "funcscope_category": { "id":
		 * 3 } } ]
		 */
		List<String> funcs = new ArrayList<String>();
		JSONArray funcInfoArray = authorizationInfoJSON.getJSONArray("func_info");
		funcInfoArray.stream().forEach(fc -> {
			// {"funcscope_category": { "id": 2 } }
			JSONObject funcate = JSON.parseObject(fc.toString());
			String id = funcate.getJSONObject("funcscope_category").getString("id");
			funcs.add(id);
		});

		this.funcInfo = funcs;
		// 提前5分钟刷新
		this.accessTokenExpiresTime = System.currentTimeMillis() + (expiresIn - 300) * 1000l;
	}

	public String getAuthorizerAppId() {
		return authorizerAppId;
	}

	public void setAuthorizerAppId(String authorizerAppId) {
		this.authorizerAppId = authorizerAppId;
	}

	public List<String> getFuncInfo() {
		return funcInfo;
	}

	public void setFuncInfo(List<String> funcInfo) {
		this.funcInfo = funcInfo;
	}

	public String getAuthorizerAccessToken() {
		return authorizerAccessToken;
	}

	public void setAuthorizerAccessToken(String authorizerAccessToken) {
		this.authorizerAccessToken = authorizerAccessToken;
	}

	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}

	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Long getAccessTokenExpiresTime() {
		return accessTokenExpiresTime;
	}

	public void setAccessTokenExpiresTime(Long accessTokenExpiresTime) {
		this.accessTokenExpiresTime = accessTokenExpiresTime;
	}

	@Override
	public String toString() {
		return "ApiQueryAuth [authorizerAppId=" + authorizerAppId + ", funcInfo=" + funcInfo + ", authorizerAccessToken=" + authorizerAccessToken + ", authorizerRefreshToken="
				+ authorizerRefreshToken + ", expiresIn=" + expiresIn + ", accessTokenExpiresTime=" + accessTokenExpiresTime + "]";
	}

}
