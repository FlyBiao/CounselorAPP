package com.cesaas.android.counselor.order.rong.custom;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.util.Log;

/**
 * 自定义商品消息类
 * @author fgb
 *
 */
@MessageTag(value = "ProductMessage", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage extends MessageContent {
	
	//消息属性，可随意定义
	public String ShopStyleId;
	public String ImageUrl;
	public String Title;
	public String Url;
	public int Type;//商品类型，实物商品或积分商品
	public String Points;//积分
	public double VipPrice;//会员价
	
	public CustomizeMessage(){

	}
	public static CustomizeMessage obtain(String ShopStyleId,String ImageUrl,String Title,String Url,String Points) {
        CustomizeMessage rongRedPacketMessage = new CustomizeMessage();
        rongRedPacketMessage.ShopStyleId=ShopStyleId;
        rongRedPacketMessage.ImageUrl=ImageUrl;
        rongRedPacketMessage.Title=Title;
        rongRedPacketMessage.Url=Url;
        rongRedPacketMessage.Points=Points;
        
        return rongRedPacketMessage;
    }
	/**
	 * 该方法的功能是将消息属性封装成 json 串，再将 json 串转成 byte 数组，该方法会在发消息时调用
	 */
	@Override
	public byte[] encode() {
		
		 JSONObject jsonObj = new JSONObject();

		    try {
		        jsonObj.put("ShopStyleId", ShopStyleId);
		        jsonObj.put("ImageUrl", ImageUrl);
		        jsonObj.put("Title", Title);
		        jsonObj.put("Url", Url);
		        jsonObj.put("Points", Points);
		        
		    } catch (JSONException e) {
		        Log.e("JSONException", e.getMessage());
		    }

		    try {
		        return jsonObj.toString().getBytes("UTF-8");
		    } catch (UnsupportedEncodingException e) {
		        e.printStackTrace();
		    }

		    return null;
	}

	/**
	 * 构造方法，该方法将对收到的消息进行解析，先由 byte 转成 json 字符串，再将 json 中内容取出赋值给消息属性
	 * @param data
	 */
	public CustomizeMessage(byte[] data) {
	    String jsonStr = null;

	    try {
	        jsonStr = new String(data, "UTF-8");
	    } catch (UnsupportedEncodingException e1) {

	    }

	    try {
	        JSONObject jsonObj = new JSONObject(jsonStr);

	        if (jsonObj.has("ShopStyleId"))
	        	ShopStyleId = jsonObj.optString("ShopStyleId");
	        
	        if (jsonObj.has("ImageUrl"))
	        	ImageUrl = jsonObj.optString("ImageUrl");
	        
	        if (jsonObj.has("Title"))
	        	Title = jsonObj.optString("Title");
	        
	        if (jsonObj.has("Url"))
	        	Url = jsonObj.optString("Url");
	        
	        if (jsonObj.has("Points"))
	        	Points = jsonObj.optString("Points");

	    } catch (JSONException e) {
//	        RLog.e(this, "JSONException", e.getMessage());
	    }

	}
	
	/**
	 * 给消息赋值。
	 * @param in
	 */
	public CustomizeMessage(Parcel in) {
		//该类为工具类，消息属性
		ShopStyleId=ParcelUtils.readFromParcel(in);
	    //这里可继续增加你消息的属性
		ImageUrl=ParcelUtils.readFromParcel(in);
		Title=ParcelUtils.readFromParcel(in);
		Url=ParcelUtils.readFromParcel(in);
	  }
	
	
	 /**
	   * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
	   */
	  public static final Creator<CustomizeMessage> CREATOR = new Creator<CustomizeMessage>() {

	      @Override
	      public CustomizeMessage createFromParcel(Parcel source) {
	          return new CustomizeMessage(source);
	      }

	      @Override
	      public CustomizeMessage[] newArray(int size) {
	          return new CustomizeMessage[size];
	      }
	  };
	  
	
	/**
	   * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
	   *
	   * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
	   */
	@Override
	public int describeContents() {
		return 0;
	}

	
	 /**
	   * 将类的数据写入外部提供的 Parcel 中。
	   *
	   * @param dest  对象被写入的 Parcel。
	   * @param flags 对象如何被写入的附加标志。
	   */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		//该类为工具类，对消息中属性进行序列化
		ParcelUtils.writeToParcel(dest, ShopStyleId);
		//这里可继续增加你消息的属性。。
		ParcelUtils.writeToParcel(dest, ImageUrl);
		ParcelUtils.writeToParcel(dest, Title);
		ParcelUtils.writeToParcel(dest, Url);
	}

}
