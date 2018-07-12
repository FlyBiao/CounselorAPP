package com.cesaas.android.counselor.order.power.utils;

import android.app.Activity;
import android.os.Bundle;

import com.cesaas.android.counselor.order.OrderActivity;
import com.cesaas.android.counselor.order.activity.WXLaFansActivity;
import com.cesaas.android.counselor.order.activity.main.NewMainActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.DailyActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.NewBossMainActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.VipTargetActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.member.MemberServiceAnalysisActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.member.SingleShopMemberServiceAnalysisActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.shop.MerchantReportActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.shop.ShopResultsActivity;
import com.cesaas.android.counselor.order.global.ActionPower;
import com.cesaas.android.counselor.order.inventory.ui.InventoryMainActivity;
import com.cesaas.android.counselor.order.member.service.ManagerAssisTantActivity;
import com.cesaas.android.counselor.order.member.service.MemberCounselorResultsActivity;
import com.cesaas.android.counselor.order.member.service.MemberDistributionNewActivity;
import com.cesaas.android.counselor.order.member.service.MemberInfoUpdateListActivity;
import com.cesaas.android.counselor.order.member.service.MemberResultsActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceCheckActivity;
import com.cesaas.android.counselor.order.member.volume.TicketReportActivity;
import com.cesaas.android.counselor.order.power.bean.MenuDataBean;
import com.cesaas.android.counselor.order.report.CountOffActivity;
import com.cesaas.android.counselor.order.shopmange.ClerkManageActivity;
import com.cesaas.android.counselor.order.shopmange.ClerkRankingActivity;
import com.cesaas.android.counselor.order.shopmange.ShopActivityListActivity;
import com.cesaas.android.counselor.order.shopmange.ShopSalesActivity;
import com.cesaas.android.counselor.order.shopmange.ShoppingGuideActivity;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.java.ui.activity.require.RequireListActivity;
import com.cesaas.android.java.ui.activity.move.MoveDeliveryListActivity;
import com.cesaas.android.java.ui.activity.notice.NoticeOrderActivity;
import com.cesaas.android.java.ui.activity.receive.ReceiveListActivity;
import com.cesaas.android.java.ui.activity.school.SchoolMainActivity;
import com.cesaas.android.order.ui.activity.NewOrderActivity;
import com.cesaas.android.order.ui.activity.RetailOrderActivity;

/**
 * Author FGB
 * Description
 * Created at 2018/1/31 14:58
 * Version 1.0
 */

public class SkipMenuUtils {

    public static void skipArea(MenuDataBean menuDataBean, Activity mActivity,int resultNo){
            Bundle bundle=new Bundle();
            if(menuDataBean.getMenuNo().equals(ActionPower.A_R_A_M)){//区域首页
                bundle.putInt("resultNo",resultNo);
                Skip.mNextFroData(mActivity,NewBossMainActivity.class,bundle,true);
            }else if (menuDataBean.getMenuNo().equals(ActionPower.A_R_Q_M)){//店员首页
                bundle.putInt("resultNo",resultNo);
                Skip.mNextFroData(mActivity,NewMainActivity.class,bundle,true);
            }
        }

