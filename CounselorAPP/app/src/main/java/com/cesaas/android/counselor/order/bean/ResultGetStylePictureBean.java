package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 根据商品款号获取商品图片Bean
 * @author FGB
 *
 */
public class ResultGetStylePictureBean extends BaseBean{

	public ArrayList<ImageArray> TModel;
	
	public class ImageArray implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public String StyleId;//
		public String Url;
		public String ThumbUrl;
		public String StyleCode;
	}
}
