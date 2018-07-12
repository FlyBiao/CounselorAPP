package com.cesaas.android.counselor.order.bean;

/**
 * 自定义菜单权限
 * @author FGB
 *
 */
public class CustomMenuPowerBean {

	private int id;
	private String menuName;//菜单名称
	private String menuNo;//菜单编号
	private String menuParentNo;//父级菜单
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}
	public String getMenuParentNo() {
		return menuParentNo;
	}
	public void setMenuParentNo(String menuParentNo) {
		this.menuParentNo = menuParentNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
