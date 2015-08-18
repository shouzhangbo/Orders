package com.test.order.db.util;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.omg.CORBA.COMM_FAILURE;

import com.test.order.db.global.GlobalContents;


public class RedisQueue {
	String type;
	Object obj;
//	public RedisQueue(){}
	public RedisQueue(String type){
		this.type = type;
	}
	/**
	 * 申请获取队列
	 * @return
	 */
	public Object getQueue(){
		if(!GlobalContents.QueueType.Create_Queue_Key.equals(type)&&!
				GlobalContents.QueueType.Query_Queue_Key.equals(type)){
			return null;
		}
		if(CommonUtil.isEmpty(RedisUtil.get(type))){
			Queue<Object> q = new LinkedBlockingQueue<Object>(10000);
			RedisUtil.set(type, CommonUtil.getSerializable(q));
			return q;
		}
		obj = CommonUtil.UnSerializable(RedisUtil.get(type));
		return obj;
	}
	/**
	 * 关闭队列，将其写入redis中
	 */
	public void close(){
		if(!GlobalContents.QueueType.Create_Queue_Key.equals(type)&&
				!GlobalContents.QueueType.Query_Queue_Key.equals(type)||
				CommonUtil.isEmpty(obj)){
			System.out.println("错误了，走到这里了！");
		}else{
			RedisUtil.set(type, CommonUtil.getSerializable(obj));
		}
	}
	
	public static boolean add(String type,String orderNo){
		boolean flag = false;
		//创建订单
		if(GlobalContents.Redis.create_redis_success.equals(type)){
			String start = RedisUtil.get(GlobalContents.Redis.create_start);
			if(!CommonUtil.isEmpty(start)){
				if(Integer.parseInt(start)>=GlobalContents.queue_length){
					if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.Redis.create_redis_success+"1"))){
						RedisUtil.set(GlobalContents.Redis.create_redis_success+"1", orderNo);
						RedisUtil.set(GlobalContents.Redis.create_start, "1");
					}else{
						return false;
					}
				}else{
					if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.Redis.create_redis_success+
							String.valueOf(Integer.parseInt(GlobalContents.Redis.create_start)+1))))
					{
						RedisUtil.set(String.valueOf(Integer.parseInt(GlobalContents.Redis.create_start)+1), orderNo);
						RedisUtil.set(GlobalContents.Redis.create_start, String.valueOf(Integer.parseInt(GlobalContents.Redis.create_start)+1));
					}else{
						return false;
					}
				}
			}else{
				//初始化
				if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.Redis.create_redis_success+"1"))){
					RedisUtil.set(GlobalContents.Redis.create_redis_success+"1", orderNo);
					RedisUtil.set(GlobalContents.Redis.create_start, "1");
				}else{
					return false;
				}
			}
			//操作成功
		}else if(GlobalContents.Redis.opt_redis_success.equals(type)){
			String start = RedisUtil.get(GlobalContents.Redis.opt_suss_start);
			if(!CommonUtil.isEmpty(start)){
				if(Integer.parseInt(start)>=GlobalContents.queue_length){
					if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.Redis.opt_redis_success+"1"))){
						RedisUtil.set(GlobalContents.Redis.opt_redis_success+"1", orderNo);
						RedisUtil.set(GlobalContents.Redis.opt_suss_start, "1");
					}else{
						return false;
					}
				}else{
					if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.Redis.opt_redis_success+
							String.valueOf(Integer.parseInt(GlobalContents.Redis.opt_suss_start)+1))))
					{
						RedisUtil.set(String.valueOf(Integer.parseInt(GlobalContents.Redis.opt_suss_start)+1), orderNo);
						RedisUtil.set(GlobalContents.Redis.opt_suss_start, String.valueOf(Integer.parseInt(GlobalContents.Redis.opt_suss_start)+1));
					}else{
						return false;
					}
				}
			}else{
				//初始化
				if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.Redis.opt_redis_success+"1"))){
					RedisUtil.set(GlobalContents.Redis.opt_redis_success+"1", orderNo);
					RedisUtil.set(GlobalContents.Redis.opt_suss_start, "1");
				}else{
					return false;
				}
			}
		}
		return flag;
	}
	public static void main(String[] args) throws ClassNotFoundException {
		String start = RedisUtil.get(GlobalContents.Redis.create_start);
		System.out.println(start);
//		Queue<Object> q = new LinkedBlockingQueue<Object>(5);
//		System.out.println(q.offer("1"));
//		System.out.println(q.offer("2"));
//		System.out.println(q.offer("3"));
//		System.out.println(q.offer("4"));
//		System.out.println(q.offer("5"));
//		System.out.println("第一个："+q.peek());
//		System.out.println(q);
//		System.out.println("返回并删除"+q.poll());
//		System.out.println(q);
//		
//		LinkedBlockingQueue<Object> qq = (LinkedBlockingQueue<Object>)CommonUtil.UnSerializable(CommonUtil.getSerializable(q));
//        System.out.println("qq="+qq);
	}
}
