package com.zbeboy.blog.geettest;

/**
 * GeetestWeb配置文件
 * 
 *
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private static final String captcha_id = "4018c36e7e8491045fe45699e0624003";
	private static final String private_key = "141055739abb9ff4ed551c67ba88fc18";

	public static final String getCaptcha_id() {
		return captcha_id;
	}

	public static final String getPrivate_key() {
		return private_key;
	}

}
