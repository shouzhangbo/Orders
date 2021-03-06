package com.test.order.merchat.crtl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.test.order.db.entiy.Orders;
import com.test.order.db.util.CommonUtil;
import com.test.order.db.util.HttpUtil;
import com.test.order.db.util.JsonUtil;

@Controller
@Scope("prototype")
public class CreateOrderCtrl {
	
	private static String url = "http://localhost:8080/CreateOrder/createOrder.json";
	private static String queryUrl = "http://localhost:8080/QueryOrder/queryOrder.json";
	private static String encode = "UTF-8";
	
	private static final Logger log = LoggerFactory.getLogger(CreateOrderCtrl.class);
	
	@RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
	 public Map<String,Object> tet(HttpServletRequest request,HttpServletResponse response,Orders orderForm) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("111", "1213");
		return map;
	}
	@RequestMapping(value = "createOrder", method = {RequestMethod.POST, RequestMethod.GET})
	 public String get(HttpServletRequest request,HttpServletResponse response,Orders orderForm) {
		if(!CommonUtil.isEmpty(orderForm.getAmount())
				&&!CommonUtil.isEmpty(orderForm.getMobile())
				&&!CommonUtil.isEmpty(orderForm.getUserId()))
		{
			log.info("进入了充值接口");
			log.info("请求参数："+orderForm.getMobile()+","+orderForm.getAmount()+","+orderForm.getUserId());
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("mobile", orderForm.getMobile());
			paramsMap.put("amount", String.valueOf(orderForm.getAmount()));
			paramsMap.put("userId", orderForm.getUserId());
			log.info("请求平台的参数："+paramsMap);
			String result = HttpUtil.doPost(url, paramsMap, encode);
			log.info("充值结果："+result);
			JsonObject resultJson = JsonUtil.parseJson(result);
			if("0000".equals(resultJson.get("respCode").getAsString())){
				request.setAttribute("respMsg", "充值成功！");
			}else{
				request.setAttribute("respMsg", "充值失败！");
			}
		}else{
			request.setAttribute("respMsg", "充值失败！");
		}
		 return "/order/result";
	 }
	@RequestMapping(value = "query", method = {RequestMethod.POST, RequestMethod.GET})
	 public String query(HttpServletRequest request,HttpServletResponse response,Orders orderForm) {
		List<Orders> list = new ArrayList<Orders>();
		if(!CommonUtil.isEmpty(orderForm.getAmount())
				&&!CommonUtil.isEmpty(orderForm.getMobile())
				&&!CommonUtil.isEmpty(orderForm.getUserId()))
		{
			if(!CommonUtil.isEmpty(orderForm.getMobile()))
			{
				Map<String,String> paramsMap = new HashMap<String,String>();
				paramsMap.put("mobile", orderForm.getMobile());
				String result = HttpUtil.doPost(url, paramsMap, encode);
				log.info("查询结果："+result);
				JsonObject resultJson = JsonUtil.parseJson(result);
				if("0000".equals(resultJson.get("respCode").getAsString())){
					request.setAttribute("respMsg", "查询成功！");
					JsonArray orderList = resultJson.getAsJsonArray("orderList");
					if(!CommonUtil.isEmpty(orderList)&&orderList.size()>0){
						for(int i=0;i<orderList.size();i++){
							JsonObject json = (JsonObject) orderList.get(i);
							Orders o = new Orders();
							o.setAmount(json.get("amount").getAsInt());
							o.setMobile(json.get("mobile").getAsString());
							o.setUserId(json.get("userId").getAsString());
							list.add(o);
						}
					}
				}else{
					request.setAttribute("respMsg", "查询失败！");
				}
			}
		}
		request.setAttribute("list", list);
		return "/order/result2";
	}
	
	public static void main(String[] args) {
		String url = "http://localhost:8082/CreateOrder/createOrder.json";
		String encode = "UTF-8";
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("mobile", "18811431531");
		paramsMap.put("userId", "123");
		paramsMap.put("amount", "12312");
		String result = HttpUtil.doPost(url, paramsMap, encode);
		System.out.println(result);
	}
}
