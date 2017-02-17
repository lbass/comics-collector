package com.lbass.manastaynight.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbass.manastaynight.crawler.impl.MainCrawler;

public class Utils {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static String getCurrentData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
}
