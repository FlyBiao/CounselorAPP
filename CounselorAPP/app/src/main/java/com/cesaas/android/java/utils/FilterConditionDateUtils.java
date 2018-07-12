package com.cesaas.android.java.utils;

import com.cesaas.android.java.bean.FilterConditionListBean;
import com.cesaas.android.java.bean.Value;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Author FGB
 * Description 日期过滤条件
 * Created at 2018/5/24 17:40
 * Version 1.0
 */

public class FilterConditionDateUtils {

    public static JSONObject getFilterConditionDate(String startDate,String endDate){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("createTime");
        v.setValue(startDate);
        v.setOperators(4);
        v.setConnector(0);
        arr.put(v.getValueInfo());

        Value v1=new Value();
        v1.setName("createTime");
        v1.setValue(endDate);
        v1.setOperators(5);
        v1.setConnector(0);
        arr.put(v1.getValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterConditionDateAndId(String startDate,String endDate,int id){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("createTime");
        v.setValue(startDate);
        v.setOperators(4);
        v.setConnector(0);
        arr.put(v.getValueInfo());

        Value v1=new Value();
        v1.setName("createTime");
        v1.setValue(endDate);
        v1.setOperators(5);
        v1.setConnector(0);
        arr.put(v1.getValueInfo());

        Value v2=new Value();
        v2.setName("id");
        v2.setId(id);
        v2.setOperators(1);
        v2.setConnector(0);
        arr.put(v2.getPidValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterConditionDateAndIdTotalValue(String startDate,String endDate,int id,int totalValue){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("createTime");
        v.setValue(startDate);
        v.setOperators(4);
        v.setConnector(0);
        arr.put(v.getValueInfo());

        Value v1=new Value();
        v1.setName("createTime");
        v1.setValue(endDate);
        v1.setOperators(5);
        v1.setConnector(0);
        arr.put(v1.getValueInfo());

        Value v2=new Value();
        v2.setName("id");
        v2.setId(id);
        v2.setOperators(1);
        v2.setConnector(0);
        arr.put(v2.getPidValueInfo());

        Value v3=new Value();
        v3.setName("totalValue");
        v3.setId(totalValue);
        v3.setOperators(1);
        v3.setConnector(0);
        arr.put(v3.getPidValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterConditionStatus(int BillStatus){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("status");
        v.setIntValue(BillStatus);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getIntValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterStatus(int status){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("status");
        v.setIntValue(status);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getIntValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterStatusAndDate(int status,String startDate,String endDate){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("status");
        v.setIntValue(status);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getIntValueInfo());

        Value v1=new Value();
        v1.setName("createTime");
        v1.setValue(startDate);
        v1.setOperators(4);
        v1.setConnector(0);
        arr.put(v1.getValueInfo());

        Value v2=new Value();
        v2.setName("createTime");
        v2.setValue(endDate);
        v2.setOperators(5);
        v2.setConnector(0);
        arr.put(v2.getValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterCategoryAndSel(int status,String str,String startDate,String endDate){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        if(status!=0){
            Value v=new Value();
            v.setName("category");
            v.setIntValue(status);
            v.setOperators(1);
            v.setConnector(0);
            arr.put(v.getIntValueInfo());
        }

        Value v=new Value();
        v.setName("no");
        v.setValue(str);
        v.setOperators(0);
        v.setConnector(1);
        arr.put(v.getValueInfo());

        Value v1=new Value();
        v1.setName("createTime");
        v1.setValue(startDate);
        v1.setOperators(4);
        v1.setConnector(0);
        arr.put(v1.getValueInfo());

        Value v2=new Value();
        v2.setName("createTime");
        v2.setValue(endDate);
        v2.setOperators(5);
        v2.setConnector(0);
        arr.put(v2.getValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterCategory(int status,String startDate,String endDate){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        if(status!=0){
            Value v=new Value();
            v.setName("category");
            v.setIntValue(status);
            v.setOperators(1);
            v.setConnector(0);
            arr.put(v.getIntValueInfo());
        }

        Value v1=new Value();
        v1.setName("createTime");
        v1.setValue(startDate);
        v1.setOperators(4);
        v1.setConnector(0);
        arr.put(v1.getValueInfo());

        Value v2=new Value();
        v2.setName("createTime");
        v2.setValue(endDate);
        v2.setOperators(5);
        v2.setConnector(0);
        arr.put(v2.getValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterConditionSelect(String str){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("no");
        v.setValue(str);
        v.setOperators(0);
        v.setConnector(1);
        arr.put(v.getValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterConditionSelectAndDate(String str,String startDate,String endDate){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("no");
        v.setValue(str);
        v.setOperators(0);
        v.setConnector(1);
        arr.put(v.getValueInfo());

        Value v1=new Value();
        v1.setName("createTime");
        v1.setValue(startDate);
        v1.setOperators(4);
        v1.setConnector(0);
        arr.put(v1.getValueInfo());

        Value v2=new Value();
        v2.setName("createTime");
        v2.setValue(endDate);
        v2.setOperators(5);
        v2.setConnector(0);
        arr.put(v2.getValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterConditionSelectPidAndNo(String str,long pid){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("pId");
        v.setId(pid);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getPidValueInfo());

        Value v1=new Value();
        v1.setName("no");
        v1.setValue(str);
        v1.setOperators(1);
        v1.setConnector(0);
        arr.put(v1.getValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getFilterConditionSelectShopName(String str){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("shopName");
        v.setValue(str);
        v.setOperators(0);
        v.setConnector(1);
        arr.put(v.getValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getArrayIds(long id){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        arr.put(id);
        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getPId(long id){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("pId");
        v.setId(id);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getPidValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getPIdAndBoxIdGetSub(long id,long scopeId){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("pId");
        v.setId(id);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getPidValueInfo());

        Value vs=new Value();
        vs.setName("scopeId");
        vs.setId(scopeId);
        vs.setOperators(1);
        vs.setConnector(0);
        arr.put(vs.getPidValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getPIdAndBoxId(long id,long boxId){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("pId");
        v.setId(id);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getPidValueInfo());

        Value vs=new Value();
        vs.setName("boxId");
        vs.setId(boxId);
        vs.setOperators(1);
        vs.setConnector(0);
        arr.put(vs.getPidValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getPIdAndBoxIdAndNo(long id,long boxId,String no){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("pId");
        v.setId(id);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getPidValueInfo());

        Value vs=new Value();
        vs.setName("boxId");
        vs.setId(boxId);
        vs.setOperators(1);
        vs.setConnector(0);
        arr.put(vs.getPidValueInfo());

        Value vvv=new Value();
        vvv.setName("barcodeNo");
        vvv.setValue(no);
        vvv.setOperators(0);
        vvv.setConnector(1);
        arr.put(vvv.getPidValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getPIdAndNo(long id,String no){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("pId");
        v.setId(id);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getPidValueInfo());

        Value vs=new Value();
        vs.setName("barcodeNo");
        vs.setValue(no);
        vs.setOperators(0);
        vs.setConnector(1);
        arr.put(vs.getValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }


    public static JSONObject getId(long id){
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        JSONArray arr=new JSONArray();
        Value v=new Value();
        v.setName("id");
        v.setId(id);
        v.setOperators(1);
        v.setConnector(0);
        arr.put(v.getPidValueInfo());

        filterConditionListBean.setValue(arr);

        return  filterConditionListBean.getFilterConditionList();
    }

    public static JSONObject getRequireType(int type,int requireType,String startDate,String endDate){
        JSONArray arrs=new JSONArray();
        FilterConditionListBean filterConditionListBean=new FilterConditionListBean();
        Value v=new Value();
        v.setName("type");
        v.setId(type);
        v.setOperators(1);
        v.setConnector(0);
        arrs.put(v.getPidValueInfo());

        if(requireType!=0){
            Value vv=new Value();
            vv.setName("status");
            vv.setId(requireType);
            vv.setOperators(1);
            vv.setConnector(0);
            arrs.put(vv.getPidValueInfo());
        }

        Value v1=new Value();
        v1.setName("createTime");
        v1.setValue(startDate);
        v1.setOperators(4);
        v1.setConnector(0);
        arrs.put(v1.getValueInfo());

        Value v2=new Value();
        v2.setName("createTime");
        v2.setValue(endDate);
        v2.setOperators(5);
        v2.setConnector(0);
        arrs.put(v2.getValueInfo());

        filterConditionListBean.setValue(arrs);

        return  filterConditionListBean.getFilterConditionList();
    }
}
