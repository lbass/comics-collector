package com.lbass.manastaynight.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	public static String getCurrentData() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(new Date());

	}

	public static void main(String[] args) throws ClientProtocolException, IOException {

		System.out.println(" Start Date : " + getCurrentData());
		// 2. 가져올 HTTP 주소 세팅
		HttpGet http = new HttpGet("http://manastaynight09.blogspot.kr/");
	    // 3. 가져오기를 실행할 클라이언트 객체 생성
	    HttpClient httpClient = HttpClientBuilder.create().build();
	    // 4. 실행 및 실행 데이터를 Response 객체에 담음
	    HttpResponse response = httpClient.execute(http);
	    // 5. Response 받은 데이터 중, DOM 데이터를 가져와 Entity에 담음
	    HttpEntity entity = response.getEntity();

	    // 6. Charset을 알아내기 위해 DOM의 컨텐트 타입을 가져와 담고 Charset을 가져옴 
	    ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();

        // 7. DOM 데이터를 한 줄씩 읽기 위해 Reader에 담음 (InputStream / Buffered 중 선택은 개인취향) 
	    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
	    // 8. 가져온 DOM 데이터를 담기위한 그릇
	    StringBuffer sb = new StringBuffer();

	    // 9. DOM 데이터 가져오기
	    String line = "";
	    while((line=br.readLine()) != null){
	    	sb.append(line+"\n");
	    }

	    // 10. 가져온 아름다운 DOM을 보자
	    //System.out.println(sb.toString());
	    //System.out.println("\n\n\n\n\n\n");

	    // 11. Jsoup으로 파싱해보자.
	    Document doc = Jsoup.parse(sb.toString());
	    Elements links = doc.select("a[href]");
	    for(Element link : links) {
	    	System.out.println(link);
	    	System.out.println("\n");
	    }
	    // 참고 - Jsoup에서 제공하는 Connect 처리
	    /*
	    Document doc2 = Jsoup.connect("http://manastaynight09.blogspot.kr/2017/01/14-3.html").get();
	    
	    Elements links = doc2.select("img[src]");
	    for(Element link : links) {
	    	System.out.println(link);
	    	System.out.println("\n");
	    }
	    */
	    //System.out.println("======= JSOUP ========");
	    //System.out.println(doc2.data());
	    // 12. 얼마나 걸렸나 찍어보자
	    //System.out.println(" End Date : " + getCurrentData());
	}

}
