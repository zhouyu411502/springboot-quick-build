
package org.springboot.quick.weixin.mp.sdk.core.api.impl;

import java.io.File;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springboot.quick.commons.redis.service.RedisService;
import org.springboot.quick.weixin.mp.sdk.core.api.WxConsts;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.ApacheHttpClientBuilder;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;

/**
 * ProjectName: weixin-quick-mp-sdk <br/>
 * ClassName: WxMpInRedisConfigStorage <br/>
 * Date: 2016年11月5日 下午4:07:50 <br/>
 * 
 * @author chababa
 * @version
 * @since JDK 1.7
 * @see
 */
public class WxMpInRedisConfigStorage implements WxMpConfigStorage {

	protected volatile String appId;
	protected volatile String secret;
	protected volatile String partnerId;
	protected volatile String partnerKey;
	protected volatile String token;
	protected volatile String accessToken;
	protected volatile String aesKey;
	protected volatile long expiresTime;

	protected volatile String oauth2redirectUri;

	protected volatile String httpProxyHost;
	protected volatile int httpProxyPort;
	protected volatile String httpProxyUsername;
	protected volatile String httpProxyPassword;

	protected volatile String jsapiTicket;
	protected volatile long jsapiTicketExpiresTime;

	protected volatile String cardApiTicket;
	protected volatile long cardApiTicketExpiresTime;

	/**
	 * 临时文件目录
	 */
	protected volatile File tmpDirFile;

	protected volatile SSLContext sslContext;

	protected volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

	protected volatile RedisService redisService;

	@Override
	public String getAccessToken() {
		this.accessToken = hget("authorizer_access_token");
		return this.accessToken;
	}

	@Override
	public boolean isAccessTokenExpired() {
		this.expiresTime = Long.parseLong(hget("authorizer_access_token_expires"));
		return System.currentTimeMillis() > this.expiresTime;
	}

	@Override
	public synchronized void updateAccessToken(WxAccessToken accessToken) {
		updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
	}

	@Override
	public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
		this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
		this.accessToken = accessToken;

		setAccessToken(accessToken);
		setExpiresTime(expiresInSeconds);	}

	@Override
	public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
		this.jsapiTicket = jsapiTicket;
		// 预留200秒的时间
		this.jsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
		
		setJsapiTicket(this.jsapiTicket);
		setJsapiTicketExpiresTime(this.jsapiTicketExpiresTime);
	}
	
	@Override
	public synchronized void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
		this.cardApiTicket = cardApiTicket;
		// 预留200秒的时间
		this.cardApiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
		setCardApiTicket(cardApiTicket);
		setCardApiTicket(cardApiTicket);
		hset("card_api_ticket_expires", cardApiTicketExpiresTime + "");
	}
	
	@Override
	public void expireAccessToken() {
		hset("authorizer_access_token_expires", "0");
		this.expiresTime = 0;
	}

	@Override
	public String getJsapiTicket() {
		this.jsapiTicket = hget("jsapi_ticket");
		return this.jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		hset("jsapi_ticket", jsapiTicket);
		this.jsapiTicket = jsapiTicket;
	}

	public long getJsapiTicketExpiresTime() {
		this.jsapiTicketExpiresTime = Long.parseLong(hget("jsapi_ticket_expires"));
		return this.jsapiTicketExpiresTime;
	}

	public void setJsapiTicketExpiresTime(long jsapiTicketExpiresTime) {
		hset("jsapi_ticket_expires", jsapiTicketExpiresTime + "");
		this.jsapiTicketExpiresTime = jsapiTicketExpiresTime;
	}

	@Override
	public boolean isJsapiTicketExpired() {
		String val = hget("jsapi_ticket_expires");
		if(val == null || "".equals(val) || "null".equals(val)){
			val = "0";
		}
		this.jsapiTicketExpiresTime = Long.parseLong(val);
		return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
	}

	@Override
	public void expireJsapiTicket() {
		hset("jsapi_ticket_expires", "0");
		this.jsapiTicketExpiresTime = 0;
	}

	/**
	 * 卡券api_ticket
	 */
	@Override
	public String getCardApiTicket() {
		return this.cardApiTicket;
	}

	public void setCardApiTicket(String cardApiTicket) {
		this.cardApiTicket = cardApiTicket;
		hset("card_api_ticket", this.cardApiTicket);
	}
	
	public void setCardApiTicketExpiresTime(long cardApiTicketExpiresTime) {
		hset("card_api_ticket_expires", this.cardApiTicketExpiresTime+"");
		this.cardApiTicketExpiresTime = cardApiTicketExpiresTime;
	}


	@Override
	public boolean isCardApiTicketExpired() {
		this.cardApiTicketExpiresTime = Long.parseLong(hget("card_api_ticket_expires"));
		return System.currentTimeMillis() > this.cardApiTicketExpiresTime;
	}


	@Override
	public void expireCardApiTicket() {
		hset("card_api_ticket_expires", "0");
		this.cardApiTicketExpiresTime = 0;
	}

	private void hset(String key, String value) {
		redisService.hset(getAuthorizationInfoMapKey(), key, value);
	}

	private String hget(String key) {
		return redisService.hget(getAuthorizationInfoMapKey(), key);
	}

	@Override
	public String getAppId() {
		return this.appId;
	}

	@Override
	public String getSecret() {
		return this.secret;
	}

	@Override
	public String getToken() {
		return this.token;
	}

	@Override
	public long getExpiresTime() {
		return this.expiresTime;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String getAesKey() {
		return this.aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setExpiresTime(long expiresTime) {
		hset("authorizer_access_token_expires", expiresTime + "");
		this.expiresTime = expiresTime;
	}

	@Override
	public String getOauth2redirectUri() {
		return this.oauth2redirectUri;
	}

	public void setOauth2redirectUri(String oauth2redirectUri) {
		this.oauth2redirectUri = oauth2redirectUri;
	}

	@Override
	public String getHttpProxyHost() {
		return this.httpProxyHost;
	}

	public void setHttpProxyHost(String httpProxyHost) {
		this.httpProxyHost = httpProxyHost;
	}

	@Override
	public int getHttpProxyPort() {
		return this.httpProxyPort;
	}

	public void setHttpProxyPort(int httpProxyPort) {
		this.httpProxyPort = httpProxyPort;
	}

	@Override
	public String getHttpProxyUsername() {
		return this.httpProxyUsername;
	}

	public void setHttpProxyUsername(String httpProxyUsername) {
		this.httpProxyUsername = httpProxyUsername;
	}

	@Override
	public String getHttpProxyPassword() {
		return this.httpProxyPassword;
	}

	public void setHttpProxyPassword(String httpProxyPassword) {
		this.httpProxyPassword = httpProxyPassword;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public String getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	@Override
	public String getPartnerKey() {
		return this.partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	@Override
	public File getTmpDirFile() {
		return this.tmpDirFile;
	}

	public void setTmpDirFile(File tmpDirFile) {
		this.tmpDirFile = tmpDirFile;
	}

	@Override
	public SSLContext getSSLContext() {
		return this.sslContext;
	}

	public void setSSLContext(SSLContext context) {
		this.sslContext = context;
	}

	@Override
	public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
		return this.apacheHttpClientBuilder;
	}

	public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
		this.apacheHttpClientBuilder = apacheHttpClientBuilder;
	}

	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	public String getAuthorizationInfoMapKey() {
		return String.format(WxConsts.REDIS_COMPONENT_AUTHORIZER_INFO, this.appId);
	}
}
