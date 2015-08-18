package com.test.order.db.global;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalContents {

	public static final int queue_length = 10000;
	public class RedisQueue{
		//创建订单
		public static final String create_queue = "create_queue_";
		public static final String create_start = "create_start";
		public static final String create_end = "create_end";
		
		//订单查询
		public static final String query_queue = "query_queue_";
		public static final String query_start = "query_start";
		public static final String query_end = "query_end";
	}
	public class Redis{
		//创建订单
		public static final String create_redis_success = "create_redis_success_";
		public static final String create_start = "create_start";
		public static final String create_end = "create_end";
		//订单处理成功
		public static final String opt_redis_success = "opt_redis_success_";
		public static final String  opt_suss_start = "opt_suss_start";
		public static final String  opt_suss_end = "opt_suss_end";
		//订单处理失败
		public static final String opt_redis_falid = "opt_redis_falid_";
		public static final String  opt_fail_start = "opt_fail_start";
		public static final String  opt_fail_end = "opt_fail_end";
		//订单查询失败
		public static final String query_redis_falid = "query_redis_falid_";
		public static final String query_start = "query_start";
		public static final String query_end = "query_end";
	}
	public class QueueType{
//		public static final String Create_Queue_Type = "1";
		public static final String Create_Queue_Key = "Create_Queue_Name";
//		public static final String Query_Queue_Type = "2";
		public static final String Query_Queue_Key = "Query_Queue_Name";
	}
	public class Order{
		public static final String Create_Order_Success_Stataus = "0";//下单成功
		public static final String Opt_Order_Success_Stataus = "1";//订单处理成功
		public static final String Opt_Order_Faild_Stataus = "2";//订单处理失败
		public static final String Query_Order_Success_Stataus = "3";//订单查询成功
		public static final String Query_Order_Faild_Stataus = "4";//订单查询失败
	}
	
	public  static String getStatusName(String status){
		if(GlobalContents.Order.Create_Order_Success_Stataus.equals(status)){
			return "创建订单成功";
		}else if(GlobalContents.Order.Opt_Order_Success_Stataus.equals(status)){
			return "订单处理成功";
		}else if(GlobalContents.Order.Opt_Order_Faild_Stataus.equals(status)){
			return "订单处理失败";
		}else if(GlobalContents.Order.Query_Order_Success_Stataus.equals(status)){
			return "订单查询成功";
		}else if(GlobalContents.Order.Query_Order_Faild_Stataus.equals(status)){
			return "订单查询失败";
		}
		return "";
	}
	public static String getOrderN(){
		return new SimpleDateFormat("yyyymmddHHmmsss").format(new Date());
	}
}
