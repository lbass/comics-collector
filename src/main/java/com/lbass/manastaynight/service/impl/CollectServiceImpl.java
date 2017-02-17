package com.lbass.manastaynight.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbass.common.utils.Utils;
import com.lbass.manastaynight.crawler.ChapterCrawler;
import com.lbass.manastaynight.crawler.MainCrawler;
import com.lbass.manastaynight.dao.ManastaynightDAO;
import com.lbass.manastaynight.dto.ChapterBean;
import com.lbass.manastaynight.dto.SessionBean;
import com.lbass.manastaynight.service.CollectService;

@Service("collectService")
public class CollectServiceImpl implements CollectService {

	private static Logger logger = LoggerFactory.getLogger(CollectServiceImpl.class);
	
	@Autowired
	private MainCrawler mainCrawler;
	@Autowired
	private ChapterCrawler chapterCrawler;
	@Autowired
	private ManastaynightDAO manastaynightDAO;	
	
	@Override
	public void collectCoimcs() {
		//1. max seq를 조회한다.
		SessionBean lastSession = selectLastSession();
		int newSeq = 0;
		String lastIndex = null;
		if(lastSession != null) {
			newSeq = lastSession.getSeq() + 1;
			lastIndex = lastSession.getLastChpaterURI();			
		} else {
			newSeq = 1;
		}
		SessionBean session = new SessionBean();
		session.setSeq(newSeq);
		session.setCollecDate(Utils.getCurrentDate());
		session.setSuccess(false);
		String sessionId = manastaynightDAO.insertSessionInfo(session);
		
		//2. 이전 수집목록의 가장 마지막 정보를 조회한다. (old index)
		//3. 데이터를 수집한다.
		List<String> collectedList = collectManastaynightList();
		//4. 수집한 데이터 중 old index를 사용하여 마지막 수집 챕터를 확인하고 그 이후의 챕터는 수집대상에서 제외한다.
		List<String> updateList = filterDuplicateList(collectedList, lastIndex);
		//5. 업데이트목록을 통해 만화 데이터를 수집하고 저장하기 위한 정보로 가공한다.
		List<ChapterBean> chpterList = new ArrayList<ChapterBean>();

		String newLastIndex = null;
		
		if(updateList.size() > 0) {
			logger.debug("[Start Collection]");
			newLastIndex = updateList.get(0);
			for(int i = 0 ; i < updateList.size() ; i++) {
				String uri = updateList.get(i);
				ChapterBean bean = chapterCrawler.runCrawling(uri);
				if(bean != null) {
					logger.debug("Add ChapterName : " + bean.getChapterName());
					bean.setOrder(i + 1);
					bean.setSession_seq(newSeq);
					chpterList.add(bean);					
				}
			}
			logger.debug("Collect Count : " + updateList.size());
			logger.debug("[End Collection]");
			//6. 가공된 정보를 저장한다.
			batchInsertChapterData(chpterList);			
		} else {
			newLastIndex = lastIndex;
		}
		
		session.setLastChpaterURI(newLastIndex);
		session.setSuccess(true);
		manastaynightDAO.updateSessionInfo(sessionId, session);

	}
	
	private SessionBean selectLastSession() {
		return manastaynightDAO.selectLastSession();
	}

	private void batchInsertChapterData(List<ChapterBean> chpterList) {
		manastaynightDAO.batchChpaterData(chpterList);
		//System.out.println(result);
	}

	private List<String> filterDuplicateList(List<String> cList, String lastIndex) {
		List<String> resultList = new ArrayList<String>();
		for(int i = 0 ; i < cList.size() ; i++) {
			String chapterUri = cList.get(i);
			if(lastIndex != null && chapterUri.toString().equals(lastIndex)) {
				break;
			}
			resultList.add(chapterUri);
		}
		return resultList;
	}

	private List<String> collectManastaynightList() {
		return mainCrawler.runCrawling();
	}	

}
