package com.cesaas.android.counselor.order.power.utils;

import android.util.Log;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.ActionPower;
import com.cesaas.android.counselor.order.power.bean.MenuDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/31 14:58
 * Version 1.0
 */
public class MenuUtils {

    public static List<MenuDataBean> menuList=new ArrayList<>();
    public static List<MenuDataBean> posTaskMenuList=new ArrayList<>();
    public static List<MenuDataBean> shopMenuList=new ArrayList<>();

    public static List<MenuDataBean> menuList(List<String> actionList){
        menuList=new ArrayList<>();
        if (actionList.indexOf(ActionPower.A_R_S_CM)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("店员管理");
            m.setMenuNo(ActionPower.A_R_S_CM);
            m.setMenuImage(R.string.fa_glass);
            menuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_S_R)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("报数");
            m.setMenuNo(ActionPower.A_R_S_R);
            m.setMenuImage(R.string.goods_collocation);
            menuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_O_N)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("订单");
            m.setMenuNo(ActionPower.A_R_O_N);
            m.setMenuImage(R.string.shop_order);
            menuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_S_P)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("店铺业绩分配");
            m.setMenuNo(ActionPower.A_R_S_P);
            m.setMenuImage(R.string.shop_order);
            menuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_S_PG)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("个人业绩分配");
            m.setMenuNo(ActionPower.A_R_S_PG);
            m.setMenuImage(R.string.shop_order);
            menuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_S_A)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("营销活动");
            m.setMenuNo(ActionPower.A_R_S_A);
            m.setMenuImage(R.string.fa_play_circle_o);
            menuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_F_D)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("会员分配");
            m.setMenuNo(ActionPower.A_R_F_D);
            m.setMenuImage(R.string.shop_order);
            menuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_S_C)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("微信拉粉");
            m.setMenuNo(ActionPower.A_R_S_C);
            m.setMenuImage(R.string.group_contact);
            menuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_O_O)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("O2O发货");
            m.setMenuNo(ActionPower.A_R_O_O);
            m.setMenuImage(R.string.shop_order);
            menuList.add(m);
        }
        if(actionList.indexOf(ActionPower.A_R_F_SA)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("新建服务");
            m.setMenuNo(ActionPower.A_R_F_SA);
            m.setMenuImage(R.string.fa_address_book_o);
            menuList.add(m);
        }if(actionList.indexOf(ActionPower.A_R_F_SC)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("服务检查");
            m.setMenuNo(ActionPower.A_R_F_SC);
            m.setMenuImage(R.string.fa_check_circle);
            menuList.add(m);
        }
        if(actionList.indexOf(ActionPower.A_R_F_IC)!=-1){
            MenuDataBean mm=new MenuDataBean();
            mm.setMenuName("会员资料申请");
            mm.setMenuNo(ActionPower.A_R_F_IC);
            mm.setMenuImage(R.string.user);
            menuList.add(mm);
        }
//        if(actionList.indexOf(ActionPower.A_R_S_PR)!=-1){
//            MenuDataBean mms=new MenuDataBean();
//            mms.setMenuName("店铺业绩分配");
//            mms.setMenuNo(ActionPower.A_R_S_PR);
//            mms.setMenuImage(R.string.fa_line_chart);
//            menuList.add(mms);
//        }

        if(actionList.indexOf(ActionPower.A_R_S_DY)!=-1){
            MenuDataBean mms=new MenuDataBean();
            mms.setMenuName("店长管理店员");
            mms.setMenuNo(ActionPower.A_R_S_DY);
            mms.setMenuImage(R.string.group_contact);
            menuList.add(mms);
        }

//        MenuDataBean mms=new MenuDataBean();
//        mms.setMenuName("商学院");
//        mms.setMenuNo(ActionPower.S_X_Y);
//        mms.setMenuImage(R.string.group_contact);
//        menuList.add(mms);

        return menuList;
    }

    public static List<MenuDataBean> shopMenuList(List<String> actionList){
        shopMenuList=new ArrayList<>();
        if (actionList.indexOf(ActionPower.A_R_R_P)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("商品销售排名");
            m.setMenuNo(ActionPower.A_R_R_P);
            m.setMenuImage(R.string.fa_area_chart);
            shopMenuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_R_PR)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("商品销售报表");
            m.setMenuNo(ActionPower.A_R_R_PR);
            m.setMenuImage(R.string.fa_area_chart);
            shopMenuList.add(m);
        }
