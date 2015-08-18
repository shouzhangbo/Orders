package com.test.order.opt.main;

import com.test.order.db.global.GlobalContents;
import com.test.order.db.util.CommonUtil;
import com.test.order.db.util.JdbcOptUtil;
import com.test.order.db.util.RedisQueueUtil;

public class QueryOrder {

	public static void main(String[] args) {
		System.out.println("欢迎来到订单查询入口！");
		String url = "jdbc:postgresql://127.0.0.1:5432/mobile";
		String user = "postgres";
		String psd = "houzhangbo";
		int i = 0;
//		RedisQueueUtil qu = new RedisQueueUtil(GlobalContents.RedisQueue.create_queue);
		RedisQueueUtil query = new RedisQueueUtil(GlobalContents.RedisQueue.query_queue);
		JdbcOptUtil jdbc = new JdbcOptUtil(url,user,psd);
		while(true){
			Object obj = query.get();
			if(!CommonUtil.isEmpty(obj)){
				i++;
				String orderNo = (String)obj;
				if(i%2==0){
					System.out.println("订单号为："+orderNo+",查询成功");
					if(jdbc.updateByorderNo(GlobalContents.Order.Query_Order_Success_Stataus, 
							GlobalContents.getStatusName(GlobalContents.Order.Query_Order_Success_Stataus), orderNo)){
						System.out.println("订单号为："+orderNo+",更新成功");
					}
				}else{
					System.out.println("订单号为："+orderNo+",查询失败");
					query.add(orderNo);
				}
			}
		}
	}
}
