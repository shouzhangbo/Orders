package com.test.order.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.test.order.db.entiy.Orders;

public class JdbcOptUtil {
	Connection con = null;
	PreparedStatement pstmt = null; //表示数据库更新操作
	ResultSet result = null;//返回的结果
	String url = "";
	String name = "";
	String psd = "";
	public JdbcOptUtil(String url,String name,String psd){
		this.url = url;
		this.name = name;
		this.psd = psd;
	}
	static{
		try {
			Class.forName("org.postgresql.Driver");//1、使用CLASS 类加载驱动程序  
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
    /**
     * 根据订单号查询
     * @param con
     * @param orderNo
     * @return
     */
	public Orders getByOrderNo( String orderNo){
		Orders order = new Orders();
		String sql = "select * from orders where order_no=?";
		 try {
			con = DriverManager.getConnection(url, name, psd); //2、连接数据库  
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, orderNo); //第一个？号的内容  
			pstmt.executeQuery();
			result = pstmt.executeQuery();
			while(result.next()){
				order.setOrderId(result.getInt("order_id"));
				order.setAmount(result.getInt("amount"));
				order.setOrderNo(result.getString("order_no"));
				order.setMobile(result.getString("mobile"));
				order.setStatus(result.getString("status"));
				order.setUserId(result.getString("user_id"));
				order.setCreatedAt(result.getDate("created_at"));
				order.setUpdatedAt(result.getDate("updated_at"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(!CommonUtil.isEmpty(result)){
					result.close();
				}else if(!CommonUtil.isEmpty(pstmt)){
					pstmt.close();
				}else if(!CommonUtil.isEmpty(con)){
					con.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return order;
	}
	/**
	 * 根据订单号更新状态
	 * @param con
	 * @param status
	 * @param statusName
	 * @param orderNo
	 * @return
	 */
	public boolean updateByorderNo(String status,String statusName,String orderNo){
		boolean flag = false;
		String sql = "update orders set status=?,status_name=? where order_no=?";
		try {
			con = DriverManager.getConnection(url, name, psd); //2、连接数据库 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status); //第一个？号的内容
			pstmt.setString(2, statusName); //第二个？号的内容
			pstmt.setString(3, orderNo); //第三个？号的内容
			pstmt.executeUpdate();
//			con.commit();//提交事务
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(!CommonUtil.isEmpty(pstmt)){
					pstmt.close();
				}else if(!CommonUtil.isEmpty(con)){
					con.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
}
