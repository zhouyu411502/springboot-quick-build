
package org.springboot.quick.commons.crypto;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * ProjectName: springboot-quick-util <br/>
 * ClassName: SHA1 <br/>
 * Date: 2016年11月3日 下午6:07:31 <br/>
 * 
 * @author chababa
 * @version
 * @since JDK 1.7
 * @see
 */
public class SHA1 {

	/**
	 * 串接arr参数，生成sha1 digest
	 *
	 * @param arr
	 * @return
	 */
	public static String gen(String... arr) throws NoSuchAlgorithmException {
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (String a : arr) {
			sb.append(a);
		}
		return DigestUtils.sha1Hex(sb.toString());
	}

	/**
	 * 用&串接arr参数，生成sha1 digest
	 *
	 * @param arr
	 * @return
	 */
	public static String genWithAmple(String... arr) throws NoSuchAlgorithmException {
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			String a = arr[i];
			sb.append(a);
			if (i != arr.length - 1) {
				sb.append('&');
			}
		}
		return DigestUtils.sha1Hex(sb.toString());
	}
}
