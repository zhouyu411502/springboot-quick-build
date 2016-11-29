package org.springboot.quick.weixin.third.sdk.bean;

import java.io.InputStream;
import java.io.Serializable;

import org.springboot.quick.commons.xml.XStreamCDataConverter;
import org.springboot.quick.weixin.third.sdk.util.xml.XStreamTransformer;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 接收授权事件推送过来的解密后的Xml
 */
@XStreamAlias("xml")
public class AuthEventResp implements Serializable {
	private static final long serialVersionUID = 1L;

	@XStreamAlias("AppId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String appId;

    @XStreamAlias("CreateTime")
    private String createTime;

    @XStreamAlias("InfoType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String infoType;

    @XStreamAlias("ComponentVerifyTicket")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String componentVerifyTicket;
    
    @XStreamAlias("AuthorizerAppid")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String authorizerAppid;
    
    @XStreamAlias("AuthorizationCode")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String authorizationCode;
    
    @XStreamAlias("AuthorizationCodeExpiredTime")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String authorizationCodeExpiredTime;

    public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getComponentVerifyTicket() {
		return componentVerifyTicket;
	}

	public void setComponentVerifyTicket(String componentVerifyTicket) {
		this.componentVerifyTicket = componentVerifyTicket;
	}

	public String getAuthorizerAppid() {
		return authorizerAppid;
	}

	public void setAuthorizerAppid(String authorizerAppid) {
		this.authorizerAppid = authorizerAppid;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getAuthorizationCodeExpiredTime() {
		return authorizationCodeExpiredTime;
	}

	public void setAuthorizationCodeExpiredTime(String authorizationCodeExpiredTime) {
		this.authorizationCodeExpiredTime = authorizationCodeExpiredTime;
	}

	public static AuthEventResp fromXml(String xml) {
        return XStreamTransformer.fromXml(AuthEventResp.class, xml);
    }

    public static AuthEventResp fromXml(InputStream is) {
        AuthEventResp vo = XStreamTransformer.fromXml(AuthEventResp.class, is);
        return vo;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public String toXml() {
        return XStreamTransformer.toXml((Class) this.getClass(), this);
    }

	@Override
	public String toString() {
		return "ComponentVerifyTicketBean [appId=" + appId + ", createTime=" + createTime + ", infoType=" + infoType
				+ ", componentVerifyTicket=" + componentVerifyTicket + ", authorizerAppid=" + authorizerAppid
				+ ", authorizationCode=" + authorizationCode + ", authorizationCodeExpiredTime="
				+ authorizationCodeExpiredTime + "]";
	}
   
}
