package com.lbass.manastaynight.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lbass.common.esclient.ESRepository;
import com.lbass.manastaynight.dto.ChapterBean;
import com.lbass.manastaynight.dto.SessionBean;

@Component("manastaynightDAO")
public class ManastaynightDAO extends ESRepository {
	
	private static Logger logger = LoggerFactory.getLogger(ManastaynightDAO.class);
	
	@Value("${es.msn.index}")
	private String INDEX;
	@Value("${es.msn.type.comics}")
	private String TYPE_COMICS;
	@Value("${es.msn.type.session}")
	private String TYPE_SESSION;	

	public String insertSessionInfo(SessionBean sessionInfo) {
		String resultId = put(INDEX, TYPE_SESSION, sessionInfo);
		return resultId;
	}	
	
	public String updateSessionInfo(String id, SessionBean sessionInfo) {
		String resultId = post(INDEX, TYPE_SESSION, id, sessionInfo);
		return resultId;
	}

	public SessionBean selectLastSession() {
		String query = "{" +
				  "\"size\": 1," +
				  "\"query\": {" +
				    "\"match\": {" +
				      "\"success\": true" +  
				    "}" +
				  "}," +
				  "\"sort\": [" +
				    "{" +
				      "\"seq\": {" +
				        "\"order\": \"desc\"" +
				      "}" +
				    "}" +
				  "]" +
				"}";
		return search(INDEX, TYPE_SESSION, query, SessionBean.class);
	}
	
	public void batchChpaterData(List<ChapterBean> param) {
		bulk(INDEX, TYPE_COMICS, param);
	}
	
	public void initRepository() {
		deleteIndex(INDEX);

		String settings = "{" +
				"\"settings\": {" +
					"\"index\": {" +
						"\"analysis\": {" +
							"\"analyzer\": {" +
								"\"korean\": {" +
									"\"type\": \"custom\"," +
									"\"tokenizer\": \"seunjeon_default_tokenizer\"" +
								"}" +
							"}," +
							"\"tokenizer\": { " +
								"\"seunjeon_default_tokenizer\": {" +
									"\"type\": \"seunjeon_tokenizer\"," +
									"\"user_words\": [ ]" +
								"}" +
							"}" +
						"}" +
					"} " +
				"}" +
			"} ";
		createIndex(INDEX, settings);

		String comicsMapping = "{" +
				"\"comics\": {" +
					"\"properties\": {" +
						"\"title\": {" +
							"\"type\": \"string\"," +
							"\"analyzer\": \"korean\"" +
						"}," +
						"\"chapterName\": {" +
							"\"type\": \"string\"," +
							"\"analyzer\": \"korean\"" +
						"}," +
						"\"session_seq\": {" +
							"\"type\": \"long\"" +
						"}," +
						"\"order\": {" +
							"\"type\": \"long\"" +
						"}" +
					"}" +
				"}" +
			"}";
		mappingType(INDEX, TYPE_COMICS, comicsMapping);

		String sessionMapping = "{" +
				"\"session\": {" +
					"\"properties\": {" +
						"\"seq\": {" +
							"\"type\": \"long\"" +
						"}" +
					"}" +
				"}" +
			"}";
		
		mappingType(INDEX, TYPE_SESSION, sessionMapping);
		
	}

	public String insertChapter(ChapterBean param) {
		return put(INDEX,TYPE_COMICS, param);
	}
}
