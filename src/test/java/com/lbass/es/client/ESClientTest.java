package com.lbass.es.client;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;

public class ESClientTest {

	private static Logger logger = LoggerFactory.getLogger(ESClientTest.class);

	@Test
	public void test() throws IOException {
		// Construct a new Jest client according to configuration via factory
		
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200")
				.defaultCredentials("lbass", "lbass81").multiThreaded(true).build());
		JestClient client = factory.getObject();
		
		/*
		JestResult result = client.execute(new CreateIndex.Builder("articles").build());
		logger.debug(result.getJsonString());

		PutMapping putMapping = new PutMapping.Builder("articles", "my_type",
				"{ \"my_type\" : { \"properties\" : { \"message\" : {\"type\" : \"string\", \"store\" : \"yes\"} } } }")
						.build();
		result = client.execute(putMapping);
		logger.debug(result.getJsonString());
		*/
		String source = jsonBuilder()
				.startObject()
				.field("message", "테스트다테스트")
				.endObject().string();
		/*
		 * String settings = "\"settings\" : {\n" +
		 * "        \"number_of_shards\" : 5,\n" +
		 * "        \"number_of_replicas\" : 1\n" + "    }\n";
		 * 
		 * client.execute(new CreateIndex.Builder("articles")
		 * .settings(Settings.builder().loadFromSource(settings).build().
		 * getAsMap()).build());
		 */

		
		Index putMapper = new Index.Builder(source).index("articles").type("my_type").build();
		logger.debug(putMapper.toString());
		JestResult result = client.execute(putMapper);
		logger.debug(result.getJsonString());

		client.shutdownClient();

		// fail("Not yet implemented");
	}

}
