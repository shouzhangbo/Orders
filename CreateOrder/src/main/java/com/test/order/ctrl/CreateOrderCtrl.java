package com.test.order.ctrl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.test.order.db.util.RedisQueueUtil;

@Controller
@Scope("prototype")
public class CreateOrderCtrl {
	@Autowired
	private OrderService orderServie;
	private static final Logger log = LoggerFactory.getLogger(CreateOrderCtrl.class);
	
	@RequestMapping(value = "/createOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> createOrder(HttpServletRequest request,HttpServletResponse response,Orders orderForm)
	{
		log.info("手机号为："+orderForm.getMobile()+"充值"+orderForm.getAmount()+"元");
		Map<String,Object> map = new HashMap<String,Object>();
		String respCode = "9000";
		String respMsg = "失败";
		if(!CommonUtil.isEmpty(orderForm.getMobile())
				&&!CommonUtil.isEmpty(orderForm.getAmount())
				&&!CommonUtil.isEmpty(orderForm.getUserId())){
			try{
				orderForm.setOrderNo(GlobalContents.getOrderN());
				orderForm.setStatus(GlobalContents.Order.Create_Order_Success_Stataus);
				orderForm.setStatusName(GlobalContents.getStatusName(GlobalContents.Order.Create_Order_Success_Stataus));
				orderForm.setCreatedAt(new Date());
				orderForm.setUpdatedAt(new Date());
				orderServie.save(orderForm);
				RedisQueueUtil queue = new RedisQueueUtil(GlobalContents.RedisQueue.create_queue);
				queue.add(orderForm.getOrderNo());
				log.info("手机号为："+orderForm.getMobile()+"充值成功");
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
