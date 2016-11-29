package org.springboot.quick.weixin.third.sdk.bean.request;

import java.io.InputStream;
import java.io.Serializable;

import org.springboot.quick.commons.xml.XStreamCDataConverter;
import org.springboot.quick.weixin.third.sdk.util.xml.XStreamTransformer;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;


/**
 * 接收授权事件推送过来的解密后的Xml
 * 
 */
@XStreamAlias("xml")
public class AuthEventNotifyXmlBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@XStreamAlias("AppId")
	@XStreamConverter(value = XStreamCDataConverter.class)
	private String appId;
	@XStreamAlias("Encrypt")
	@XStreamConverter(value = XStreamCDataConverter.class)
	private String encrypt;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	public static AuthEventNotifyXmlBean fromXml(String xml) {
		return XStreamTransformer.fromXml(AuthEventNotifyXmlBean.class, xml);
	}

	public static AuthEventNotifyXmlBean fromXml(InputStream is) {
		AuthEventNotifyXmlBean vo = XStreamTransformer.fromXml(AuthEventNotifyXmlBean.class, is);
		return vo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toXml() {
		return XStreamTransformer.toXml((Class) this.getClass(), this);
	}

	@Override
	public String toString() {
		return "AuthEventNotifyBean{" + "appId='" + appId + '\'' + ", encrypt='" + encrypt + '\'' + '}';
	}
}
