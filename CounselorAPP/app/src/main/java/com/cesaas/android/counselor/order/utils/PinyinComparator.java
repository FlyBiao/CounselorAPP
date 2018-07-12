package com.cesaas.android.counselor.order.utils;

import java.util.Comparator;

/**
 * 拼音来排列比较器
 * @author FlyBiao
 *
 */
public class PinyinComparator implements Comparator<BaseClass> {

	public int compare(BaseClass o1, BaseClass o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
