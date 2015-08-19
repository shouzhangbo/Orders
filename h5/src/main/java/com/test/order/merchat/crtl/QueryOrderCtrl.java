package com.test.order.merchat.crtl;

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

import com.test.order.db.entiy.Orders;
import com.test.order.db.service.OrderService;
import com.test.order.db.util.CommonUtil;
import com.test.order.db.util.PageInfo;
import com.test.order.merchat.form.OrderForm;
@Controller
@Scope("prototype")
public class QueryOrderCtrl {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "queryOrder", method = {RequestMethod.POST, RequestMethod.GET})
	 public String get(HttpServletRequest request,HttpServletResponse response,OrderForm orderForm)
	{
		System.out.println("queryOrder");
		PageInfo page = new PageInfo();
		if(CommonUtil.isEmpty(orderForm.getPageSize())){
			page.setPageSize(10);
		}
		if(CommonUtil.isEmpty(orderForm.getPage())){
			page.setCurrentPage(1);
		}else{
			page.setCurrentPage(orderForm.getPage());
		}
		Map<String,Object> params = new HashMap<String,Object>();
		String hqlStr = "from Orders where 1=1";
		if(!CommonUtil.isEmpty(orderForm.getMobile())){
			hqlStr = hqlStr + " and mobile=:mobile ";
			params.put("mobile", orderForm.getMobile());
		}else if(!CommonUtil.isEmpty(orderForm.getUserId())){
			hqlStr = hqlStr + "  and userId=:userId";
			params.put("userId", orderForm.getUserId());
		}
		List<Orders> list = orderService.findByPage(hqlStr, params, page);
		request.setAttribute("list", list);
		request.setAttribute("page", orderForm.getPage());
		request.setAttribute("pageSize",orderForm.getPageSize());
		return "/result";
	}
}
