package com.cesaas.android.counselor.order.global;

public enum AbMsgable {

	CARTJIA("", 1011), CARTJIAN("", 1012), CARTEDIT("", 1013);

	private String name;
	private int index;

	private AbMsgable(String n, int i) {
		name = n;
		index = i;
	}

	public static String getName(int i) {
		for (AbMsgable message : AbMsgable.values()) {
			if (message.getIndex() == i) {
				return message.getName();
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int i) {
		index = i;
	}

}
