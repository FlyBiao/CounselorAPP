package com.cesaas.android.counselor.order.boss.bean;

import com.cesaas.android.counselor.order.boss.bean.member.TaskTypeShopDataBean;
import com.cesaas.android.counselor.order.member.bean.Tags;

import java.util.List;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Author FGB
 * Description
 * Created at 2017/8/15 10:23
 * Version 1.0
 */

public class ShopListBean extends TaskTypeShopDataBean implements IndexableEntity {

    private int ShopId;
    private String ShopName;
    private int AreaId;
    private String AreaName;
    private int OrganizationId;
    private String OrganizationName;
    private int ShopType;

    private boolean check=false;
    private int pos;
    public boolean isSelect;

    private String nick;
    private String avatar;
    private String mobile;
    private String pinyin;
    private boolean bo;

    private List<Tags> Tags;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }


    public ShopListBean(String nick, String mobile,int ShopId ,boolean check) {
        this.nick = nick;
        this.mobile = mobile;
        this.ShopId=ShopId;
        this.bo=check;
    }

    public ShopListBean() {

    }

    public List<Tags> getTags() {
        return Tags;
    }

    public void setTags(List<Tags> tags) {
        Tags = tags;
    }

    public int getShopType() {
        return ShopType;
    }

    public void setShopType(int shopType) {
        ShopType = shopType;
    }

    public boolean isBo() {
        return bo;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean getBo() {
        return bo;
    }

    public void setBo(boolean bo) {
        this.bo = bo;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String name) {
        this.nick = name;
    }

    @Override
    public String getFieldIndexBy() {
        return nick;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.nick = indexField;
    }


    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        this.pinyin = pinyin;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public void setAreaId(int AreaId) {
        this.AreaId = AreaId;
    }

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    public void setOrganizationId(int OrganizationId) {
        this.OrganizationId = OrganizationId;
    }

    public void setOrganizationName(String OrganizationName) {
        this.OrganizationName = OrganizationName;
    }

    public int getShopId() {
        return ShopId;
    }

    public String getShopName() {
        return ShopName;
    }

    public int getAreaId() {
        return AreaId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public int getOrganizationId() {
        return OrganizationId;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }
}
