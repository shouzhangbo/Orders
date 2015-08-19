package com.test.order.merchat.crtl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.test.order.db.entiy.Orders;
import com.test.order.db.util.CommonUtil;
import com.test.order.db.util.HttpUtil;
import com.test.order.db.util.JsonUtil;

@Controller
@Scope("prototype")
public class CreateOrderCtrl {
	
	private static String url = "http://localhost:8080/CreateOrder/createOrder.json";
	private static String encode = "UTF-8";
	
	@RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
	 public Map<String,Object> tet(HttpServletRequest request,HttpServletResponse response,Orders orderForm) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("111", "1213");
		return map;
	}
	@RequestMapping(value = "/h5/createOrder", method = {RequestMethod.POST, RequestMethod.GET})
	 public String get(HttpServletRequest request,HttpServletResponse response,Orders orderForm) {
		System.out.println("1111");
		if(!CommonUtil.isEmpty(orderForm.getAmount())
				&&!CommonUtil.isEmpty(orderForm.getMobile())
				&&!CommonUtil.isEmpty(orderForm.getUserId()))
		{
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("mobile", orderForm.getMobile());
			paramsMap.put("amount", String.valueOf(orderForm.getAmount()));
			paramsMap.put("userId", orderForm.getUserId());
			String result = HttpUtil.doPost(url, paramsMap, encode);
			System.out.println("充值结果："+result);
			JsonObject resultJson = JsonUtil.parseJson(result);
			if("0000".equals(resultJson.get("respCode").getAsString())){
				request.setAttribute("respMsg", "充值成功！");
			}else{
				request.setAttribute("respMsg", "充值失败！");
			}
		}else{
			request.setAttribute("respMsg", "充值失败！");
		}
		 return "/h5/result";
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
