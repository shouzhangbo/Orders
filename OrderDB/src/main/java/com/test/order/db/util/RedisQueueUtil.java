package com.test.order.db.util;

import com.test.order.db.global.GlobalContents;

public class RedisQueueUtil {

	String type;
	
	public RedisQueueUtil(String type){
		System.out.println("type="+type);
		this.type = type;
	}
	public synchronized boolean add(String obj){
		if(GlobalContents.RedisQueue.create_queue.equals(type)){
			return addCreateQueue(obj);
		}else if(GlobalContents.RedisQueue.query_queue.equals(type)){
			return addQueryQueue(obj);
		}
		return false;
	}
	public synchronized Object get(){
		if(GlobalContents.RedisQueue.create_queue.equals(type)){
			return getCreateQueue();
		}else if(GlobalContents.RedisQueue.query_queue.equals(type)){
			return getQueryQueue();
		}
		return false;
	}
	public boolean addCreateQueue(String obj){
		if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.create_end))){
			//初始化
			if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.create_queue+"1"))){
				System.out.println("初始化");
				RedisUtil.set(GlobalContents.RedisQueue.create_end, "1");
				RedisUtil.set(GlobalContents.RedisQueue.create_queue+"1", obj);
			}else{
				return false;
			}
		}else{
			System.out.println("end="+RedisUtil.get(GlobalContents.RedisQueue.create_end));
			System.out.println("create_queue="+RedisUtil.get(GlobalContents.RedisQueue.create_queue+String.valueOf(Integer.parseInt(RedisUtil.get(GlobalContents.RedisQueue.create_end))+1)));
			if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.create_queue+String.valueOf(Integer.parseInt(RedisUtil.get(GlobalContents.RedisQueue.create_end))+1)))){
				RedisUtil.set(GlobalContents.RedisQueue.create_end, String.valueOf(Integer.parseInt(RedisUtil.get(GlobalContents.RedisQueue.create_end))+1));
				System.out.println("end="+RedisUtil.get(GlobalContents.RedisQueue.create_end));
				RedisUtil.set(GlobalContents.RedisQueue.create_queue+RedisUtil.get(GlobalContents.RedisQueue.create_end),obj);
			}else{
				return false;
			}
		}
		return true;
	}
	
	public boolean addQueryQueue(String obj){
		if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.query_end))){
			//初始化
			if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.query_queue+"1"))){
				RedisUtil.set(GlobalContents.RedisQueue.query_end, "1");
				RedisUtil.set(GlobalContents.RedisQueue.query_queue+"1", obj);
			}else{
				return false;
			}
		}else{
			if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.query_queue+String.valueOf(Integer.parseInt(RedisUtil.get(GlobalContents.RedisQueue.query_end))+1)))){
				RedisUtil.set(GlobalContents.RedisQueue.query_end, String.valueOf(Integer.parseInt(RedisUtil.get(GlobalContents.RedisQueue.query_end))+1));
				RedisUtil.set(GlobalContents.RedisQueue.query_queue+RedisUtil.get(GlobalContents.RedisQueue.query_end),obj);
			}else{
				return false;
			}
		}
		return true;
	}
	public Object getCreateQueue(){
		String result ;
		if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.create_end))){
			//空的队列
			return null;
		}
		if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.create_start))){
			//如果还没获取过头部元素的话，发挥第一个
			RedisUtil.set(GlobalContents.RedisQueue.create_start, "1");
			result = RedisUtil.get(GlobalContents.RedisQueue.create_queue+"1");
			RedisUtil.set(GlobalContents.RedisQueue.create_queue+"1","");
			return result;
		}else{
			RedisUtil.set(GlobalContents.RedisQueue.create_start, String.valueOf(Integer.parseInt(RedisUtil.get(GlobalContents.RedisQueue.create_start))+1));
			result = RedisUtil.get(GlobalContents.RedisQueue.create_queue+RedisUtil.get(GlobalContents.RedisQueue.create_start));
			RedisUtil.set(GlobalContents.RedisQueue.create_queue+RedisUtil.get(GlobalContents.RedisQueue.create_start),"");
			return result;
		}
	}
	public Object getQueryQueue(){
		String result ;
		if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.query_end))){
			//空的队列
			return null;
		}
		if(CommonUtil.isEmpty(RedisUtil.get(GlobalContents.RedisQueue.query_start))){
			//如果还没获取过头部元素的话，发挥第一个
			RedisUtil.set(GlobalContents.RedisQueue.query_start, "1");
			result = RedisUtil.get(GlobalContents.RedisQueue.query_queue+"1");
			RedisUtil.set(GlobalContents.RedisQueue.query_queue+"1","");
			return result;
		}else{
			RedisUtil.set(GlobalContents.RedisQueue.query_start, String.valueOf(Integer.parseInt(RedisUtil.get(GlobalContents.RedisQueue.query_start))+1));
			result = RedisUtil.get(GlobalContents.RedisQueue.query_queue+RedisUtil.get(GlobalContents.RedisQueue.query_start));
			RedisUtil.set(GlobalContents.RedisQueue.query_queue+RedisUtil.get(GlobalContents.RedisQueue.query_start),"");
			return result;
		}
	}
}