//        if (actionList.indexOf(ActionPower.A_R_R_I)!=-1){
//            MenuDataBean m=new MenuDataBean();
//            m.setMenuName("店铺日报");
//            m.setMenuNo(ActionPower.A_R_R_I);
//            m.setMenuImage(R.string.fa_line_chart);
//            shopMenuList.add(m);
//        }
        if (actionList.indexOf(ActionPower.A_R_R_S)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("店铺日报");
            m.setMenuNo(ActionPower.A_R_R_S);
            m.setMenuImage(R.string.fa_line_chart);
            shopMenuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_R_AC)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("店员销售报表");
            m.setMenuNo(ActionPower.A_R_R_AC);
            m.setMenuImage(R.string.fa_line_chart);
            shopMenuList.add(m);
        }
        if (actionList.indexOf(ActionPower.A_R_R_C)!=-1){
            MenuDataBean m=new MenuDataBean();
            m.setMenuName("店员业绩排名");
            m.setMenuNo(ActionPower.A_R_R_C);
            m.setMenuImage(R.string.fa_area_chart);
            shopMenuList.add(m);
        }
        if(actionList.indexOf(ActionPower.A_R_S_RS)!=-1){
            MenuDataBean mm=new MenuDataBean();
            mm.setMenuName("店员排名");
            mm.setMenuNo(ActionPower.A_R_S_RS);
            mm.setMenuImage(R.string.try_record);
            shopMenuList.add(mm);
        }
        if(actionList.indexOf(ActionPower.A_R_F_B)!=-1){
            MenuDataBean mm=new MenuDataBean();
            mm.setMenuName("会员服务统计分析");
            mm.setMenuNo(ActionPower.A_R_F_B);
            mm.setMenuImage(R.string.user_o);
            shopMenuList.add(mm);
        }
        if(actionList.indexOf(ActionPower.A_R_F_CR)!=-1){
            MenuDataBean mms=new MenuDataBean();
            mms.setMenuName("券报表");
            mms.setMenuNo(ActionPower.A_R_F_CR);
            mms.setMenuImage(R.string.fa_line_chart);
            shopMenuList.add(mms);
        }

        if(actionList.indexOf(ActionPower.A_R_S_PR)!=-1){
            MenuDataBean mms=new MenuDataBean();
            mms.setMenuName("店铺业绩分配");
            mms.setMenuNo(ActionPower.A_R_S_PR);
            mms.setMenuImage(R.string.fa_line_chart);
            shopMenuList.add(mms);
        }

        return shopMenuList;
    }

    public static List<MenuDataBean> posTaskMenuList(List<String> actionList){
        posTaskMenuList=new ArrayList<>();
//        if(actionList.indexOf(ActionPower.A_R_O_R)!=-1){
//            MenuDataBean mm=new MenuDataBean();
//            mm.setMenuName("零售");
//            mm.setMenuNo(ActionPower.A_R_O_R);
//            mm.setMenuImage(R.string.group_contact);
//            posTaskMenuList.add(mm);
//        }
        if(actionList.indexOf(ActionPower.A_R_O_D)!=-1){
            MenuDataBean mm=new MenuDataBean();
            mm.setMenuName("调拨");
            mm.setMenuNo(ActionPower.A_R_O_D);
            mm.setMenuImage(R.string.fa_plane);
            posTaskMenuList.add(mm);
        }
        if(actionList.indexOf(ActionPower.A_R_O_SH)!=-1){
            MenuDataBean mm=new MenuDataBean();
            mm.setMenuName("收货");
            mm.setMenuNo(ActionPower.A_R_O_SH);
            mm.setMenuImage(R.string.fa_get_pocket);
            posTaskMenuList.add(mm);
        }
        if(actionList.indexOf(ActionPower.A_R_O_TH)!=-1){
            MenuDataBean mm=new MenuDataBean();
            mm.setMenuName("退货");
            mm.setMenuNo(ActionPower.A_R_O_TH);
            mm.setMenuImage(R.string.fa_outdent);
            posTaskMenuList.add(mm);
        }
        if(actionList.indexOf(ActionPower.A_R_O_BH)!=-1){
            MenuDataBean mm=new MenuDataBean();
            mm.setMenuName("补货");
            mm.setMenuNo(ActionPower.A_R_O_BH);
            mm.setMenuImage(R.string.fa_shopping_cart);
            posTaskMenuList.add(mm);
        }
        if(actionList.indexOf(ActionPower.A_R_O_I)!=-1){
            MenuDataBean mm=new MenuDataBean();
            mm.setMenuName("盘点");
            mm.setMenuNo(ActionPower.A_R_O_I);
            mm.setMenuImage(R.string.fa_barcode);
            posTaskMenuList.add(mm);
        }
        if(actionList.indexOf(ActionPower.A_R_O_TZD)!=-1){
            MenuDataBean mm=new MenuDataBean();
            mm.setMenuName("通知单");
            mm.setMenuNo(ActionPower.A_R_O_TZD);
            mm.setMenuImage(R.string.fa_bell);
            posTaskMenuList.add(mm);
        }

        return posTaskMenuList;
    }
}
