package com.cesaas.android.counselor.order.label.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/5/24 11:08
 * Version 1.0
 */

public class GetTagListBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public Integer TagId;
    public String TagName;
    public int ControllerType;//标签分类类型
    public int CategoryId;//父类ID
    private String CategoryName;
    private int Type;//0未选 1已选
    private int pos;
    private int Id;
    public boolean isSelect;
    public boolean isR;
    private String Title;

    public JSONObject getTagInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("Id",getTagId());
            obj.put("Title",getTagName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetTagListBean listBean = (GetTagListBean) o;

        if (ControllerType != listBean.ControllerType) return false;
        if (CategoryId != listBean.CategoryId) return false;
        if (Type != listBean.Type) return false;
        if (pos != listBean.pos) return false;
        if (isSelect != listBean.isSelect) return false;
        if (TagId != null ? !TagId.equals(listBean.TagId) : listBean.TagId != null) return false;
        if (TagName != null ? !TagName.equals(listBean.TagName) : listBean.TagName != null) return false;
        return CategoryName != null ? CategoryName.equals(listBean.CategoryName) : listBean.CategoryName == null;

    }

    @Override
    public int hashCode() {
        int result = TagId != null ? TagId.hashCode() : 0;
        result = 31 * result + (TagName != null ? TagName.hashCode() : 0);
        result = 31 * result + ControllerType;
        result = 31 * result + CategoryId;
        result = 31 * result + (CategoryName != null ? CategoryName.hashCode() : 0);
        result = 31 * result + Type;
        result = 31 * result + pos;
        result = 31 * result + (isSelect ? 1 : 0);
        return result;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean isR() {
        return isR;
    }

    public void setR(boolean r) {
        isR = r;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }
    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public Integer getTagId() {
        return TagId;
    }

    public void setTagId(Integer tagId) {
        TagId = tagId;
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String tagName) {
        TagName = tagName;
    }

    public int getControllerType() {
        return ControllerType;
    }

    public void setControllerType(int controllerType) {
        ControllerType = controllerType;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }
}
