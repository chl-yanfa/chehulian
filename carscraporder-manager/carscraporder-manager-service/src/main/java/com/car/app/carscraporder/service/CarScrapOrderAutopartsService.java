package com.car.app.carscraporder.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsDetailBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.OrderAttachmentBO;
import com.car.app.carscraporder.pojo.CarScrapOrderAutoparts;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsVO;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 类名称：CarScrapOrderAutopartsService   
 * 类描述：汽车配件服务类   
 * 创建人：刘子亮
 * 创建时间：2018年10月9日 下午4:22:28      
 * @version  V1.0
 *
 */
public interface CarScrapOrderAutopartsService extends BaseService<CarScrapOrderAutoparts>{

	 CarScrapOrderAutoparts queryById(String id);
	
	 CarScrapOrderAutopartsBO  queryBOById(String id)throws Exception;

	 Integer  saveAutoparts(CarScrapOrderAutopartsVO  carScrapOrderAutopartsVO)throws Exception;
	
	 CarScrapOrderAutopartsDetailBO  getOrderAutopartsDetailById(String id)throws Exception;
	
	  List<CarScrapOrderAutopartsBO> queryListByOrderId(String orderid)throws Exception;

	 PageInfo<CarScrapOrderPageBO> queryBOPageListByWhere(Integer page,Integer rows, CarScrapOrderKeywordVO paramter)throws Exception;
	
	/**
	 * 获取待分拣和待入库数据
	 * @param page
	 * @param rows
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	 PageInfo<CarScrapOrderPageBO> getSortingParts(Integer page,Integer rows, CarScrapOrderKeywordVO paramter)throws Exception;
	
	
	 PageInfo<CarScrapOrderPageBO> queryHistoryBOPageList(Integer page,Integer rows, CarScrapOrderKeywordVO paramter)throws Exception;
	/**
	 * 
	 * @param id  配件主键
	 * @param orderStatus 审核状态
	 * @param remark 审核备注
	 * @param operator 审核人
	 * @return
	 * @throws Exception
	 */
	 Integer saveOrderAutopartsAuditingRecord(String id,Integer orderStatus,String remark,String operator,CarScrapOrderAutoparts parts)throws Exception;

	 Integer queryPendingOrder(String agentUserid,Integer partsStauts,List<String> areasids,Boolean isSorting)throws Exception;
	
	 String createQRcode(String partsid)throws Exception;
	
	 Boolean saveSortingParts(String id,Integer sortingStatus, String operator)throws Exception;
	
	 OrderAttachmentBO importFile(MultipartFile file,String id)throws Exception ;

	 Integer selectCount(CarScrapOrderKeywordVO paramter)throws Exception ;
	
	 boolean removeFile(Integer attachmentId, String operator)throws Exception ;
	
	 Integer  updateAutopartsByIdSelective(CarScrapOrderAutoparts record)throws Exception;
	
	 Integer deleteById(String id);
}
