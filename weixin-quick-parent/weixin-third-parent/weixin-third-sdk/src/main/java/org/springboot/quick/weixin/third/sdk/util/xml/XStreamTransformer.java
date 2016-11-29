package org.springboot.quick.weixin.third.sdk.util.xml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springboot.quick.commons.xml.XStreamInitializer;
import org.springboot.quick.weixin.third.sdk.bean.AuthEventResp;
import org.springboot.quick.weixin.third.sdk.bean.request.AuthEventNotifyXmlBean;

import com.thoughtworks.xstream.XStream;

public class XStreamTransformer {

	protected static final Map<Class<?>, XStream> CLASS_2_XSTREAM_INSTANCE = configXStreamInstance();

	/**
	 * xml -> pojo
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(Class<T> clazz, String xml) {
		T object = (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(xml);
		return object;
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromXml(Class<T> clazz, InputStream is) {
		T object = (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(is);
		return object;
	}

	/**
	 * 注册扩展消息的解析器
	 * 
	 * @param clz
	 *            类型
	 * @param xStream
	 *            xml解析器
	 */
	public static void register(Class<?> clz, XStream xStream) {
		CLASS_2_XSTREAM_INSTANCE.put(clz, xStream);
	}

	/**
	 * pojo -> xml
	 */
	public static <T> String toXml(Class<T> clazz, T object) {
		return CLASS_2_XSTREAM_INSTANCE.get(clazz).toXML(object);
	}

	private static Map<Class<?>, XStream> configXStreamInstance() {
		Map<Class<?>, XStream> map = new HashMap<Class<?>, XStream>();

		// 第三方开发者
		map.put(AuthEventNotifyXmlBean.class, config_AuthEventNotifyBean());
		map.put(AuthEventResp.class, config_AuthEventRequest());

		return map;
	}

	private static XStream config_AuthEventRequest() {
		XStream xstream = XStreamInitializer.getInstance();
		xstream.processAnnotations(AuthEventResp.class);
		return xstream;
	}

	private static XStream config_AuthEventNotifyBean() {
		XStream xstream = XStreamInitializer.getInstance();
		xstream.processAnnotations(AuthEventNotifyXmlBean.class);
		return xstream;
	}
}
