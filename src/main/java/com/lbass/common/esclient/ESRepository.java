package com.lbass.common.esclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lbass.common.utils.Utils;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;

@Component("esRepository")
public class ESRepository {

	private static Logger logger = LoggerFactory.getLogger(ESRepository.class);
	
	@Autowired
	private ESClientFactory clientFatory;
	
	protected String post(String index, String type, String id, Object param) {
		logger.debug("[ES Method - post]");
		JestClient client = null;
		String result = null;
		try {
			client = clientFatory.getObject();

			String source = Utils.toJsonString(param);
			logger.debug("Query : " + source);

			Index postMapper = new Index.Builder(source).index(index).type(type).id(id).build();
			logger.debug("URI : " + postMapper.getURI());
			logger.debug("RestMethodName : " + postMapper.getRestMethodName());

			JestResult jestResult = client.execute(postMapper);
			logger.debug("Result : " + jestResult.getJsonString());
			
			result = (String)jestResult.getValue("_id");
			//result = jestResult.getSourceAsObject(result.getClass());

		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}
		return result;
	}
	
	protected String put(String index, String type, Object param) {
		logger.debug("[ES Method - put]");
		JestClient client = null;
		String result = null;
		try {
			client = clientFatory.getObject();

			String source = Utils.toJsonString(param);
			logger.debug("Query : " + source);

			Index putMapper = new Index.Builder(source).index(index).type(type).build();
			logger.debug("URI : " + putMapper.getURI());
			logger.debug("RestMethodName : " + putMapper.getRestMethodName());

			JestResult jestResult = client.execute(putMapper);
			logger.debug("Result : " + jestResult.getJsonString());
			
			result = (String)jestResult.getValue("_id");

		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}
		return result;
	}	
	
	protected void bulk(String index, String type, List param) {
		logger.debug("[ES Method - bulk]");
		JestClient client = null;
		JestResult result = null;
		try {
			client = clientFatory.getObject();

			List bulkList = new ArrayList();
			for(Object obj : param) {
				bulkList.add(new Index.Builder(Utils.toJsonString(obj)).build());
			}
			
			Bulk bulk = new Bulk.Builder()
	                .defaultIndex(index)
	                .defaultType(type)
	                .addAction(bulkList)
	                .build();
			logger.debug("URI : " + bulk.getURI());
			logger.debug("RestMethodName : " + bulk.getRestMethodName());

			result = client.execute(bulk);
			logger.debug("Result : " + result.getJsonString());

		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}	
	}	
	
	public <T> T search(String index, String type, String query, Class<T> returnType) {
		logger.debug("[ES Method - search]");
		JestClient client = null;
		JestResult jestResult = null;
		T result = null;
		try {
			client = clientFatory.getObject();
			logger.debug("Query : " + query);
			
			Search searchMapper = new Search.Builder(query)
					.addIndex(index)
					.addType(type)
					.build();
			logger.debug("URI : " + searchMapper.getURI());
			logger.debug("RestMethodName : " + searchMapper.getRestMethodName());

			jestResult = client.execute(searchMapper);
			logger.debug("Result : " + jestResult.getJsonString());

			result = jestResult.getSourceAsObject(returnType);
		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}
		return result;
	}	

	public JestResult get(String id) {
		JestClient client = null;
		JestResult result = null;
		try {
			client = clientFatory.getObject();

			Get getMapper = new Get.Builder("manastaynight", id).build();
			logger.debug(getMapper.getURI());
			result = client.execute(getMapper);
			logger.debug("Result : " + result.getJsonString());

		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}
		return result;
	}

	public JestResult delete(String id) {
		JestClient client = null;
		JestResult result = null;
		try {
			client = clientFatory.getObject();
			
			Delete putMapper = new Delete.Builder(id).index("manastaynight").type("comics").build();
			logger.debug(putMapper.toString());
			result = client.execute(putMapper);
			logger.debug("Result : " + result.getJsonString());

		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}

		return result;
	}
	
	protected void createIndex(String index, String settings) {
		logger.debug("[ES Method - createIndex]");
		JestClient client = clientFatory.getObject();
		try {
			logger.debug("Query : " + settings);
			CreateIndex indexSetting = new CreateIndex.Builder(index).settings(Utils.toJsonMap(settings)).build();
			logger.debug("URI : " + indexSetting.getURI());
			logger.debug("RestMethodName : " + indexSetting.getRestMethodName());
			JestResult result = client.execute(indexSetting);
			logger.debug("Result : " + result.getJsonString());
						
		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}
	}

	protected void deleteIndex(String index) {
		logger.debug("[ES Method - deleteIndex]");
		JestClient client = clientFatory.getObject();
		try {
			Delete indexDelete = new Delete.Builder(null).index(index).build();
			logger.debug("URI : " + indexDelete.getURI());
			logger.debug("RestMethodName : " + indexDelete.getRestMethodName());
			JestResult result = client.execute(indexDelete);
			logger.debug("Result : " + result.getJsonString());
			
		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}
	}
	
	protected void mappingType(String index, String type, String mappings) {
		logger.debug("[ES Method - mappingType]");
		JestClient client = clientFatory.getObject();
		try {
			logger.debug("Query : " + mappings);
			PutMapping typeSetting = new PutMapping.Builder(index, type, mappings).build();
			logger.debug("URI : " + typeSetting.getURI());
			logger.debug("RestMethodName : " + typeSetting.getRestMethodName());
			JestResult result = client.execute(typeSetting);
			logger.debug("Result : " + result.getJsonString());
			
		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}
	}	
	
}
