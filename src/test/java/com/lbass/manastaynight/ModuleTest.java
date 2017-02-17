package com.lbass.manastaynight;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ModuleTest {

	@Test
	public void ArrayListFiterTest() {
		List<String> cList = new ArrayList<String>();
		for(int i = 0 ; i < 40 ; i++) {
			cList.add(String.valueOf(i));
		}
		List<String> resultList = new ArrayList<String>();
		for(int i = 0 ; i < cList.size() ; i++) {
			String chapterUri = cList.get(i);
			if(chapterUri.equals("21")) {
				resultList.addAll(cList.subList(0, i));
			}
		}
		System.out.println(resultList);
	}

}
