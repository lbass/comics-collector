package com.lbass.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.lbass.manastaynight.crawler.MainCrawler;
import com.lbass.manastaynight.dto.ChapterBean;

public class Utils {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String stringReplace(String str) {
		String regExpSpcial = "[\"'\\{\\}\\[\\]/,?.;:|\\)\\(*~`!^\\_+<>@#$%^\\\\=]";
		str = str.replaceAll(regExpSpcial, "");
		str = str.replaceAll("\\s", "_");
		return str;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(stringReplace("거미입니다만, 뭔가 14-3"));
	}

	public static int getIntegerValue(Object obj) {
		int result = 0;
		if(obj == null) {
			return result;
		}
		if(obj instanceof Double) {
			result = ((Double)obj).intValue();
		} else if(obj instanceof Float) {
			result = ((Float)obj).intValue();
		} else {
			throw new RuntimeException("not support type");
		}
		return result;
	}

	public static String toJsonString(Object param) {
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(param);
		return json.toString();
	}
	
	public static HashMap toJsonMap(String param) {
		Gson gson = new Gson();
		HashMap jsonMap = gson.fromJson(param, HashMap.class);
		return jsonMap;
	}	
}
