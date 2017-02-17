package com.lbass.common.esclient;

import org.springframework.beans.factory.annotation.Value;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class ESClientFactory {
	
	private JestClientFactory clientFactory;
	@Value("${es.config.user}")
	private String USER;
	@Value("${es.config.password}")
	private String PASSWORD;
	@Value("${es.config.restpath}")
	private String RESTPATH;	
	
	public void setClientFactory(JestClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void init(){
		clientFactory.setHttpClientConfig(
				new HttpClientConfig.Builder(RESTPATH)
					.defaultCredentials(USER, PASSWORD)
					.readTimeout(20000)
					.multiThreaded(true).build()
		);
	}

	@Override
	public String toString() {
		return "ESClientFactory [clientFactory=" + clientFactory + ", esUser=" + USER + ", esUserPassword="
				+ PASSWORD + ", esRestPath=" + RESTPATH + "]";
	}

	public JestClient getObject() {
		return clientFactory.getObject();
	}
	
}