    public static void skipMenu(String menuNo, AbPrefsUtil prefs, Activity mActivity){
       switch (menuNo){
           case ActionPower.A_R_S_CM://店员管理
               Skip.mNext(mActivity,ClerkManageActivity.class);
               break;
           case ActionPower.A_R_S_R://报数
               Skip.mNext(mActivity,CountOffActivity.class);
               break;
           case ActionPower.A_R_O_N://订单
               Skip.mNext(mActivity, OrderActivity.class);
               break;
           case ActionPower.A_R_S_P://分配业绩
//               Skip.mNext(mActivity,PerformanceDistributionActivity.class);
               Skip.mNext(mActivity,MemberResultsActivity.class);
               break;
           case ActionPower.A_R_S_PG://个人业绩分配
               Skip.mNext(mActivity,MemberCounselorResultsActivity.class);
               break;
           case ActionPower.A_R_S_A://营销活动
//               Skip.mNext(mActivity,RecommendShopListActivity.class);
               Skip.mNext(mActivity,ShopActivityListActivity.class);
               break;
           case ActionPower.A_R_F_D://会员分配
//               Skip.mNext(mActivity, MemberDistributionActivity.class);
               Skip.mNext(mActivity, MemberDistributionNewActivity.class);
               break;
           case ActionPower.A_R_S_C://微信蜡粉
               Bundle b=new Bundle();
               b.putString("Ticket", prefs.getString("ticket"));
               Skip.mNextFroData(mActivity, WXLaFansActivity.class,b);
               break;
           case ActionPower.A_R_O_O://o2o发货
               Skip.mNext(mActivity, NewOrderActivity.class);
               break;
           case ActionPower.A_R_R_S://店铺销售排名
//               Skip.mNext(mActivity, ShopPerformanceActivity.class);
               Skip.mNext(mActivity, DailyActivity.class);
               break;
           case ActionPower.A_R_R_P://商品销售排名
               Skip.mNext(mActivity, ShopSalesActivity.class);
               break;
//           case ActionPower.A_R_R_I://日报报表
//               Skip.mNext(mActivity,IntoReportActivity.class);
//               break;
           case ActionPower.A_R_R_C://店员业绩排名
               Skip.mNext(mActivity, ShoppingGuideActivity.class);
               break;
           case ActionPower.A_R_F_SA://会员服务
               Skip.mNext(mActivity, MemberServiceActivity.class);
               break;
           case ActionPower.A_R_F_SC://服务检查
               Skip.mNext(mActivity, MemberServiceCheckActivity.class);
               break;
           case ActionPower.A_R_F_IC://会员资料更改申请列表
               Skip.mNext(mActivity, MemberInfoUpdateListActivity.class);
               break;
           case ActionPower.A_R_R_PR://商品销售报表
               Skip.mNext(mActivity, MerchantReportActivity.class);
               break;
           case ActionPower.A_R_R_I://日报报表
               Skip.mNext(mActivity, DailyActivity.class);
               break;
           case ActionPower.A_R_R_AC://店员销售报表
               Skip.mNext(mActivity, VipTargetActivity.class);
               break;
           case ActionPower.A_R_F_SM://会员服务统计分析
               Skip.mNext(mActivity, MemberServiceAnalysisActivity.class);
               break;
           case ActionPower.A_R_F_B://单店会员服务统计分析
               Skip.mNext(mActivity,SingleShopMemberServiceAnalysisActivity.class);
               break;
           case ActionPower.A_R_S_RS://店员排名
               Skip.mNext(mActivity, ClerkRankingActivity.class);
               break;
           case ActionPower.A_R_S_DY://店长管理店员
               Skip.mNext(mActivity, ManagerAssisTantActivity.class);
               break;
           case ActionPower.A_R_F_CR://券报表分析追踪
               Skip.mNext(mActivity, TicketReportActivity.class);
               break;
           case ActionPower.A_R_S_PR://店铺业绩分配
               Skip.mNext(mActivity,ShopResultsActivity.class);
               break;
           case ActionPower.A_R_O_R://零售单
               Skip.mNext(mActivity, RetailOrderActivity.class);
               break;
           case ActionPower.A_R_O_D://调拨单
               Bundle bundle=new Bundle();
               bundle.putInt("netType",0);
               Skip.mNextFroData(mActivity,MoveDeliveryListActivity.class,bundle);
               break;
           case ActionPower.A_R_O_SH://收货
               Skip.mNext(mActivity,ReceiveListActivity.class);
               break;
           case ActionPower.A_R_O_TH://退货
               Bundle bundle1=new Bundle();
               bundle1.putInt("netType",1);
               Skip.mNextFroData(mActivity,MoveDeliveryListActivity.class,bundle1);
//               Skip.mNextFroData(mActivity,ReturnListActivity.class,bundle1);
               break;
           case ActionPower.A_R_O_BH://补货
               Skip.mNext(mActivity,RequireListActivity.class);
               break;
           case ActionPower.A_R_O_I://盘点
               Skip.mNext(mActivity,InventoryMainActivity.class);
               break;
           case ActionPower.A_R_O_TZD://通知单
               Skip.mNext(mActivity,NoticeOrderActivity.class);
               break;
           case ActionPower.S_X_Y://商学院
               Skip.mNext(mActivity,SchoolMainActivity.class);
               break;
           default:

               break;
       }
    }
}
