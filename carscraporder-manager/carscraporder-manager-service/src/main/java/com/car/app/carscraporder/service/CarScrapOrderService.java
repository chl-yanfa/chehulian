package com.car.app.carscraporder.service;

import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderMyTradeBO;
import com.car.app.carscraporder.bo.CarScrapOrderMyTradeInfoBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.CarScrapOrderQuotePageBO;
import com.car.app.carscraporder.bo.OrderAduitBO;
import com.car.app.carscraporder.pojo.CarScrapOrder;
import com.car.app.carscraporder.vo.CarScrapOrderConfirmAmountVO;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.carscraporder.vo.CarScrapOrderQuoteVO;
import com.car.app.carscraporder.vo.CarScrapOrderVO;
import com.car.app.carscraporder.vo.CarScrapOrderWhereParamterVO;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 类名称：CarScrapOrderService   
 * 类描述： 汽车报废订单服务类  
 * 创建人：刘子亮
 * 创建时间：2018年10月9日 下午4:18:13      
 * @version  V1.0
 *
 */
public interface CarScrapOrderService extends BaseService<CarScrapOrder> {

	 PageInfo<CarScrapOrderPageBO>  queryPageListByWhere(Integer page,Integer rows,CarScrapOrderWhereParamterVO paramter)throws Exception;
	
	 PageInfo<CarScrapOrderPageBO>  queryPageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;

	 PageInfo<CarScrapOrderPageBO>  queryPageListByKeywordByChl(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;

	 PageInfo<CarScrapOrderPageBO>  queryPageListByKeywordByExamine(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;

	 PageInfo<CarScrapOrderPageBO>  queryPageListByKeywordByExamineOK(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;

	 PageInfo<CarScrapOrderPageBO>  queryPageListByKeywordByCancel(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	 PageInfo<CarScrapOrderPageBO>  queryPageHistoryListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;

	 PageInfo<CarScrapOrderPageBO>  queryPageHistoryListByKeywordByChl(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	 PageInfo<CarScrapOrderPageBO>  queryAll(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;

	 PageInfo<CarScrapOrderPageBO>  queryAllByChl(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	
	 CarScrapOrderBO queryBOById(String id)throws Exception;
	
	/**
	 * 状态删除
	 * @param id 主键id
	 * @param operator 操作人id
	 * @return
	 * @throws Exception
	 */
	 int deleteByPrimaryKey(String id,String operator)throws Exception;
	
	 Integer cancelOrder(String id,String cancelMemo,String operator)throws Exception;
	
	 Boolean confirmAmount(CarScrapOrderConfirmAmountVO confirmAmountVO)throws Exception;
	
	 Integer update(CarScrapOrderVO record) throws Exception;
	 Integer save(CarScrapOrderVO record)throws Exception ;
	
	
	 CarScrapOrder queryById(String id);
	
	 Integer saveOrderAuditingRecord(String id,Integer orderStatus,String remark,String operator,CarScrapOrder order,String username,String userId) throws Exception;

	 Integer saveOrderAuditingRecord(String id,Integer orderStatus,String remark,String operator,CarScrapOrder order) throws Exception;

	 Integer saveOrderAuditingRecord(String id,Integer orderStatus,String remark,String operator,String whyNo,CarScrapOrder order) throws Exception;

	 OrderAduitBO getOrderAuditInfo(String orderId) throws Exception;

	 CarScrapOrderMyTradeBO myTrade(String clientId)throws Exception;
	
	 PageInfo<CarScrapOrderMyTradeInfoBO> myTradeInfo(Integer page,Integer rows, String clientId)throws Exception;
	
	
	 Boolean quote(CarScrapOrderQuoteVO quoteVO)throws Exception;

	 Boolean quoteByPush(CarScrapOrderQuoteVO quoteVO)throws Exception;
	
	
	 PageInfo<CarScrapOrderQuotePageBO>  queryQuotePageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	
	
}
