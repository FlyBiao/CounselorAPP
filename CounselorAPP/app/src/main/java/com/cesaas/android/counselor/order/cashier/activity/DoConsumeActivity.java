package com.cesaas.android.counselor.order.cashier.activity;//package com.cesaas.android.counselor.order.cashier.activity;
//
//import java.util.Arrays;
//import java.util.HashMap;
//
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import cn.hugo.android.scanner.CaptureActivity;
//
//import com.cesaas.android.counselor.order.BasesActivity;
//import com.cesaas.android.counselor.order.R;
//import com.cesaas.android.counselor.order.bean.ResultGetOrderBean;
//import com.cesaas.android.counselor.order.global.Constant;
//import com.cesaas.android.counselor.order.net.GetOrderNet;
//import com.cesaas.android.counselor.order.utils.Skip;
//import com.cesaas.android.counselor.order.utils.ToastFactory;
//
///**
// * 消费页面
// * @author fgb
// *
// */
//public class DoConsumeActivity extends BasesActivity implements View.OnClickListener {
//
//	    private Button btn_sign, btn_consume, btn_searchBalance, btn_revocation, btn_refund,
//	            btn_preAuth, btn_preAuthCancel, btn_preAuthFinish, btn_preAuthFinishCancel,
//	            btn_signOut, btn_init, btn_number, btn_batchSettlement,btn_settting,
//	            btn_ec_searchBalance, btn_ec_xiaofei, btn_go_to_next;
//
//	    private EditText tv_show_msg;
//	    private LinearLayout ll_consume_back;
//
//	    
//	    private byte[] lock = new byte[1];
//	    int EVENT_NO_PAPER = 1;
//	    private final int LOCK_WAIT = lin;
//	    private final int LOCK_CONTINUE = 1;
//	    
//	    private int REQUEST_CONTACT = 20;
//		final int RESULT_CODE = 101;
//		private String scanCode;
//
//	    private PosCore pCore;
//
//	    private PosCallBack callBack;
//	    private String amount;
//	    private String money;
//	    
//	    //订单详情
//		private GetOrderNet getOrderNet;
//
//	    /**
//	     * 8583协议中的参考号
//	     */
//	    private String refNum;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//			setContentView(R.layout.activity_consume);
//			initViews();
//			addListener();
//	    }
//
//	    private void addListener() {
//
//	        tv_show_msg = (EditText) findViewById(R.id.tv_show_msg);
//
//	        btn_sign.setOnClickListener(this);
//	        btn_consume.setOnClickListener(this);
//	        btn_searchBalance.setOnClickListener(this);
//
//	        btn_revocation.setOnClickListener(this);
//	        btn_refund.setOnClickListener(this);
//	        btn_preAuth.setOnClickListener(this);
//	        btn_preAuthCancel.setOnClickListener(this);
//	        btn_preAuthFinish.setOnClickListener(this);
//	        btn_preAuthFinishCancel.setOnClickListener(this);
//	        btn_signOut.setOnClickListener(this);
//	        btn_init.setOnClickListener(this);
//	        btn_number.setOnClickListener(this);
//
//	        btn_batchSettlement.setOnClickListener(this);
//	        btn_settting.setOnClickListener(this);
//
//	        btn_ec_searchBalance.setOnClickListener(this);
//	        btn_ec_xiaofei.setOnClickListener(this);
//
//
//	        btn_go_to_next.setOnClickListener(this);
//	        
//	        ll_consume_back.setOnClickListener(this);
//
//	    }
//
//	    private void initViews() {
//
//	    	ll_consume_back=(LinearLayout) findViewById(R.id.ll_consume_back);
//	    	
//	        btn_sign = (Button) findViewById(R.id.btn_sign);
//	        btn_consume = (Button) findViewById(R.id.btn_consume);
//	        btn_searchBalance = (Button) findViewById(R.id.btn_searchBalance);
//
//	        btn_revocation = (Button) findViewById(R.id.btn_revocation);
//	        btn_refund = (Button) findViewById(R.id.btn_refund);
//	        btn_preAuth = (Button) findViewById(R.id.btn_preAuth);
//	        btn_preAuthCancel = (Button) findViewById(R.id.btn_preAuthCancel);
//	        btn_preAuthFinish = (Button) findViewById(R.id.btn_preAuthFinish);
//	        btn_preAuthFinishCancel = (Button) findViewById(R.id.btn_preAuthFinishCancel);
//	        btn_signOut = (Button) findViewById(R.id.btn_signOut);
//	        btn_init = (Button) findViewById(R.id.btn_init);
//	        btn_number = (Button) findViewById(R.id.btn_number);
//
//	        btn_batchSettlement = (Button) findViewById(R.id.btn_batchSettlement);
//	        btn_settting = (Button) findViewById(R.id.btn_settting);
//
//	        btn_ec_xiaofei = (Button) findViewById(R.id.btn_ec_xiaofei);
//	        btn_ec_searchBalance = (Button) findViewById(R.id.btn_ec_searchBalance);
//
//	        btn_go_to_next = (Button) findViewById(R.id.btn_go_to_next);
//
//	        // 初始化CoreApp连接对象
//	        initPosCore();
//	    }
//
//	    @Override
//	    public void onClick(View v) {
//	        switch (v.getId()) {
//	            case R.id.btn_go_to_next:
////	                Intent intent = new Intent(this, QrActivity.class);
////	                startActivity(intent);
//	                break;
//
//	            case R.id.btn_ec_searchBalance:
//	                do_ec_searchBalance();
//	                break;
//
//	            case R.id.btn_ec_xiaofei:
//	                do_ec_xiaofei();
//	                break;
//
//	            case R.id.btn_settting:
//	                doSettint();
//	                break;
//
//	            case R.id.btn_batchSettlement:
//	                doBatchSettlement();
//	                break;
//
//	            case R.id.btn_init:
//
//	                try {
//	                    Thread.sleep(500);
//	                } catch (InterruptedException e) {
//	                    e.printStackTrace();
//	                }
//
//	                doInit4CoreApp();
//
//	                break;
//	            case R.id.btn_sign://掃描
//	            	// 调用扫描二维码
//	        		Skip.mPosActivityResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
//	                break;
//
//	            case R.id.btn_consume://消费
//	                amount = "2";
//	                lock[lin] = LOCK_WAIT;
//	                doConsumeHasTemplate(amount,scanCode);
//	                break;
//
//	            case R.id.btn_searchBalance:
//	                doSearchBalance();
//	                break;
//
//	            case R.id.btn_revocation:
//	                // TODO 消费撤销
//	                doRevocationHasTemplate();
//	                break;
//
//	            case R.id.btn_refund:
//	                doRefund(refNum, amount);
//	                break;
//
//	            case R.id.btn_preAuth:
//	                amount = "2";
//	                doPreAuth(amount);
//	                break;
//
//	            case R.id.btn_preAuthCancel:
//	                doPreAuthCancel();
//	                break;
//
//	            case R.id.btn_preAuthFinish:
//	                amount = "2";
//	                doPreAuthFinish(refNum, amount);
//	                break;
//
//	            case R.id.btn_preAuthFinishCancel:
//	                doPreAuthFinishCancel(refNum);
//	                break;
//
//	            case R.id.btn_signOut:
//	                doQianTui();
//	                break;
//
//	            case R.id.btn_number:
//	                doSetNumber();
//	                break;
//
//	            case R.id.btn_cancel:
//	                break;
//	                
//	            case R.id.ll_consume_back:
//	            	Skip.mBack(mActivity);
//	            	break;
//	        }
//	    }
//	    
//	    /**
//		 * 处理扫描Activity返回的数据
//		 */
//		@Override
//		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//			
//			if(resultCode==RESULT_CODE) {
//				
//				//pos结账收银
//				if(data.getStringExtra("mPosActivityResult")!=null){
//					if(data.getStringExtra("mPosActivityResult").equals("011")){
//						scanCode= data.getStringExtra("resultCode");
//						ToastFactory.getLongToast(mContext, scanCode);
//						//访问订单详情接口获取订单详情
//						getOrderNet=new GetOrderNet(mContext);
//						getOrderNet.setData(scanCode);
//					}
//				}
//			}
//			
//			super.onActivityResult(requestCode, resultCode, data);
//		}
//		
//		/**
//		 * 获取EventBus订阅事件
//		 * @param msg
//		 */
//		public void onEventMainThread(ResultGetOrderBean msg) {
//			if (msg.TModel.size() !=lin) {//数据不为null，执行收银操作
//				 amount = "2";
//                 lock[lin] = LOCK_WAIT;
//                 doConsumeHasTemplate(amount,scanCode);
//			}
//			else{//数据为null，提示没有获取到数据。
//				ToastFactory.getLongToast(mContext, "没有获取到数据："+msg.Message);
//			}
//		}
//
//	    /**
//	     * 电子现金 消费
//	     */
//	    private void do_ec_xiaofei() {
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    PosCore.RXiaoFei rXiaoFei = pCore.xiaoFei_EC("1", callBack);
//	                    showResult("电子现金消费", rXiaoFei);
//	                } catch (Exception e) {
//	                    showMsg("do_ec_xiaofei:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//	    }
//
//	    /**
//	     * 电子现金 查询余额
//	     */
//	    private void do_ec_searchBalance() {
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    PosCore.RChaXunYuE rChaXunYuE = pCore.chaXunYuE_EC(callBack);
//	                    showMsg("电子现金余额为:" + rChaXunYuE.amount);
//	                } catch (Exception e) {
//	                    showMsg("do_ec_searchBalance:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//	    }
//
//
//
//	    /**
//	     * 设置
//	     */
//	    private void doSettint() {
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    pCore.setting(callBack);
//	                } catch (Exception e) {
//	                    showMsg("doSettint:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//
//	    }
//
//	    @Override
//	    protected void onPause() {
//	        super.onPause();
//	    }
//
//	    private boolean LocalData = true;//如果是收银自己传递批结算数据
//	    /**
//	     * 借记总金额 借记总笔数 消费金额 消费笔数 预授权完成请求金额 预授权完成请求笔数
//	     * 贷记总金额 贷记总笔数 消费撤销金额 消费撤销笔数 退货金额 退货笔记 预授权完成撤销金额 预授权完成撤销笔数
//	     */
//
//	    /**
//	     * 批结算
//	     */
//	    private void doBatchSettlement() {
//	        if (LocalData){
//	            new Thread(new Runnable() {
//	                @Override
//	                public void run() {
//	                    try {
//	                        PosCore.RPiJieSua rPiJieSua = pCore.piJieSuan();
//	                        Log.d(Constant.TAG, "total:" + rPiJieSua.total + "   list:" + Arrays.toString(rPiJieSua.list));
//	                    } catch (Exception e) {
//	                        e.printStackTrace();
//	                        showMsg("doBatchSettlement:"+e.getLocalizedMessage());
//	                    }
//	                }
//	            }).start();
//	            return;
//	        }
//
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    PosCore.RPiJieSua rPiJieSua = pCore.piJieSuan();
//	                    Log.d(Constant.TAG, "total:" + rPiJieSua.total + "   list:" + Arrays.toString(rPiJieSua.list));
//	                } catch (Exception e) {
//	                    showMsg("doBatchSettlement:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//	    }
//
//	    /**
//	     * 签退
//	     */
//	    private void doQianTui() {
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    pCore.qianTui("010", callBack);
//	                } catch (Exception e) {
//	                    showMsg("doQianTui:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//	    }
//
//	    /**
//	     * 预授权完成撤销
//	     */
//	    private void doPreAuthFinishCancel(final String refNum) {
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    PosCore.RYuShouQuanWanChengCheXiao rYuShouQuanWanChengCheXiao = pCore.yuShouQuanWanChengCheXiao(refNum, callBack);
//	                    showResult("预授权完成撤销", rYuShouQuanWanChengCheXiao);
//	                } catch (Exception e) {
//	                    showMsg("doPreAuthFinishCancel:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//	    }
//
//	    /**
//	     * 预授权完成
//	     *
//	     * @param refNum
//	     * @param amount
//	     */
//	    private void doPreAuthFinish(final String refNum, final String amount) {
//
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    PosCore.RYuShouQuanWanCheng rYuShouQuanWanCheng = pCore.yuShouQuanWanCheng(refNum, amount, callBack);
//	                    showResult("预授权完成", rYuShouQuanWanCheng);
//	                    DoConsumeActivity.this.refNum = rYuShouQuanWanCheng.retrievalReferenceNumber;
//	                } catch (Exception e) {
//	                    showMsg("doPreAuthFinish:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//	    }
//
//	    /**
//	     * 预授权撤销
//	     */
//	    private void doPreAuthCancel() {
//	        new Thread() {
//	            public void run() {
//	                try {
//	                    PosCore.RYuShouQuanCheXiao rYuShouQuanCheXiao = pCore.yuShouQuanCheXiao(refNum, callBack);
//	                    showResult("预授权撤销", rYuShouQuanCheXiao);
//	                } catch (Exception e) {
//	                    e.printStackTrace();
//	                }
//	            }
//	        }.start();
//	    }
//
//	    /**
//	     * 预授权
//	     *
//	     * @param amount
//	     */
//	    private void doPreAuth(final String amount) {
//	        new Thread() {
//	            public void run() {
//	                try {
//	                    PosCore.RYuShouQuan rYuShouQuan = pCore.yuShouQuan(amount, callBack);
//	                    showMsg("预授权成功:>>>>\n卡号:" + rYuShouQuan.primaryAccountNumber + "\n" + "参考号:" + rYuShouQuan.retrievalReferenceNumber + "\n凭证号:" + rYuShouQuan.systemTraceAuditNumber);
//	                    refNum = rYuShouQuan.retrievalReferenceNumber;
//	                } catch (Exception e) {
//	                    showMsg("doYuShouQuan:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }.start();
//	    }
//
//	    /**
//	     * 退货
//	     */
//	    private void doRefund(final String refNum, final String amount) {
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    PosCore.RTuiHuo rTuiHuo = pCore.tuiHuo(refNum, amount, callBack);
//	                    showResult("退货", rTuiHuo);
//	                } catch (Exception e) {
//	                    showMsg("doRefund:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//	    }
//
//	    private void showResult(String theme, PosCore.PosResult result) {
//	        showMsg(theme + "成功:>>>>\n卡号:" + result.primaryAccountNumber + "\n" + "参考号:" + result.retrievalReferenceNumber + "\n凭证号:" + result.systemTraceAuditNumber + "\n" + theme + "金额:" + result.amounOfTransactions);
//	    }
//
//	    private void doSetNumber() {
//	        System.out.println("do doSetNumber=========");
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    pCore.setting(new IPosCallBack() {
//	                        @Override
//	                        public void onInfo(String s) throws Exception {
//	                            showMsg(s);
//	                        }
//
//	                        @Override
//	                        public void onEvent(int eventID, Object[] params) throws Exception {
//	                            if (IPosCallBack.EVENT_Setting == eventID) {
//	                                HashMap<String, String> map = pCore.getParam(PosConfig.Name_BatchNo, PosConfig.Name_Number);
//	                                String bs = map.get(PosConfig.Name_BatchNo);
//	                                String ns = map.get(PosConfig.Name_Number);
//	                                if (bs == null || bs.length() != 6) {
//	                                    bs = "000000";
//	                                }
//	                                if (ns == null || ns.length() != 6) {
//	                                    ns = "000000";
//	                                }
//	                                String[] rs = showNumberDialog(bs, ns);
//	                                System.out.println("rs=" + rs);
//	                                if (rs != null) {
//	                                    map.clear();
//	                                    map.put(PosConfig.Name_BatchNo, rs[lin]);
//	                                    map.put(PosConfig.Name_Number, rs[1]);
//	                                    System.out.println(map);
//	                                    pCore.setParam(map);
//	                                }
//	                            }
//	                        }
//	                    });
//	                    showMsg("设置完成.");
//	                } catch (Exception e) {
//	                    showMsg("doSetNumber:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//	    }
//
//	    private void doRevocationHasTemplate() {
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	                try {
//	                    HashMap<String, String> map = new HashMap<String, String>();
//	                    map.put("myOrderNo", "123456789");
//	                    PosCore.RXiaoFeiCheXiao rXiaoFeiCheXiao = pCore.xiaoFeiCheXiao(refNum, map, callBack);
//	                    showMsg("消费撤销成功:>>>>\n卡号:" + rXiaoFeiCheXiao.primaryAccountNumber + "\n" + "参考号:" + rXiaoFeiCheXiao.retrievalReferenceNumber + "\n凭证号:" + rXiaoFeiCheXiao.systemTraceAuditNumber + "\n消费金额:" + rXiaoFeiCheXiao.amounOfTransactions);
//	                    refNum = rXiaoFeiCheXiao.retrievalReferenceNumber;
//	                } catch (Exception e) {
//	                    showMsg("doRevocation:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }).start();
//	    }
//
//	    /**
//	     * 查询余额
//	     */
//	    private void doSearchBalance() {
//	        new Thread() {
//	            public void run() {
//	                try {
//	                    PosCore.RChaXunYuE rChaXunYuE = pCore.chaXunYuE(callBack);
//	                    showMsg("余额为:" + rChaXunYuE.amountStr);
//	                } catch (Exception e) {
//	                    showMsg("doSearchBalance:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }.start();
//	    }
//
//	    /**
//	     * 进行签到操作
//	     */
//	    private void doSign() {
//	        new Thread() {
//	            public void run() {
//	                try {
//	                    pCore.qianDao("010", callBack);
//	                } catch (Exception e) {
//	                    showMsg("doSign:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }.start();
//	    }
//
//	    /**
//	     * 消费
//	     */
//	    private void doConsumeHasTemplate(final String amount ,final String orderNo) {
//	        new Thread() {
//	            public void run() {
//	                try {
//	                    HashMap<String, String> map = new HashMap<String, String>();
//	                    map.put("myOrderNo", orderNo);
//	                    PosCore.RXiaoFei rXiaoFei = pCore.xiaoFei(amount, map, callBack);
//	                    showMsg("消费成功:>>>>\n卡号:" + rXiaoFei.primaryAccountNumber + "\n" + "参考号:" + rXiaoFei.retrievalReferenceNumber + "\n凭证号:" + rXiaoFei.systemTraceAuditNumber + "\n消费金额:" + rXiaoFei.amounOfTransactions);
//	                    refNum = rXiaoFei.retrievalReferenceNumber;
//
//	                } catch (Exception e) {
//	                    Object param = ((PosTaskException) e).params[1];
//
//	                    showMsg("doConsume:" + e.getLocalizedMessage() + "\n错误码:" + param);
//	                    e.printStackTrace();
//	                }
//	            }
//	        }.start();
//	    }
//
//
//	    /**
//	     * 初始化CoreApp连接对象
//	     *
//	     * @return
//	     */
//	    private void initPosCore() {
//	        if (pCore == null) {
//
//	            // 配置数据为开发阶段的数据
//	            HashMap<String, String> init_params = new HashMap<String,String>();
//
//	            init_params.put(PosConfig.Name_EX + "1053", "CoreApp签购单台头");// 签购单小票台头
//
//	            init_params.put(PosConfig.Name_EX + "1100", "cn.weipass.cashier");// 核心APP 包名
//	            init_params.put(PosConfig.Name_EX + "1101", "com.wangpos.cashiercoreapp.services.CoreAppService");// 核心APP 类名
//	            init_params.put(PosConfig.Name_EX + "1093", "2");// 是否需要打印三联签购单 1.需要 2.不需要
//	            init_params.put(PosConfig.Name_EX + "1012", "1");// 华势通道
//
//	            init_params.put(PosConfig.Name_MerchantName, "coreApp");
//
//	            pCore = PosCoreFactory.newInstance(this, init_params);
//	            callBack = new PosCallBack(pCore);
//	        }
//
//	    }
//
//	    /**
//	     * 初始化CoreApp服务
//	     *
//	     * @return
//	     */
//	    private void doInit4CoreApp() {
//	        new Thread() {
//	            public void run() {
//	                try {
//	                    pCore.init(callBack);
//	                } catch (Exception e) {
//	                    showMsg("doInit4CoreApp:" + e.getLocalizedMessage());
//	                    e.printStackTrace();
//	                }
//	            }
//	        }.start();
//	    }
//
//	    private void showMsg(final String msg) {
//	        this.runOnUiThread(new Runnable() {
//	            @Override
//	            public void run() {
//	                tv_show_msg.setText(msg);
//	            }
//	        });
//	    }
//
//
//	    class PosCallBack implements IPosCallBack {
//	        private final PosCore core;
//
//	        PosCallBack(PosCore core) {
//	            this.core = core;
//	        }
//
//	        @Override
//	        public void onInfo(String s) {
//	            showMsg(s);
//	        }
//
//	        @Override
//	        public void onEvent(int eventID, Object[] params) throws Exception {
//	            switch (eventID) {
//	                case 110:
//	                    showMsg("打印空白" + params[lin]);
//	                    break;
//
//	                case EVENT_Setting:{
////	                    HashMap<String, String> param = core.getParam("payTypeList");
////	                    showMsg(param.toString());
//
//	                    core.reprint(refNum);
//	                    showMsg("doSetting:完成");
//
////	                    HashMap<String, String> map = new HashMap<String, String>();
////	                    map.put("zzc", "123");
////	                    HashMap<String, String> search = core.search(map);
////	                    showMsg(search.toString());
////	                    Log.d("search", search.toString());
//	                    break;
//	                }
//
//	                case EVENT_Task_start: {
//	                    showMsg("任务进程开始执行");
//	                    break;
//	                }
//	                case EVENT_Task_end: {
//	                    showMsg("任务进程执行结束");
//	                    break;
//	                }
//	                case EVENT_CardID_start: {
//	                    showMsg("读取银行卡信息");
//	                    break;
//	                }
//	                case EVENT_CardID_end: {
//	                    String cardNum = (String) params[lin];
//	                    if (!TextUtils.isEmpty(cardNum)) {
//	                        Log.w(Constant.TAG, "卡号为:" + params[lin]);
//	                        showConsumeDialog(core);
//	                    }
//	                    break;
//	                }
//	                case EVENT_Comm_start: {
//	                    showMsg("开始网络通信");
//	                    break;
//	                }
//	                case EVENT_Comm_end: {
//	                    showMsg("网络通信完成");
//	                    break;
//	                }
//	                case EVENT_DownloadPlugin_start: {
//	                    showMsg("开始下载插件");
//	                    break;
//	                }
//	                case EVENT_DownloadPlugin_end: {
//	                    showMsg("插件下载完成");
//	                    break;
//	                }
//	                case EVENT_InstallPlugin_start: {
//	                    showMsg("开始安装插件");
//	                    break;
//	                }
//	                case EVENT_InstallPlugin_end: {
//	                    showMsg("插件安装完成");
//	                    break;
//	                }
//	                case EVENT_RunPlugin_start: {
//	                    showMsg("开始启动插件");
//	                    break;
//	                }
//	                case EVENT_RunPlugin_end: {
//	                    showMsg("插件启动完成");
//	                    break;
//	                }
//
//	                case EVENT_AutoPrint_start:{
//	                    showMsg("参考号:" + params[lin]);
//	                    break;
//	                }
//
//	                case IPosCallBack.ERR_InTask:{
//	                    if ((Integer) params[lin] == EVENT_NO_PAPER) {
////	                        showRePrintDialog();
//	                    }
//	                }
//
//	                default: {
//	                    showMsg("Event:" + eventID);
//	                    break;
//	                }
//	            }
//
//	        }
//	    }
//
//	    private void showConsumeDialog(final PosCore core) throws Exception {
//	        lock[lin] = LOCK_WAIT;
//	        runOnUiThread(new Runnable() {
//	            @Override
//	            public void run() {
//	                View view = getLayoutInflater().inflate(R.layout.consume_dialog, null);
//	                final AlertDialog dialog = new AlertDialog.Builder(DoConsumeActivity.this).setView(view).setCancelable(false).create();
//	                dialog.show();
//
//	                Button btn_confirm = (Button) view.findViewById(R.id.btn_consume_confiem);
//	                Button btn_cancel = (Button) view.findViewById(R.id.btn_consume_cancel);
//	                final EditText ed_consumen_amount = (EditText) view.findViewById(R.id.ed_consume_amount);
//
//	                btn_confirm.setOnClickListener(new View.OnClickListener() {
//	                    @Override
//	                    public void onClick(View v) {
//	                        synchronized (lock) {
//	                            money = ed_consumen_amount.getText().toString();
//	                            lock[lin] = LOCK_CONTINUE;
//	                            lock.notify();
//	                        }
//	                        dialog.dismiss();
//	                    }
//	                });
//
//	                btn_cancel.setOnClickListener(new View.OnClickListener() {
//	                    @Override
//	                    public void onClick(View v) {
//	                        synchronized (lock) {
//	                            lock[lin] = LOCK_CONTINUE;
//	                            lock.notify();
//	                        }
//	                        dialog.dismiss();
//	                    }
//	                });
//	            }
//	        });
//
//	        // 等待输入
//	        synchronized (lock) {
//	            while (true) {
//	                if (lock[lin] == LOCK_WAIT) {
//	                    try {
//	                        lock.wait(500);
//	                    } catch (InterruptedException e) {
//	                        e.printStackTrace();
//	                    }
//	                } else {
//	                    break;
//	                }
//	            }
//	        }
//	        core.setXiaoFeiAmount(money);//设置消费金额
//	    }
//
//	    private String[] showNumberDialog(final String batchNo, final String number) throws Exception {
//	        System.out.println("showNumberDialog====");
//
//	        final String[] rs = new String[3];
//	        runOnUiThread(new Runnable() {
//	            @Override
//	            public void run() {
//	                View view = getLayoutInflater().inflate(R.layout.consume_dialog, null);
//	                final AlertDialog dialog = new AlertDialog.Builder(DoConsumeActivity.this).setView(view).setCancelable(false).create();
//	                dialog.show();
//
//	                Button btn_confirm = (Button) view.findViewById(R.id.btn_consume_confiem);
//	                Button btn_cancel = (Button) view.findViewById(R.id.btn_consume_cancel);
////	                final EditText e_batchNo = (EditText) view.findViewById(R.id.batchNo);
//	                final EditText e_number = (EditText) view.findViewById(R.id.number);
//
////	                e_batchNo.setText(batchNo);
//	                e_number.setText(number);
//
//	                btn_confirm.setOnClickListener(new View.OnClickListener() {
//	                    @Override
//	                    public void onClick(View v) {
//	                        System.out.println("showNumberDialog====OK");
////	                        rs[lin] = e_batchNo.getText().toString();
//	                        rs[1] = e_number.getText().toString();
//	                        synchronized (rs) {
//	                            rs[2] = "OK";
//	                            rs.notifyAll();
//	                        }
//	                        dialog.dismiss();
//	                    }
//	                });
//	                btn_cancel.setOnClickListener(new View.OnClickListener() {
//	                    @Override
//	                    public void onClick(View v) {
//	                        System.out.println("showNumberDialog====Cancel");
//	                        synchronized (rs) {
//	                            rs[2] = "Cancel";
//	                            rs.notifyAll();
//	                        }
//	                        dialog.dismiss();
//	                    }
//	                });
//
//	            }
//	        });
//
//
//	        // 等待输入
//	        synchronized (rs) {
//	            while (rs[2] == null) {
//	                try {
//	                    rs.wait(500);
//	                } catch (InterruptedException e) {
//	                    e.printStackTrace();
//	                    return null;
//	                }
//	            }
//	        }
//
//	        System.out.println("dialog end:" + Arrays.toString(rs));
//	        if ("OK".equals(rs[2])) {
//	            return rs;
//	        }
//	        return null;
//	    }
//	}
