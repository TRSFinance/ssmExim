package com.trs.commodity.controller.dataAnalyse;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimerTask;
import org.apache.commons.dbcp.BasicDataSource;

import com.trs.commodity.controller.dataAnalyse.ProRtComtradeFene;
import com.trs.commodity.model.CommodityInfo;

public class RtComtradeFeneToDB extends TimerTask{

	
	BasicDataSource dataSource;
	
	public RtComtradeFeneToDB() {

	}
	public RtComtradeFeneToDB(BasicDataSource dataSource) {
        this.dataSource=dataSource;
	}
	/**
	 * 入库操作主方法
	 */
	public void run(){	
		if(ProRtComtradeFene.queueEnd.isEmpty()&&ProRtComtradeFene.isBreak){
			System.out.println("待处理数据为空,并且数据处理进程已关闭,取消入库定时任务。。。");
        	ProRtComtradeFene.timer.cancel();
        }
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);//设置数据库操作为手动提交，进行批处理
			stmt = conn.createStatement();
			int t = 0;//初始化计数器
			while(!ProRtComtradeFene.queueEnd.isEmpty()){//开始将分析后的数据入库
				System.out.println("存入第"+t+"条处理后的数据到集合中");
				CommodityInfo comm =ProRtComtradeFene.queueEnd.poll();
				String sql = "update commodity_info set reporter_trade_rate="+comm.getReporterTradeRate()+" where yr="+comm.getYr()
						+" and reporter_code= "+comm.getReporterCode()+" and partner_code="+comm.getPartnerCode()
						+" and trade_flow_code= "+comm.getTradeFlowCode()+" and commodity_code= '"+comm.getCommodityCode()+"'";
				stmt.addBatch(sql);
				t++;
				if(t%100==0){//如果对象存放对象达到3000个，执行一次入库操作
					stmt.executeBatch();
					conn.commit();
				}			
			}
			stmt.executeBatch();
			conn.commit();               
		} catch (SQLException e) {
			if(conn!=null){
				try {					
					conn.rollback();						
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{			
			if(stmt!=null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		}	
	}
	public BasicDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	
}
