package com.cesaas.android.counselor.order.rong.custom;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 自定义人脸识别消息类
 * @author fgb
 *
 */
@MessageTag(value = "CameraNotify", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class FaceMessage extends MessageContent {

	public String Sex;
	public String VipId;
	public String  Name;
	public String ImageUrl;
	public String FaceFrame;
	public String CreateTime;

	public FaceMessage(){

	}
	public static FaceMessage obtain(String Sex,String VipId,String Name,String ImageUrl,String FaceFrame,String CreateTime) {
        FaceMessage rongRedPacketMessage = new FaceMessage();
        rongRedPacketMessage.Sex=Sex;
        rongRedPacketMessage.VipId=VipId;
        rongRedPacketMessage.Name=Name;
        rongRedPacketMessage.ImageUrl=ImageUrl;
		rongRedPacketMessage.FaceFrame=FaceFrame;
		rongRedPacketMessage.CreateTime=CreateTime;

        return rongRedPacketMessage;
    }
	/**
	 * 该方法的功能是将消息属性封装成 json 串，再将 json 串转成 byte 数组，该方法会在发消息时调用
	 */
	@Override
	public byte[] encode() {

		 JSONObject jsonObj = new JSONObject();

		    try {
		        jsonObj.put("Sex", Sex);
		        jsonObj.put("VipId", VipId);
		        jsonObj.put("Name", Name);
		        jsonObj.put("ImageUrl", ImageUrl);
		        jsonObj.put("FaceFrame", FaceFrame);
		        jsonObj.put("CreateTime", CreateTime);

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
	public FaceMessage(byte[] data) {
	    String jsonStr = null;

	    try {
	        jsonStr = new String(data, "UTF-8");
	    } catch (UnsupportedEncodingException e1) {

	    }

	    try {
	        JSONObject jsonObj = new JSONObject(jsonStr);

	        if (jsonObj.has("Sex"))
				Sex = jsonObj.optString("Sex");

	        if (jsonObj.has("VipId"))
				VipId = jsonObj.optString("VipId");

	        if (jsonObj.has("Name"))
				Name = jsonObj.optString("Name");

			if (jsonObj.has("ImageUrl"))
				ImageUrl = jsonObj.optString("ImageUrl");

			if (jsonObj.has("FaceFrame"))
				FaceFrame = jsonObj.optString("FaceFrame");

			if (jsonObj.has("CreateTime"))
				CreateTime = jsonObj.optString("CreateTime");

	    } catch (JSONException e) {
//	        RLog.e(this, "JSONException", e.getMessage());
	    }

	}

	/**
	 * 给消息赋值。
	 * @param in
	 */
	public FaceMessage(Parcel in) {
		//该类为工具类，消息属性
		Sex=ParcelUtils.readFromParcel(in);
	    //这里可继续增加你消息的属性
		VipId=ParcelUtils.readFromParcel(in);
		Name=ParcelUtils.readFromParcel(in);
		ImageUrl=ParcelUtils.readFromParcel(in);
		FaceFrame=ParcelUtils.readFromParcel(in);
		CreateTime=ParcelUtils.readFromParcel(in);
	  }
	
	
	 /**
	   * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
	   */
	  public static final Creator<FaceMessage> CREATOR = new Creator<FaceMessage>() {

	      @Override
	      public FaceMessage createFromParcel(Parcel source) {
	          return new FaceMessage(source);
	      }

	      @Override
	      public FaceMessage[] newArray(int size) {
	          return new FaceMessage[size];
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
		ParcelUtils.writeToParcel(dest, Sex);
		//这里可继续增加你消息的属性。。
		ParcelUtils.writeToParcel(dest, VipId);
		ParcelUtils.writeToParcel(dest, Name);
		ParcelUtils.writeToParcel(dest, ImageUrl);
		ParcelUtils.writeToParcel(dest, FaceFrame);
		ParcelUtils.writeToParcel(dest, CreateTime);
	}

}
