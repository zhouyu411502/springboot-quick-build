package org.springboot.quick.commons.servlet;

/**
 * Created by chababa on 7/12/16.
 */

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springboot.quick.commons.exception.MissingParameterException;

import java.nio.ByteBuffer;
import java.util.*;

public class Params {
	public static HashMap<String, String> requestToMap(HttpServletRequest request) {

		HashMap<String, String> parameterMap = new HashMap<String, String>();
		Enumeration<?> names = request.getParameterNames();
		if (names != null) {
			Collections.list(names).stream().filter(name -> name != null).forEach(name -> {
				parameterMap.put(name.toString(), request.getParameter(name.toString()));
			});
		}
		return parameterMap;
	}

	public static Object notNull(Object o) throws MissingParameterException {
		if (o != null) {
			return o;
		}

		StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		throw new MissingParameterException(
				"Null parameter at " + e.getClassName() + '.' + e.getMethodName() + ':' + e.getLineNumber());
	}

	public static String get(HttpServletRequest req, String name) {
		Object ra = req.getAttribute(name);
		if (ra != null) {
			return ra.toString();
		}

		String value = null;
		if ((value = req.getParameter(name)) != null) {
			return value;
		}

		HttpSession s = req.getSession();
		Object sa = s.getAttribute(name);
		if (sa != null) {
			return sa.toString();
		}

		Cookie[] carr = req.getCookies();
		if (carr != null) {
			for (int i = 0; i < carr.length; i++)
				if (carr[i].getName().equals(name))
					return carr[i].getValue();
		}
		return null;
	}

	public static String getString(HttpServletRequest req, String name) {
		return get(req, name);
	}

	public static String needString(HttpServletRequest req, String name) {
		String value = getString(req, name);
		if (value == null) {
			throw new MissingParameterException("Required parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static Integer getInt(HttpServletRequest req, String name) {
		try {
			String v = get(req, name);
			return v == null ? null : Integer.valueOf(v);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Integer needInt(HttpServletRequest req, String name) {
		Integer value = getInt(req, name);
		if (value == null) {
			throw new MissingParameterException("Required int parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static Float getFloat(HttpServletRequest req, String name) {
		try {
			String v = get(req, name);
			return v == null ? null : Float.valueOf(v);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Float needFloat(HttpServletRequest req, String name) {
		Float value = getFloat(req, name);
		if (value == null) {
			throw new MissingParameterException("Required float parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static Double getDouble(HttpServletRequest req, String name) {
		try {
			String v = get(req, name);
			return v == null ? null : Double.valueOf(v);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Double needDouble(HttpServletRequest req, String name) {
		Double value = getDouble(req, name);
		if (value == null) {
			throw new MissingParameterException("Required double parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static Byte getByte(HttpServletRequest req, String name) {
		try {
			String v = get(req, name);
			return v == null ? null : Byte.valueOf(v);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Byte needByte(HttpServletRequest req, String name) {
		Byte value = getByte(req, name);
		if (value == null) {
			throw new MissingParameterException("Required byte parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static Long getLong(HttpServletRequest req, String name) {
		try {
			String v = get(req, name);
			return v == null ? null : Long.valueOf(v);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Long needLong(HttpServletRequest req, String name) {
		Long value = getLong(req, name);
		if (value == null) {
			throw new MissingParameterException("Required long parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static Date getDate(HttpServletRequest req, String name) {
		try {
			Long v = getLong(req, name);
			return v == null ? null : new Date(v.longValue());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Date needDate(HttpServletRequest req, String name) {
		Date value = getDate(req, name);
		if (value == null) {
			throw new MissingParameterException("Required date parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static byte[] getByteArray(HttpServletRequest req, String name) {
		try {
			String v = get(req, name);
			if (v == null) {
				return null;
			}
			int l = v.length() / 2;

			ByteBuffer buf = ByteBuffer.allocate(l);
			for (int i = 0; i < v.length(); i += 2) {
				buf.put(Integer.valueOf(v.substring(i, i + 2), 16).byteValue());
			}

			return buf.array();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] needByteArray(HttpServletRequest req, String name) {
		byte[] value = getByteArray(req, name);
		if (value == null) {
			throw new MissingParameterException(
					"Required byte array parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static Boolean getBoolean(HttpServletRequest req, String name) {
		String v = get(req, name);
		return v == null ? null : Boolean.valueOf(v);
	}

	public static Boolean needBoolean(HttpServletRequest req, String name) {
		Boolean value = getBoolean(req, name);
		if (value == null) {
			throw new MissingParameterException("Required boolean parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static String[] getStringArray(HttpServletRequest req, String name) {
		try {
			String v = get(req, name);
			if (v == null) {
				return null;
			}
			return v.split(",");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static List<String> getStringList(HttpServletRequest req, String name) {
		String[] sa = getStringArray(req, name);
		return sa != null ? Arrays.asList(sa) : null;
	}

	public static List<String> needStringList(HttpServletRequest req, String name) {
		List<String> value = getStringList(req, name);
		if (value == null) {
			throw new MissingParameterException("Required float parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static Integer[] getIntArray(HttpServletRequest req, String name) {
		try {
			String[] sa = getStringArray(req, name);
			if (sa == null) {
				return null;
			}
			Integer[] ia = new Integer[sa.length];
			for (int i = 0; i < sa.length; i++) {
				ia[i] = (sa[i].isEmpty() ? null : Integer.valueOf(sa[i]));
			}
			return ia;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static List<Long> getLongList(HttpServletRequest req, String name) {
		try {
			String[] sa = getStringArray(req, name);
			if (sa == null) {
				return null;
			}
			List<Long> l = new ArrayList<Long>(sa.length);
			for (int i = 0; i < sa.length; i++) {
				l.add(sa[i].isEmpty() ? null : Long.valueOf(sa[i]));
			}

			return l;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static List<Long> needLongList(HttpServletRequest req, String name) {
		List<Long> value = getLongList(req, name);
		if (value == null) {
			throw new MissingParameterException("Required float parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}

	public static Double[] getDoubleArray(HttpServletRequest req, String name) {
		try {
			String[] sa = getStringArray(req, name);
			if (sa == null) {
				return null;
			}
			Double[] da = new Double[sa.length];
			for (int i = 0; i < sa.length; i++) {
				da[i] = (sa[i].isEmpty() ? null : Double.valueOf(sa[i]));
			}
			return da;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static List<Double> getDoubleList(HttpServletRequest req, String name) {
		Double[] da = getDoubleArray(req, name);
		return da != null ? Arrays.asList(da) : null;
	}

	public static Date[] getDateArray(HttpServletRequest req, String name) {
		try {
			String[] sa = getStringArray(req, name);
			if (sa == null) {
				return null;
			}
			Date[] da = new Date[sa.length];
			for (int i = 0; i < sa.length; i++) {
				da[i] = (sa[i].isEmpty() ? null : new Date(Long.valueOf(sa[i]).longValue()));
			}
			return da;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static List<Date> getDateList(HttpServletRequest req, String name) {
		Date[] da = getDateArray(req, name);
		return da != null ? Arrays.asList(da) : null;
	}

	public static List<Date> needDateList(HttpServletRequest req, String name) {
		List<Date> value = getDateList(req, name);
		if (value == null) {
			throw new MissingParameterException("Required float parameter/cookies/attribute but not found:" + name);
		}
		return value;
	}
}
