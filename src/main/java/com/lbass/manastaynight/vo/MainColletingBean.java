package com.lbass.manastaynight.vo;

import java.util.List;

public class MainColletingBean {
	private String collecDate;
	private List<ChapterBean> chapterBeans;
	
	public String getCollecDate() {
		return collecDate;
	}
	
	public void setCollecDate(String collecDate) {
		this.collecDate = collecDate;
	}
	
	public List<ChapterBean> getChapterBeans() {
		return chapterBeans;
	}
	
	public void setChapterBeans(List<ChapterBean> chapterBeans) {
		this.chapterBeans = chapterBeans;
	}
	
	@Override
	public String toString() {
		return "MainColletingBean [collecDate=" + collecDate + ", chapterBeans=" + chapterBeans + ", getCollecDate()="
				+ getCollecDate() + ", getChapterBeans()=" + getChapterBeans() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

}
