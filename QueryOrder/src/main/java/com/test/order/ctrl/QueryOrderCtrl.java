package com.test.order.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.order.db.entiy.Orders;
import com.test.order.db.global.GlobalContents;
import com.test.order.db.service.OrderService;
import com.test.order.db.util.CommonUtil;

@Controller
@Scope("prototype")
public class QueryOrderCtrl {
	@Autowired
	private OrderService orderServie;
	
	@RequestMapping(value = "/queryOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
	public Map<String,Object> queryOrder(HttpServletRequest request,HttpServletResponse response,Orders orderForm)
	{
		System.out.println("手机号为："+orderForm.getMobile()+"查询");
		Map<String,Object> map = new HashMap<String,Object>();
		String respCode = "9000";
		String respMsg = "失败";
		if(!CommonUtil.isEmpty(orderForm.getMobile())){
			try{
				Map<String,Object> parMap = new HashMap<String,Object>();
				String sql = "from Orders where mobile=:mobile and status=:status";
				parMap.put("mobile", orderForm.getMobile());
				parMap.put("status", GlobalContents.Order.Query_Order_Success_Stataus);
				List<Orders> orderList = orderServie.find(sql, parMap);
				System.out.println("手机号为："+orderForm.getMobile()+"查询到了"+orderList.size()+"条数据");
				map.put("orderList", orderList);
				respCode = "0000";
				respMsg = "success";
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		return map;
	}
}
