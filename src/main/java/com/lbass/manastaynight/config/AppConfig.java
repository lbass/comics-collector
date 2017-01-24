package com.lbass.manastaynight.config;

public class AppConfig {
	
	//TODO :: 향후 설정 파일 통해 세팅되어야 하며 잠겨있는 맵을 통해 공유되어야 한다.
	private static final String IMAGE_STORE_PATH = "/Users/dowonkim/Desktop/comics/";
	private static final String DEFAULT_URL = "http://manastaynight09.blogspot.kr/";
	
	public static String getPropertyValue(AppConfigEnum key) {
		String value = null;
		if(key == AppConfigEnum.IMAGE_STORE_PATH) {
			value = IMAGE_STORE_PATH;
		} else if(key == AppConfigEnum.DEFAULT_URL) {
			value = DEFAULT_URL;
		}
		return value;
	}
	
	
	
	public enum AppConfigEnum {
		IMAGE_STORE_PATH,
		DEFAULT_URL;
	}
}
