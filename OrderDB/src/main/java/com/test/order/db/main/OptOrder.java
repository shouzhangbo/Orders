package com.test.order.db.main;


import com.test.order.db.entiy.Orders;
import com.test.order.db.global.GlobalContents;
import com.test.order.db.util.CommonUtil;
import com.test.order.db.util.JdbcOptUtil;
import com.test.order.db.util.RedisQueueUtil;

public class OptOrder {

	public static void main(String[] args) {
		String url = "jdbc:postgresql://127.0.0.1:5432/mobile";
		String user = "postgres";
		String psd = "houzhangbo";
		int i = 0;
		RedisQueueUtil qu = new RedisQueueUtil(GlobalContents.RedisQueue.create_queue);
		RedisQueueUtil query = new RedisQueueUtil(GlobalContents.RedisQueue.query_queue);
		JdbcOptUtil jdbc = new JdbcOptUtil(url,user,psd);
		while(true){
			//1.获取queue
			Object q = qu.get();
			System.out.println("q="+q);
			//2.取出头
			if(!CommonUtil.isEmpty(q)){
				i++;
				String orderNo = (String)q;
				//4.验证数据库
				Orders order = jdbc.getByOrderNo(orderNo);
				if(!CommonUtil.isEmpty(order)&&GlobalContents.Order.Create_Order_Success_Stataus.equals(order.getStatus()))
				{
					//5.去处理
					try {
						Thread.sleep(30000);
						if(i%2!=0){
							//成功
							System.out.println("订单号为："+orderNo+"处理成功！");
							//6.处理结果
							//修改数据库
							if(jdbc.updateByorderNo(GlobalContents.Order.Opt_Order_Success_Stataus, 
									GlobalContents.getStatusName(GlobalContents.Order.Opt_Order_Success_Stataus),
									orderNo)){
								System.out.println("订单号为："+orderNo+"写入数据库成功！");
								query.add(orderNo);
							}
						}else{
							//失败
							System.out.println("订单号为："+orderNo+"处理失败！");
							//6.处理结果
							qu.add(orderNo);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
