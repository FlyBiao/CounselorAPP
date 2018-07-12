
package com.cesaas.android.counselor.order.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 寄件方信息
 * @author FGB
 *
 */
public class DeliverInfo{
	//到件方详细地址
	private String Address;
	//寄件方所属城市名称，必须是标准的城市称谓如：深圳市（市字不要省略）
	private String City;
	//寄件方公司名称，如果不提供，将从系统默认配置获取
	private String Company;
	//寄件人所在县/区，必须是标准的县/区称谓示例：福田区（区字不要省略）
	private String Country;
	//寄件方联系人如果不提供，将从系统默认配置获取
	private String Contact;
	//寄件方手机 
	private String Mobile;
	//寄件方所在省份，必须是标准的省名称称谓，如：广东省（省字不要省略）
	private String Province;
	//寄件方联系电话
	private String Tel;
	//寄件方邮编代码
	private String ShipperCode;
	
	public JSONObject getDeliverInfo() {
		JSONObject json = new JSONObject();
		try {
			json.put("Address", getAddress());
			json.put("City", getCity());
			json.put("Company", getCompany());
			json.put("Country", getCountry());
			json.put("Contact", getContact());
			json.put("Mobile", getMobile());
			json.put("Province", getProvince());
			json.put("Tel", getTel());
			json.put("ShipperCode", getShipperCode());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getContact() {
		return Contact;
	}

	public void setContact(String contact) {
		Contact = contact;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getShipperCode() {
		return ShipperCode;
	}

	public void setShipperCode(String shipperCode) {
		ShipperCode = shipperCode;
	}

	
	
}
