package com.car.app.carscraporder.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.car.app.carscraporder.bo.*;
import com.car.app.carscraporder.pojo.*;
import com.car.app.carscraporder.service.*;
import com.car.app.carscraporder.util.*;
import com.car.app.common.util.SmsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.exception.DataException;
import com.car.app.carscraporder.mapper.CarScrapOrderAutopartsDelMapper;
import com.car.app.carscraporder.mapper.CarScrapOrderMapper;
import com.car.app.carscraporder.mapper.CarScrapOrderMyTradeMapper;
import com.car.app.carscraporder.pojo.CarScrapOrderExample.Criteria;
import com.car.app.carscraporder.systemparameter.CommonSystemParamter;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsConfirmVO;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsQuoteVO;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsVO;
import com.car.app.carscraporder.vo.CarScrapOrderConfirmAmountVO;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.carscraporder.vo.CarScrapOrderPictureVO;
import com.car.app.carscraporder.vo.CarScrapOrderQuoteVO;
import com.car.app.carscraporder.vo.CarScrapOrderVO;
import com.car.app.carscraporder.vo.CarScrapOrderWhereParamterVO;
import com.car.app.common.service.RedisService;
import com.car.app.common.util.DateUtil;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CarScrapOrderServiceImpl extends BaseServiceImpl<CarScrapOrder> implements
		CarScrapOrderService {

	@Autowired
	private ClientService clientService;

	@Autowired
	private CarScrapOrderMapper carScrapOrderMapper;

	
	@Autowired
	private CarScrapOrderAutopartsDelMapper carScrapOrderAutopartsDelMapper;
	
	@Autowired
	private CarScrapOrderAutopartsService carScrapOrderAutopartsService;
	
	@Autowired
	private CarScrapOrderAuditService carScrapOrderAuditService;
	
	
	@Autowired
	private TbCommonContactsService tbCommonContactsService;
	
	@Autowired
	private  TbCommonPayeeaccountService tbCommonPayeeaccountService;

	@Autowired
	private  TbCommonMailinginfoService tbCommonMailinginfoService;
	
	@Autowired
	private CarScrapOrderSettlementService carScrapOrderSettlementService;
	
	
	 @Autowired
	 private CarScrapOrderAttachmentService  carScrapOrderAttachmentService;
	@Autowired
     private UserService userService;

	@Autowired
	CarPushService carPushService;
	
	@Autowired
    private AreasService   areasService;
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CarScrapOrderMyTradeMapper carScrapOrderMyTradeMapper;


	/**
	 * 新增订单
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Integer save(CarScrapOrderVO carScrapOrderVO)throws Exception {
		
		CheckUtil.notNull(carScrapOrderVO, "param.is.null");

		CarScrapOrder record = new CarScrapOrder();
		BeanUtils.copyProperties(carScrapOrderVO, record);
		 //校验订单
		 boolean issuccuss = valid(record);
		 
		if(issuccuss){
			//生产订单id
			String id = UUIDUtil.getUUID();
			
			record.setId(id);
			  //生产订单编号
		    record.setOrderNo(getOrderNo(record.getOrderAreasId(),record.getOrderType()));
			  //订单产生时间
			record.setOrderTime(new Date());
			   //订单初始状态--2019/01/28
			Integer orderStatus = null;
			if(StringUtils.equals(carScrapOrderVO.getClientType(), CommonSystemParamter.CLIENT_ORDINARY_TYPE)) {
				orderStatus = CommonSystemParamter.ORDER_TO_SUB_QUOTED_STATUS;
			}else {
				orderStatus = CommonSystemParamter.ORDER_CREATE_STATUS;				
			}
			record.setOrderStatus(orderStatus);
			record.setIsremove(false);
			
			
			//判断订单类型
			if(StringUtils.equals(carScrapOrderVO.getOrderType(), CommonSystemParamter.ORDER_OLDPARTS_TYPE)){
				//获取配件数据
				List<CarScrapOrderAutopartsVO> carScrapOrderAutopartsList = carScrapOrderVO.getCarScrapOrderAutopartsList();
				if(carScrapOrderAutopartsList!=null&& carScrapOrderAutopartsList.size()>0){
					for(int i=0;i<carScrapOrderAutopartsList.size();i++){
						CarScrapOrderAutopartsVO partsvo = carScrapOrderAutopartsList.get(i);
						partsvo.setCreater(carScrapOrderVO.getCreater());
						partsvo.setOrderId(id);
						partsvo.setQrPic(record.getOrderNo()+"-0"+i);
						partsvo.setClientType(carScrapOrderVO.getClientType());
						carScrapOrderAutopartsService.saveAutoparts(partsvo);
					}
				}
				
			}else{
				//获取整车订单的上传图片
				List<CarScrapOrderPictureVO> pictures = carScrapOrderVO.getPictures();
				if(pictures!=null&& pictures.size()>0){
					for(CarScrapOrderPictureVO picture:pictures){
						if(StringUtils.equals(picture.getPritureCategory(), "1")||StringUtils.equals(picture.getPritureCategory(), "2")){
							if(StringUtils.isNotBlank(picture.getAttachmentId())){
								     CarScrapOrderAttachment attachment = new CarScrapOrderAttachment();
						    		 attachment.setAttachmentId(Integer.parseInt(picture.getAttachmentId()));
						    		 attachment.setOrderId(id);
						    		 attachment.setOrderType(CommonSystemParamter.ORDER_WhOLECAR_TYPE);
						    		 attachment.setAttachmentType(picture.getPritureCategory());
						    		 attachment.setCarPictureType(picture.getPritureType());
						    		 carScrapOrderAttachmentService.insert(attachment);
							}
						}
					}
				}
			}
		    //新增订单审核节点历史记录
			CarScrapOrderAudit auditData = new CarScrapOrderAudit();
			auditData.setOrderId(id);
			auditData.setStauts(orderStatus);
			auditData.setAuditor(carScrapOrderVO.getCreater());
			auditData.setAuditTime(new Date());
			auditData.setRemark("下单");
			auditData.setBusinessType(CommonSystemParamter.BUSINESS_TYPE_ORDER);
			auditData.setCreater(record.getCreater());
			carScrapOrderAuditService.save(auditData);

			//设置常用联系人
			if(carScrapOrderVO.getIsSetUpContacts()==true){
				TbCommonContacts contacts = new TbCommonContacts();
				if(StringUtils.isNotBlank(record.getTakeCarContacts())){
					contacts.setContactName(record.getTakeCarContacts());
					contacts.setContactTelephone(record.getTakeCarContactNumber());
					contacts.setClientid(record.getClientId());
					//首先查询是否有相同数据
					int count = tbCommonContactsService.selectCount(contacts);
					if(count==0)
					    tbCommonContactsService.save(contacts);
				}
			}
		  if(StringUtils.equals(carScrapOrderVO.getOrderType(), CommonSystemParamter.ORDER_WhOLECAR_TYPE)){
			  if(carScrapOrderVO.getIsSetUpMailing()==true){
				  TbCommonMailinginfo mailing = new TbCommonMailinginfo();
					if(StringUtils.isNotBlank(record.getMailingAddressName())){
						mailing.setMailingAddressName(record.getMailingAddressName());
						mailing.setMailingAddressTelephone(record.getMailingAddressTelephone());
						mailing.setProvince(record.getProvince());
						mailing.setCity(record.getCity());
						mailing.setCounty(record.getCounty());
						mailing.setDetailedAddress(record.getDetailedAddress());
						mailing.setClientid(record.getClientId());
						//首先查询是否有相同数据
						int count = tbCommonMailinginfoService.selectCount(mailing);
						if(count==0){
							mailing.setCreater(record.getCreater());
							tbCommonMailinginfoService.save(mailing);
						}
					}
			  }
             if(carScrapOrderVO.getIsSetUpPayee()==true){
            	 TbCommonPayeeaccount payee = new TbCommonPayeeaccount();
            	 if(StringUtils.isNotBlank(record.getPayeeName())){
            		 payee.setPayeeName(record.getPayeeName());
            		 payee.setOpeningBank(record.getOpeningBank());
            		 payee.setBankAccount(record.getBankAccount());
            		 payee.setClientid(record.getClientId());
            		//首先查询是否有相同数据
				   int count = tbCommonPayeeaccountService.selectCount(payee);
				  if(count==0){
					  payee.setCreater(record.getCreater());
					  tbCommonPayeeaccountService.save(payee);
				   }
            	 }
			  }
			}
		}else{
			throw new DataException("订单数据异常");
		}
		return super.save(record);
	}
	
	
	private boolean valid(CarScrapOrder carScrapOrder){
		if(carScrapOrder.getOrderAreasId()==null){
			carScrapOrder.setOrderAreasId(2);//默认北京区域
		}
		
		//TODO 订单数据校验
		CheckUtil.notNull(carScrapOrder, "param.is.null");
		//CheckUtil.notNull(carScrapOrder.getOrderAreasId(), "param.is.null");
		CheckUtil.notNull(carScrapOrder.getOrderType(), "param.is.null");
		CheckUtil.check((0<Integer.parseInt(carScrapOrder.getOrderType())), "param.is.null");
		CheckUtil.check((Integer.parseInt(carScrapOrder.getOrderType())<3), "param.is.null");
		return true;
	}

	/**
	 * 订单修改
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public Integer update(CarScrapOrderVO record) throws Exception{
		CarScrapOrder order = this.queryById(record.getId());
		record.setCreater(order.getCreater());
		BeanUtils.copyProperties(record, order);
		
		return super.update(order);
	}
	
	
	public CarScrapOrder queryById(String id){
		return carScrapOrderMapper.selectByPrimaryKey(id);
	}
	
	
	/**
	 * 根据订单id获取订单数据
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public CarScrapOrderBO queryBOById(String id) throws Exception{
		
		//查询订单信息
		CarScrapOrderBO bo = carScrapOrderMapper.queryBOById(id);
		
		if(bo!=null){
			//判断订单类型
			if(StringUtils.equals(bo.getOrderType(), CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS)){
				List<CarScrapOrderAutopartsBO>   autopartsBoList = carScrapOrderAutopartsService.queryListByOrderId(id);
				bo.setCarScrapOrderAutopartsList(autopartsBoList);
			}else{
				 fillPrictureToOrder(bo);
//				//查询整车图片返回的结构[{orderType:1,storagePaths:"201810251625111/fdsfd3eredsfd233fse.png,201810251625111/eefddsterdredsfd233fse.png"}]
//				List<CarScrapOrderAttachmentBO> attachmentList = carScrapOrderAttachmentService.getArrachmentByOrderByid(id);
//				if(attachmentList!=null&&attachmentList.size()>0){
//					for(CarScrapOrderAttachmentBO attachment:attachmentList){
//						if(StringUtils.isBlank(attachment.getAttachmentType())) break;
//						List<String> attachement = new ArrayList<String>();
//						 if(attachment.getUrl()!=null){
//							 if(StringUtils.indexOf(attachment.getUrl(), ",")>0){
//								 
//								 String[] paths = StringUtils.split(attachment.getUrl(), ",");
//								 attachement = Arrays.asList(paths);
//							 }else{
//								 attachement.add(attachment.getUrl());
//							 }
//							 fillPrictureToOrder(bo,attachment.getAttachmentType(),attachement);
//						 }
//					}
//					
//				}
//				
//			}	
	}
		
		//根据订单id查询审核历史记录
		Example example = new Example(CarScrapOrderAudit.class);
		example.setOrderByClause("auditTime");
		example.createCriteria().andEqualTo("orderId", id).andEqualTo("businessType", "1");
		List<CarScrapOrderAudit> carScrapOrderAuditList = carScrapOrderAuditService.queryListByExample(example);
		for(CarScrapOrderAudit audit :carScrapOrderAuditList){
			if(CommonSystemParamter.ORDER_CREATE_STATUS==audit.getStauts() ){
				//下单日期
				bo.setCreateOrderDate(audit.getAuditTime());
			}else if(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS == audit.getStauts()){
				//派单日期
				bo.setPiesNote(audit.getRemark());
			}else if(CommonSystemParamter.ORDER_RECEIVE_STATUS == audit.getStauts()){
				//接收日期
				bo.setReceiveCarDate(audit.getAuditTime());
			}else if(CommonSystemParamter.ORDER_STORAGE_STATUS == audit.getStauts()){
				//入库日期
				bo.setInStorageDate(audit.getAuditTime());
			}else if(CommonSystemParamter.ORDER_SCRAP_STATUS  == audit.getStauts()){
				//报废日期
				bo.setFormalitiesDate(audit.getAuditTime());
			}else if(CommonSystemParamter.ORDER_SETTLEMENT_STATUS == audit.getStauts()){
				//结算日期
				bo.setSettlementDate(audit.getAuditTime());
			} 
		}

		 List<CarScrapOrderSettlementBO> settlementList = carScrapOrderSettlementService.getCarScrapOrderSettlementByOrderId(id);
		 bo.setSettlementList(settlementList);
		}
		return bo;
	}





	
	public OrderAduitBO getOrderAuditInfo(String orderId) throws Exception{
		OrderAduitBO bo = null;
		//查询订单信息 
		CarScrapOrder order = carScrapOrderMapper.selectByPrimaryKey(orderId);
		if(order==null){
			return bo;
		}else{
			bo = new OrderAduitBO();
			BeanUtils.copyProperties(order, bo);
			//查询订单配件数
			CarScrapOrderAutoparts whereParam = new CarScrapOrderAutoparts();
			whereParam.setOrderId(orderId);
			whereParam.setIsValid(true);
			Integer count = carScrapOrderAutopartsService.selectCount(whereParam);
		    bo.setPartsCount(count);
		    
		    
			//查询审核进度
		    Map<Integer,CarScrapOrderAuditBO>  auditMap = new HashMap<Integer,CarScrapOrderAuditBO>();
			Example example = new Example(CarScrapOrderAudit.class);
			example.setOrderByClause("auditTime");
			example.createCriteria().andEqualTo("orderId", orderId).andEqualTo("businessType", "1");
			List<CarScrapOrderAudit> carScrapOrderAuditList = carScrapOrderAuditService.queryListByExample(example);
			for(CarScrapOrderAudit audit :carScrapOrderAuditList){
				if(CommonSystemParamter.ORDER_RECEIVE_STATUS == audit.getStauts()){
					//接收日期
//					CarScrapOrderAuditBO auditBo = new CarScrapOrderAuditBO();
//					BeanUtils.copyProperties(audit, auditBo);
//					//查询审核人姓名
//					User user = userService.queryById(audit.getAuditor());
//					if(user!=null){
//						auditBo.setAuditorName(user.getRealName());
//					}
//					auditMap.put(audit.getStauts(), auditBo);
					
					bo.setReceiveOrderTime(audit.getAuditTime());
					User user = userService.queryById(audit.getAuditor());
					bo.setRecipient(user.getRealName());
					
				}else if(CommonSystemParamter.ORDER_SETTLEMENT_STATUS == audit.getStauts()){
					//结算日期
//					CarScrapOrderAuditBO auditBo = new CarScrapOrderAuditBO();
//					BeanUtils.copyProperties(audit, auditBo);
//					auditMap.put(audit.getStauts(), auditBo);
					bo.setSettlementTime(audit.getAuditTime());
				} else if(CommonSystemParamter.ORDER_END_STATUS == audit.getStauts()){
					
//					CarScrapOrderAuditBO auditBo = new CarScrapOrderAuditBO();
//					BeanUtils.copyProperties(audit, auditBo);
//					auditMap.put(audit.getStauts(), auditBo);
					bo.setEndTime(audit.getAuditTime());
				}
			}
			
			
			
			
			return bo;
			
		}
		
		
	}
	
	
	
	private void fillPrictureToOrder(CarScrapOrderBO bo){

		 List<OrderAttachmentBO> attachmentBo1 = carScrapOrderAttachmentService.getArrachmentByOrderIdAndAttachmentType(bo.getId(),CommonSystemParamter.ORDER_PICTURE_CAR,CommonSystemParamter.ORDER_WhOLECAR_TYPE);
		 bo.setVehiclePictures(attachmentBo1);
			
		 List<OrderAttachmentBO> attachmentBo2 = carScrapOrderAttachmentService.getArrachmentByOrderIdAndAttachmentType(bo.getId(),CommonSystemParamter.ORDER_PICTURE_CAR_FORMALITIES,CommonSystemParamter.ORDER_WhOLECAR_TYPE);
		 bo.setFormalitiesPictures(attachmentBo2);

		  List<OrderAttachmentBO> attachmentBo3 = carScrapOrderAttachmentService.getArrachmentByOrderIdAndAttachmentType(bo.getId(),CommonSystemParamter.ORDER_PICTURE_CAR_DESTROY,CommonSystemParamter.ORDER_WhOLECAR_TYPE);
		  bo.setDestroyPictures(attachmentBo3);

		  List<OrderAttachmentBO> attachmentBo4 = carScrapOrderAttachmentService.getArrachmentByOrderIdAndAttachmentType(bo.getId(),CommonSystemParamter.ORDER_PICTURE_CAR_CERTIFICATE,CommonSystemParamter.ORDER_WhOLECAR_TYPE);
		  bo.setCertificatePictures(attachmentBo4);

		 List<OrderAttachmentBO> attachmentBo5 = carScrapOrderAttachmentService.getArrachmentByOrderIdAndAttachmentType(bo.getId(),CommonSystemParamter.ORDER_PICTURE_CAR_SINCE,CommonSystemParamter.ORDER_WhOLECAR_TYPE);
		 bo.setSincepricePictures(attachmentBo5);
		
		
		
//		if(StringUtils.equals(attachmentType, CommonSystemParamter.ORDER_PICTURE_CAR)){
//			
//			bo.setVehiclePictures(pictures);
//		}else if(StringUtils.equals(attachmentType, CommonSystemParamter.ORDER_PICTURE_CAR_FORMALITIES)){
//			
//			bo.setFormalitiesPictures(pictures);
//		}else if(StringUtils.equals(attachmentType, CommonSystemParamter.ORDER_PICTURE_CAR_DESTROY)){
//			bo.setDestroyPictures(pictures);
//		}else if(StringUtils.equals(attachmentType, CommonSystemParamter.ORDER_PICTURE_CAR_CERTIFICATE)){
//			bo.setCertificatePictures(pictures);
//		}
		
		
		
	}
	
	
	/**
	 * 删除订单
	 * 状态删除:保存操作人、操作时间
	 */
      public int deleteByPrimaryKey(String id,String operator){
    	  CarScrapOrder carScrapOrder = new CarScrapOrder();
    	  carScrapOrder.setOperatortime(new Date());
    	  carScrapOrder.setOperator(operator);
    	  carScrapOrder.setId(id);
    	  carScrapOrder.setIsremove(true);
    	  //TODO  删除旧件订单时级联删除配件数据
		return carScrapOrderMapper.updateByPrimaryKeySelective(carScrapOrder);
		
	}


	public Integer saveOrderAuditingRecord(String id,Integer orderStatus,String remark,String operator,CarScrapOrder orderParam) throws Exception{
		{

			CarScrapOrder order = this.queryById(id);
			System.out.println("+++++"+order);
			if(order!=null){
				//CheckUtil.check(orderStatus>=order.getOrderStatus(), "param.is.null");
				if(orderParam!=null){
					BeanUtils.copyProperties(orderParam, order);
				}

				order.setOperator(operator);

				//判断订单类型
				if(StringUtils.equals(order.getOrderType(), CommonSystemParamter.ORDER_WhOLECAR_TYPE)){

					if(CommonSystemParamter.PARTS_CREATE_STATUS == orderStatus){
						//整车订单初始化
						saveauditHistory(order.getId(),CommonSystemParamter.PARTS_CREATE_STATUS,"下单",CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					}else if(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS == orderStatus){
						//整车订单接单派单
						saveauditHistory(order.getId(),CommonSystemParamter.ORDER_DISTRIBUTION_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
						order.setOrderStatus(orderStatus);
						//保存订单信息
						int result = super.updateSelective(order);
					}else if(CommonSystemParamter.ORDER_RECEIVE_STATUS == orderStatus){
						//整车订单接收车辆
						saveauditHistory(order.getId(),CommonSystemParamter.ORDER_RECEIVE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
						order.setOrderStatus(orderStatus);
						//保存订单信息
						int result = super.updateSelective(order);
					}else if(CommonSystemParamter.ORDER_STORAGE_STATUS == orderStatus){
						//整车订单入场
						saveauditHistory(order.getId(),CommonSystemParamter.ORDER_STORAGE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
						order.setOrderStatus(orderStatus);
						//保存订单信息
						super.updateSelective(order);
					}else if(CommonSystemParamter.ORDER_SCRAP_STATUS == orderStatus){
						//整车订单报废
						saveauditHistory(order.getId(),CommonSystemParamter.ORDER_SCRAP_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
						order.setOrderStatus(orderStatus);
						//保存订单信息
						super.updateSelective(order);
					}else if(CommonSystemParamter.ORDER_SETTLEMENT_STATUS == orderStatus){
						//整车订单结算
						saveauditHistory(order.getId(),CommonSystemParamter.ORDER_SETTLEMENT_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
						order.setOrderStatus(orderStatus);
						//保存订单信息
						super.updateSelective(order);
					}else if(CommonSystemParamter.ORDER_END_STATUS == orderStatus){
						//整车订单结束
						saveauditHistory(order.getId(),CommonSystemParamter.ORDER_END_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
						order.setOrderStatus(orderStatus);
						//保存订单信息
						super.updateSelective(order);
					}else if(CommonSystemParamter.ORDER_ERROR_STATUS == orderStatus){
						//整车订单异常
						saveauditHistory(order.getId(),CommonSystemParamter.ORDER_ERROR_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
						order.setOrderStatus(orderStatus);
						//保存订单信息
						super.update(order);
					}
					/**
					 * 旧件订单比较特殊，
					 *1.下单                                                  下单以订单维度操作，订单下的配件订单状态和订单状态一致
					 *2：接单派单                                     接单派单以订单维度操作，订单下的配件订单状态和订单状态一致
					 *3:业务员接收车辆                        业务员接收车辆以配件订单维度操作，订单状态根据最后操作的配件订单状态逻辑变更，这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
					 *4:无法接收                                      无法接收车辆以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
					 *5：入库                                             无法接收车辆以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
					 *6：出库                                            车辆出库 以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
					 *7：结算                                            车辆结算 以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
					 *8:结束                                              订单接收 以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
					 *9：异常                                           订单异常 以订单维度操作，订单下的配件订单状态和订单状态一致
					 */

				}else if(StringUtils.equals(order.getOrderType(), CommonSystemParamter.ORDER_OLDPARTS_TYPE)){
					if(CommonSystemParamter.PARTS_CREATE_STATUS == orderStatus){
						//旧件订单初始化
						saveauditHistory(order.getId(),CommonSystemParamter.PARTS_CREATE_STATUS,"下单",CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);

					}else if(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS == orderStatus){
						//旧件订单接单派单
						//配件订单新增历史
						List<CarScrapOrderAutoparts> partsList = getCarScrapOrderAutopartsByOrderId(id);
						for(CarScrapOrderAutoparts parts:partsList){
							saveauditHistory(parts.getId(),CommonSystemParamter.PARTS_RECEIPT_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
							//更新配件订单状态
							parts.setOrderAutopartsStatus(CommonSystemParamter.PARTS_RECEIPT_STATUS);
							parts.setOperator(operator);
							carScrapOrderAutopartsService.updateSelective(parts);
						}
						//保存订单业务
						saveauditHistory(order.getId(),CommonSystemParamter.ORDER_DISTRIBUTION_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
						order.setOrderStatus(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS);
						super.updateSelective(order);
					}else if(CommonSystemParamter.ORDER_RECEIVE_STATUS == orderStatus){
						//旧件订单接收
						saveauditHistory(order.getId(),CommonSystemParamter.ORDER_RECEIVE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);

					}else if(CommonSystemParamter.PARTS_NO_RECEIVE_STATUS == orderStatus){
						//旧件订单无法入库
						saveauditHistory(order.getId(),CommonSystemParamter.PARTS_NO_RECEIVE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);

					}else if(CommonSystemParamter.PARTS_STORAGE_STATUS == orderStatus){
						//旧件订单入库
						saveauditHistory(order.getId(),CommonSystemParamter.PARTS_STORAGE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);

					}

					else if(CommonSystemParamter.PARTS_OUT_STORAGE_STATUS == orderStatus){
						//旧件订单出库
						saveauditHistory(order.getId(),CommonSystemParamter.PARTS_OUT_STORAGE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);

					}else if(CommonSystemParamter.PARTS_SETTLEMENT_STATUS == orderStatus){
						//旧件订单结算
						saveauditHistory(order.getId(),CommonSystemParamter.PARTS_SETTLEMENT_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);

					}else if(CommonSystemParamter.PARTS_END_STATUS == orderStatus){
						//旧件订单结束
						saveauditHistory(order.getId(),CommonSystemParamter.PARTS_END_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);

					}else if(CommonSystemParamter.PARTS_ERROR_STATUS == orderStatus){
						List<CarScrapOrderAutoparts> partsList = getCarScrapOrderAutopartsByOrderId(id);
						for(CarScrapOrderAutoparts parts:partsList){
							saveauditHistory(parts.getId(),CommonSystemParamter.PARTS_ERROR_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
							//更新配件订单状态
							parts.setOrderAutopartsStatus(CommonSystemParamter.PARTS_ERROR_STATUS);
							parts.setOperator(operator);
							carScrapOrderAutopartsService.updateSelective(parts);
						}
						//保存订单业务
						saveauditHistory(order.getId(),CommonSystemParamter.PARTS_ERROR_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
						order.setOrderStatus(CommonSystemParamter.PARTS_ERROR_STATUS);
						super.updateSelective(order);
					}
				}
			}
			return 1;
		}
	}



	/**
	 *
	 * @param id
	 * @param orderStatus 要更新的订单状态
	 * @param remark 备注
	 * @param operator 操作人
	 * @param orderParam 订单类对象
	 * @param username 业务员姓名
	 * @param userId 业务员Id
	 * @return
	 * @throws Exception
	 */
	public Integer saveOrderAuditingRecord(String id,Integer orderStatus,String remark,String operator,CarScrapOrder orderParam,String username,String userId,String isClient) throws Exception{

		System.out.println("service层_operator:"+operator);

		CarScrapOrder order = this.queryById(id);
        if(order!=null){
        	//CheckUtil.check(orderStatus>=order.getOrderStatus(), "param.is.null");
        	if(orderParam!=null){
    			 BeanUtils.copyProperties(orderParam, order);
    		 }

	    		
	  		  //判断订单类型
	  		if(StringUtils.equals(order.getOrderType(), CommonSystemParamter.ORDER_WhOLECAR_TYPE)){
	  			   
	  			  if(CommonSystemParamter.PARTS_CREATE_STATUS == orderStatus){
	  				   //整车订单初始化
	  				  saveauditHistory(order.getId(),CommonSystemParamter.PARTS_CREATE_STATUS,"下单",CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
	  			  }else if(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS == orderStatus && orderParam.getSinceQuote()!=null && isClient==null){  //更改订单状态为2,待用户接收【总部已审核】【用户待接收】
					  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_DISTRIBUTION_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
					  order.setIsAgree(2); //用户端待接受二次报价
					  order.setOrderStatus(orderStatus);
					  order.setSinceQuote(orderParam.getSinceQuote());
					  order.setClientId(orderParam.getClientId());
					  super.updateSelective(order);
				  }else if(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS == orderStatus && isClient!=null){   //用户端用户接受总部二次报价
					  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_DISTRIBUTION_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
//					  order.setOperator(operator);
					  order.setIsAgree(1); //用户端已接受二次报价
					  order.setOrderStatus(orderStatus);
					  super.updateSelective(order);
				  } else if(CommonSystemParamter.ORDER_NOOK_STATUS == orderStatus){ //派单操作-传递33,点击派单按钮进入此操作。将2变更33
					  //整车订单接单
					  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_NOOK_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOrderStatus(orderStatus);
					  super.updateSelective(order);
				  }else if(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS == orderStatus){
	  				  //整车订单派单
	  				  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_DISTRIBUTION_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
	  				  order.setOperator(operator);
					  order.setOperatorName("");
	  				  order.setIsAgree(3); //订单未参与过二次报价
					  order.setOrderStatus(orderStatus);
	  				  int result = super.updateSelective(order);
					  List<CarPush> carPushList = carPushService.getAllCarPush();
					  CarPush carPush = carPushList.get(4);

	  				  if(result>0){
//						  JPushUtil.sendToRegistrationId(userId,"Hi,业务员-"+(null==username?"未知用户":username)+",该你去取车啦!","车互联-派单消息","Hi,"+(null==username?"未知用户":username)+",领导派你去取车啦!","1");
						  JYyPushUtil.sendToRegistrationId(userId,username+"，"+carPush.getNotificationTitle(),carPush.getMsgTitle(),carPush.getMsgContent(),"1");

						  //派单给业务员后推送通知到用户手机,通知用户我方业务员已经出发
						  //JPushUtil.sendToRegistrationId(order.getClientId(),carPush.getNotificationTitle(),carPush.getMsgTitle(),carPush.getMsgContent(),"5");

						  //根据客户端id查询手机号
						  //ClientBO cb = clientService.getClientById(order.getClientId());

						  //发送短信到用户手机，提醒用户带好行驶本等物品
//						  System.out.println("发送短信的手机号:"+clientPhone);
//						  System.out.println("发送短信的模板格式:"+SmsSender.formatmsg);
//						  System.out.println("发送短信的内容:"+order_no);
//
//						  //短信通知用户
//				  		  SmsSender.sendSmsByOrder(cb.getPhone(), SmsSender.formatmsg, SmsSender.PUBLICEORDER,order.getOrderNo());
	  				  }
	  			  }else if(CommonSystemParamter.ORDER_RECEIVE_STATUS == orderStatus){
	  				  //整车订单接收
	  				  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_RECEIVE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
	  				  order.setOrderStatus(orderStatus);
	  				  int result = super.updateSelective(order);
					  List<CarPush> carPushList = carPushService.getAllCarPush();
	  				  CarPush carPush = carPushList.get(3);
	  				  if(result>0){
						 JYyPushUtil.sendToRegistrationId(userId,carPush.getNotificationTitle(),carPush.getMsgTitle(),carPush.getMsgContent(),"1");
					  }
	  			  }else if(CommonSystemParamter.ORDER_RE_FUSE == orderStatus){  //更改订单状态为96,二次报价单【业务员提交审核,总部待审核】
					  //整车订单二次报价车辆
					  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_RE_FUSE,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
					  order.setOrderStatus(orderStatus);
					  order.setAdjustWhy(orderParam.getAdjustWhy()); //将二次报价原因赋值
					  order.setSinceQuote(orderParam.getSinceQuote()); //将二次报价金额赋值
					  //保存订单信息
					  super.updateSelective(order);
				  }else if(CommonSystemParamter.ORDER_CANCEL_STATUS == orderStatus){ //更改订单状态为-1，用户不接受
					  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_CANCEL_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
					  order.setOrderStatus(orderStatus);
					  super.updateSelective(order);
				  }else if(CommonSystemParamter.ORDER_STORAGE_STATUS == orderStatus){
	  				  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_STORAGE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
	  				  order.setOrderStatus(orderStatus);
	  				  super.updateSelective(order);
	  			  }else if(CommonSystemParamter.ORDER_SCRAP_STATUS == orderStatus){
	  			  	  String kdId = orderParam.getExpressCompany(); //快递公司编码
					  String kdNum = orderParam.getExpressNumber(); //快递单号
	  				  //整车订单报废
	  				  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_SCRAP_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
					  order.setExpressCompany(kdId);
					  order.setExpressNumber(kdNum);
	  				  order.setOrderStatus(orderStatus);
	  				  super.updateSelective(order);
	  			  }else if(CommonSystemParamter.ORDER_SETTLEMENT_STATUS == orderStatus){
	  				  //整车订单结算
	  				  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_SETTLEMENT_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
	  				  order.setOrderStatus(orderStatus);
	  				  super.updateSelective(order);
	  			  }else if(CommonSystemParamter.ORDER_END_STATUS == orderStatus){
	  				  //整车订单结束
	  				  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_END_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
	  				  order.setOrderStatus(orderStatus);
	  				  super.updateSelective(order);
	  			  }else if(CommonSystemParamter.ORDER_ERROR_STATUS == orderStatus){
	  				  //整车订单异常
	  				  saveauditHistory(order.getId(),CommonSystemParamter.ORDER_ERROR_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
					  order.setOperator(operator);
	  				  order.setOrderStatus(orderStatus);
	  				  super.update(order);
	  			  }
	  		    /**
	  		     * 旧件订单比较特殊，	
	  		     *1.下单                                                  下单以订单维度操作，订单下的配件订单状态和订单状态一致
	  		     *2：接单派单                                     接单派单以订单维度操作，订单下的配件订单状态和订单状态一致
	  		     *3:业务员接收车辆                        业务员接收车辆以配件订单维度操作，订单状态根据最后操作的配件订单状态逻辑变更，这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
	  		     *4:无法接收                                      无法接收车辆以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
	  		     *5：入库                                             无法接收车辆以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
	  		     *6：出库                                            车辆出库 以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
	  		     *7：结算                                            车辆结算 以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
	  		     *8:结束                                              订单接收 以配件订单维度操作，订单状态跟配件订单状态最小的状态一致（无法接收排除），这里的代码忽略参考 CarScrapOrderAutopartsServiceImpl.saveOrderAutopartsAuditingRecord
	  		     *9：异常                                           订单异常 以订单维度操作，订单下的配件订单状态和订单状态一致
	  		     */

	  		}else if(StringUtils.equals(order.getOrderType(), CommonSystemParamter.ORDER_OLDPARTS_TYPE)){
				order.setOperator(operator);
	  			if(CommonSystemParamter.PARTS_CREATE_STATUS == orderStatus){
	  				//旧件订单初始化
	  				saveauditHistory(order.getId(),CommonSystemParamter.PARTS_CREATE_STATUS,"下单",CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
	  				
	  			  }else if(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS == orderStatus){
					System.out.println("系统向你派出了旧件单!");
	  				//旧件订单接单派单
	  				    //配件订单新增历史
						List<CarPush> carPushList = carPushService.getAllCarPush();
						CarPush carPush = carPushList.get(5);
	  					List<CarScrapOrderAutoparts> partsList = getCarScrapOrderAutopartsByOrderId(id);
	  					for(CarScrapOrderAutoparts parts:partsList){
	  						saveauditHistory(parts.getId(),CommonSystemParamter.PARTS_RECEIPT_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
	  						//更新配件订单状态
	  						parts.setOrderAutopartsStatus(CommonSystemParamter.PARTS_RECEIPT_STATUS);
	  						parts.setOperator(operator);
	  						carScrapOrderAutopartsService.updateSelective(parts);
	  					}
	  					//保存订单业务
	  					saveauditHistory(order.getId(),CommonSystemParamter.ORDER_DISTRIBUTION_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
	  					order.setOrderStatus(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS);
	  					int result = super.updateSelective(order);
	  					if(result>0){
							JYyPushUtil.sendToRegistrationId(userId,username+"，"+carPush.getNotificationTitle(),carPush.getMsgTitle(),carPush.getMsgContent(),"1");
						}
	  			  }else if(CommonSystemParamter.ORDER_RECEIVE_STATUS == orderStatus){
	  				    //旧件订单接收
	  					saveauditHistory(order.getId(),CommonSystemParamter.ORDER_RECEIVE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
	  					
	  			  }else if(CommonSystemParamter.PARTS_NO_RECEIVE_STATUS == orderStatus){
	  				//旧件订单无法入库
	  				  saveauditHistory(order.getId(),CommonSystemParamter.PARTS_NO_RECEIVE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
	  					
	  			  }else if(CommonSystemParamter.PARTS_STORAGE_STATUS == orderStatus){
	  				  //旧件订单入库
	  				  saveauditHistory(order.getId(),CommonSystemParamter.PARTS_STORAGE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
	  					
	  			  }
	  			  
	  			  else if(CommonSystemParamter.PARTS_OUT_STORAGE_STATUS == orderStatus){
	  				//旧件订单出库
	  				  saveauditHistory(order.getId(),CommonSystemParamter.PARTS_OUT_STORAGE_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
	  					
	  			  }else if(CommonSystemParamter.PARTS_SETTLEMENT_STATUS == orderStatus){
	  				//旧件订单结算
	  				  saveauditHistory(order.getId(),CommonSystemParamter.PARTS_SETTLEMENT_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
	  					
	  			  }else if(CommonSystemParamter.PARTS_END_STATUS == orderStatus){
	  				  //旧件订单结束
	  				  saveauditHistory(order.getId(),CommonSystemParamter.PARTS_END_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
	  					
	  			  }else if(CommonSystemParamter.PARTS_ERROR_STATUS == orderStatus){ 
	  				List<CarScrapOrderAutoparts> partsList = getCarScrapOrderAutopartsByOrderId(id);
  					for(CarScrapOrderAutoparts parts:partsList){
  						saveauditHistory(parts.getId(),CommonSystemParamter.PARTS_ERROR_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS,operator);
  						//更新配件订单状态
  						parts.setOrderAutopartsStatus(CommonSystemParamter.PARTS_ERROR_STATUS);
  						parts.setOperator(operator);
  						carScrapOrderAutopartsService.updateSelective(parts);
  					}
  					//保存订单业务
  					saveauditHistory(order.getId(),CommonSystemParamter.PARTS_ERROR_STATUS,remark,CommonSystemParamter.BUSINESS_TYPE_ORDER,operator);
  					order.setOrderStatus(CommonSystemParamter.PARTS_ERROR_STATUS);
  					super.updateSelective(order);
	  			  }
	  		   }
         }
		return 1;
	}

	private List<CarScrapOrderAutoparts> getCarScrapOrderAutopartsByOrderId(String orderid) throws Exception{
		CarScrapOrderAutoparts whereparam = new CarScrapOrderAutoparts();
		whereparam.setOrderId(orderid);
		whereparam.setIsValid(true);
		return carScrapOrderAutopartsService.queryListByWhere(whereparam);
	}
	
	/**
	 * 
	 * @param orderid  订单id或配件订单id
	 * @param orderStatus 订单审核状态
	 * @param auditRemark 订单审核备注
	 * @param orderType 订单类型  1订单  2配件订单
	 * @param operator 操作人
	 * @throws Exception
	 */
	private void saveauditHistory(String orderid,Integer orderStatus,String auditRemark,String orderType, String operator) throws Exception{
		
		//新增审核历史记录
		CarScrapOrderAudit auditData = new CarScrapOrderAudit();
		auditData.setOrderId(orderid);
		auditData.setStauts(orderStatus);
		auditData.setAuditor(operator);
		auditData.setAuditTime(new Date());
		auditData.setRemark(auditRemark);
		auditData.setBusinessType(orderType);
		auditData.setCreater(operator);
		carScrapOrderAuditService.save(auditData);
		
	}
	
	
	
	

	
	/**
	 * 生成订单编号
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private String getOrderNo(Integer areaid,String orderType) throws  Exception{
		StringBuffer sbuff = new StringBuffer();
		if(StringUtils.equals(orderType, CommonSystemParamter.BUSINESS_TYPE_AUTOPARTS)){
			sbuff.append("P");
		}else{
			sbuff.append("A");
		}
		
		Areas areas = areasService.queryById(areaid);
		if(areas == null){
			throw new DataException("区域数据不存在");
		}
		
		sbuff.append(areas.getAreasCode());
		
		String dateStr = DateUtil.getFormatDate();
		sbuff.append(dateStr);
		//每天从1开始的编码
		
		String num = redisService.get(dateStr);
		Long serialNumber = null;
		if(StringUtils.isNotBlank(num)){
			serialNumber = redisService.incr(dateStr);
		}else{
			redisService.set(dateStr, "0",86400);//一天有效
			serialNumber=redisService.incr(dateStr);
		}
		
		
		
		sbuff.append(formatserialNumber(serialNumber));
		return sbuff.toString();
		
	}
	
	public String formatserialNumber(Long serialNumber){
		StringBuffer sbuff = new StringBuffer();
		if(serialNumber<10){
			sbuff.append("000").append(serialNumber);
		}else if(serialNumber<100){
			sbuff.append("00").append(serialNumber);
		}else if(serialNumber<1000){
			sbuff.append("0").append(serialNumber);
		}else{
			sbuff.append(serialNumber);
		}
		
		return sbuff.toString();
		
	}

	/**
	 * 获取订单列表
	 * @param page
	 * @param rows
	 * @param
	 * @return
	 */
	@Override
	public PageInfo<CarScrapOrderPageBO>  queryPageListByWhere(Integer page,Integer rows,CarScrapOrderWhereParamterVO paramter) {
		PageHelper.startPage(page, rows);
		CarScrapOrderExample example = new CarScrapOrderExample();
		Criteria  criteria = example.createCriteria();
		
		if(paramter.getOrderStatus()!=null){
		  criteria.andOrderStatusEqualTo(paramter.getOrderStatus());
		}
		
		if(paramter.getCarFrameNumber()!=null){
			criteria.andCarFrameNumberEqualTo(paramter.getCarFrameNumber());
		}
		
		if(paramter.getOrderNo()!=null){
			criteria.andOrderNoEqualTo(paramter.getOrderNo());
		}
		
		if(paramter.getCarNumber()!=null){
			criteria.andCarNumberEqualTo(paramter.getCarNumber());
		}
		
		if(paramter.getTakeCarContacts()!=null){
			criteria.andTakeCarContactsEqualTo(paramter.getTakeCarContacts());
		}
		if(paramter.getTakeCarContactNumber()!=null){
			criteria.andTakeCarContactNumberEqualTo(paramter.getTakeCarContactNumber());
		}
		
		if(paramter.getClientId()!=null){
			criteria.andClientIdEqualTo(paramter.getClientId());
		}
		if(paramter.getAgentUserid()!=null){
			criteria.andAgentUseridEqualTo(paramter.getAgentUserid());
		}
		List<CarScrapOrderPageBO> data = carScrapOrderMapper.queryPageListByWhere(example);
		return new PageInfo<CarScrapOrderPageBO>(data);
	}


	/**
	 * 查询已报价未派单_APP内
	 * auditer : ningrz
	 * @param page
	 * @param rows
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	public PageInfo<CarScrapOrderPageBO>  queryPageListByKeywordByChl(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception{
		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");

					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}
			if(StringUtils.isNotBlank(paramter.getOrderStatus())){
				if(StringUtils.indexOf(paramter.getOrderStatus(), ",")>0){
					String[] arrayOrderStatus = StringUtils.split(paramter.getOrderStatus(), ",");
					paramter.setOrderStatusList(Arrays.asList(arrayOrderStatus));
				}else{
					paramter.setOrderStatusList(new ArrayList());
					paramter.getOrderStatusList().add(paramter.getOrderStatus());
				}
			}
			 PageHelper.startPage(page, rows);
			 List<CarScrapOrderPageBO> data  = carScrapOrderMapper.queryPageListByKeywordByChl(paramter);
			 return new PageInfo<CarScrapOrderPageBO>(data);
		}
		return null;
	}

	@Override
	public PageInfo<CarScrapOrderPageBO> queryPageListByKeywordByExamine(Integer page, Integer rows, CarScrapOrderKeywordVO paramter) throws Exception {
		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");

					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}
			if(StringUtils.isNotBlank(paramter.getOrderStatus())){
				if(StringUtils.indexOf(paramter.getOrderStatus(), ",")>0){
					String[] arrayOrderStatus = StringUtils.split(paramter.getOrderStatus(), ",");
					paramter.setOrderStatusList(Arrays.asList(arrayOrderStatus));
				}else{
					paramter.setOrderStatusList(new ArrayList());
					paramter.getOrderStatusList().add(paramter.getOrderStatus());
				}
			}
			PageHelper.startPage(page, rows);
			List<CarScrapOrderPageBO> data  = carScrapOrderMapper.queryPageListByKeywordByExamine(paramter);
			return new PageInfo<CarScrapOrderPageBO>(data);
		}
		return null;
	}

	@Override
	public PageInfo<CarScrapOrderPageBO> queryPageListByKeywordByCancel(Integer page, Integer rows, CarScrapOrderKeywordVO paramter) throws Exception {
		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");

					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}
			if(StringUtils.isNotBlank(paramter.getOrderStatus())){
				if(StringUtils.indexOf(paramter.getOrderStatus(), ",")>0){
					String[] arrayOrderStatus = StringUtils.split(paramter.getOrderStatus(), ",");
					paramter.setOrderStatusList(Arrays.asList(arrayOrderStatus));
				}else{
					paramter.setOrderStatusList(new ArrayList());
					paramter.getOrderStatusList().add(paramter.getOrderStatus());
				}
			}
			PageHelper.startPage(page, rows);
			List<CarScrapOrderPageBO> data  = carScrapOrderMapper.queryPageListByKeywordByCancel(paramter);
			return new PageInfo<CarScrapOrderPageBO>(data);
		}
		return null;
	}

	@Override
	public PageInfo<CarScrapOrderPageBO> queryPageListByKeywordByExamineOK(Integer page, Integer rows, CarScrapOrderKeywordVO paramter) throws Exception {
		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");

					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}
			if(StringUtils.isNotBlank(paramter.getOrderStatus())){
				if(StringUtils.indexOf(paramter.getOrderStatus(), ",")>0){
					String[] arrayOrderStatus = StringUtils.split(paramter.getOrderStatus(), ",");
					paramter.setOrderStatusList(Arrays.asList(arrayOrderStatus));
				}else{
					paramter.setOrderStatusList(new ArrayList());
					paramter.getOrderStatusList().add(paramter.getOrderStatus());
				}
			}
			PageHelper.startPage(page, rows);
			List<CarScrapOrderPageBO> data  = carScrapOrderMapper.queryPageListByKeywordByExamineOK(paramter);
			return new PageInfo<CarScrapOrderPageBO>(data);
		}
		return null;
	}

	public PageInfo<CarScrapOrderPageBO>  queryPageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception{
		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");

					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}

			if(StringUtils.isNotBlank(paramter.getOrderStatus())){
				if(StringUtils.indexOf(paramter.getOrderStatus(), ",")>0){
					String[] arrayOrderStatus = StringUtils.split(paramter.getOrderStatus(), ",");
					paramter.setOrderStatusList(Arrays.asList(arrayOrderStatus));
				}else{
					paramter.setOrderStatusList(new ArrayList());
					paramter.getOrderStatusList().add(paramter.getOrderStatus());
				}
			}


			PageHelper.startPage(page, rows);
			//此处判断然并卵，订单管理只进入else
			if(StringUtils.equals(CommonSystemParamter.ORDER_OLDPARTS_TYPE, paramter.getOrderType())){ //旧件订单查询
				System.out.println("进入旧件");
	        	List<CarScrapOrderPageBO> data = carScrapOrderMapper.queryOldPartsWillReceivePageListByKeyword(paramter);
	        	return new PageInfo<CarScrapOrderPageBO>(data);
			}else{   //整车订单查询
				System.out.println("进入整车");
				List<CarScrapOrderPageBO> data  = carScrapOrderMapper.queryPageListByKeyword(paramter);
	        	return new PageInfo<CarScrapOrderPageBO>(data);
			}
		}
		return null;
	}

	public PageInfo<CarScrapOrderPageBO>  queryAllByChl(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception{

		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");

					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}

			if(StringUtils.isNotBlank(paramter.getOrderStatus())){
				if(StringUtils.indexOf(paramter.getOrderStatus(), ",")>0){
					String[] arrayOrderStatus = StringUtils.split(paramter.getOrderStatus(), ",");
					paramter.setOrderStatusList(Arrays.asList(arrayOrderStatus));
				}else{
					paramter.setOrderStatusList(new ArrayList());
					paramter.getOrderStatusList().add(paramter.getOrderStatus());
				}
			}
		}
		PageHelper.startPage(page, rows);
		List<CarScrapOrderPageBO> data = carScrapOrderMapper.queryAllByChl(paramter);
		return new PageInfo<CarScrapOrderPageBO>(data);
	}

	public PageInfo<CarScrapOrderPageBO>  queryAll(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception{
		
		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");
					
					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}
			
			if(StringUtils.isNotBlank(paramter.getOrderStatus())){
				if(StringUtils.indexOf(paramter.getOrderStatus(), ",")>0){
					String[] arrayOrderStatus = StringUtils.split(paramter.getOrderStatus(), ",");
					paramter.setOrderStatusList(Arrays.asList(arrayOrderStatus));
				}else{
					paramter.setOrderStatusList(new ArrayList());
					paramter.getOrderStatusList().add(paramter.getOrderStatus());
				}
			}
		}
			PageHelper.startPage(page, rows);
			List<CarScrapOrderPageBO> data = carScrapOrderMapper.queryAll(paramter);
			return new PageInfo<CarScrapOrderPageBO>(data);
	}
	
	/**
	 * 查询操作过的订单
	 */
	public PageInfo<CarScrapOrderPageBO>  queryPageHistoryListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception{
		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");
					
					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}
		}
		PageHelper.startPage(page, rows);
		List<CarScrapOrderPageBO> data = carScrapOrderMapper.queryPageHistoryListByKeyword(paramter);
		return new PageInfo<CarScrapOrderPageBO>(data);
	}


	/**
	 * 查询操作过的订单_APP内
	 * auditer : ningrz
	 */
	public PageInfo<CarScrapOrderPageBO>  queryPageHistoryListByKeywordByChl(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception{
		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");

					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}
		}
		PageHelper.startPage(page, rows);
		List<CarScrapOrderPageBO> data = carScrapOrderMapper.queryPageHistoryListByKeywordByChl(paramter);
		return new PageInfo<CarScrapOrderPageBO>(data);
	}


	@Override
	public Integer cancelOrder(String id, String cancelMemo, String operator) throws Exception {
		CarScrapOrder orderByDB = carScrapOrderMapper.selectByPrimaryKey(id);
		if(orderByDB==null) {
			throw new DataException("没有订单信息");
		}
		CarScrapOrder carScrapOrder = new CarScrapOrder();
		carScrapOrder.setCarAscription(null);
		carScrapOrder.setIsremove(null);
		carScrapOrder.setOperatortime(new Date());
		carScrapOrder.setOperator(operator);
		carScrapOrder.setId(id);
		carScrapOrder.setOrderStatus(-1);
		carScrapOrder.setCancelMemo(cancelMemo);
		carScrapOrderMapper.updateByPrimaryKeySelective(carScrapOrder);
		
		//保存历史记录
		CarScrapOrderAudit auditData = new CarScrapOrderAudit();
		auditData.setOrderId(id);
		auditData.setStauts(CommonSystemParamter.ORDER_CANCEL_STATUS);
		auditData.setAuditor(operator);
		auditData.setAuditTime(new Date());
		auditData.setRemark("取消订单");
		auditData.setBusinessType(orderByDB.getOrderType());
		auditData.setCreater(operator);
		carScrapOrderAuditService.save(auditData);
		return 1;
	}


	@Override
	public Boolean confirmAmount(CarScrapOrderConfirmAmountVO confirmAmountVO) throws Exception {
		CarScrapOrder orderByDB = carScrapOrderMapper.selectByPrimaryKey(confirmAmountVO.getId());
		if(orderByDB==null||orderByDB.getOrderStatus() != CommonSystemParamter.ORDER_TO_COMFIRM_STATUS) {
			throw new DataException("订单状态异常");
		}
		if(StringUtils.equals(confirmAmountVO.getOrderType(), CommonSystemParamter.ORDER_OLDPARTS_TYPE)){
			//获取配件数据
			List<CarScrapOrderAutopartsConfirmVO> carScrapOrderAutopartsList = confirmAmountVO.getCarScrapOrderAutopartsList();
			if(carScrapOrderAutopartsList!=null&& carScrapOrderAutopartsList.size()>0){
				CarScrapOrderAutoparts sorder;
				for(int i=0;i<carScrapOrderAutopartsList.size();i++){
					CarScrapOrderAutopartsConfirmVO partsvo = carScrapOrderAutopartsList.get(i);
					Integer isaccept = partsvo.getAmountIsaccept();
					if(isaccept!=null ) {
						partsvo.getAmountIsaccept();
						sorder = new CarScrapOrderAutoparts();
						sorder.setId(partsvo.getId());
						Integer orderStatus=null;
						if(isaccept == 0) {//接受
							orderStatus = CommonSystemParamter.PARTS_CREATE_STATUS;
						}else {//不接受
							orderStatus = CommonSystemParamter.PARTS_REJECTED_STATUS;
						}
						sorder.setOrderAutopartsStatus(orderStatus);
						sorder.setOperator(confirmAmountVO.getOperator());
						sorder.setOperatortime(new Date());
						carScrapOrderAutopartsService.updateAutopartsByIdSelective(sorder);
						//保存历史记录 
						CarScrapOrderAudit auditData = new CarScrapOrderAudit();
						auditData.setOrderId(partsvo.getId());
						auditData.setStauts(orderStatus);
						auditData.setAuditor(confirmAmountVO.getOperator());
						auditData.setAuditTime(new Date());
						auditData.setRemark("确认报价");
						auditData.setBusinessType(orderByDB.getOrderType());
						auditData.setCreater(confirmAmountVO.getOperator());
						carScrapOrderAuditService.save(auditData);
						if(isaccept !=0) {
							//报价不接受,删除原数据,新增删除数据
							CarScrapOrderAutoparts autoPartsDB = carScrapOrderAutopartsService.queryById(partsvo.getId());
							CarScrapOrderAutopartsDel autoPartsDel = new CarScrapOrderAutopartsDel();
							BeanUtils.copyProperties(autoPartsDB, autoPartsDel);
							carScrapOrderAutopartsDelMapper.insert(autoPartsDel);
							carScrapOrderAutopartsService.deleteById(partsvo.getId());
						}
					}
					
				}
			}
			/**
			 * 查询旧件配件以更新旧件状态 若全已确定报价 则更新旧件订单状态
			 */
			Boolean flag= true;
			Boolean rejectFlag= true;
			List<CarScrapOrderAutopartsBO> queryListByDB = carScrapOrderAutopartsService.queryListByOrderId(confirmAmountVO.getId());
			for (CarScrapOrderAutopartsBO carScrapOrderAutopartsBO : queryListByDB) {
				if(carScrapOrderAutopartsBO.getOrderAutopartsStatus()==CommonSystemParamter.PARTS_TO_COMFIRM_STATUS)
					flag = false;//还有未确定报价的
				if(carScrapOrderAutopartsBO.getOrderAutopartsStatus()!=CommonSystemParamter.PARTS_REJECTED_STATUS)
					rejectFlag = false;//有没驳回的
			}
			if(flag&&queryListByDB!=null&&queryListByDB.size()>0) {
				CarScrapOrder order =new CarScrapOrder();
				order.setId(confirmAmountVO.getId());
				order.setOrderStatus(CommonSystemParamter.ORDER_CREATE_STATUS);
				order.setOperator(confirmAmountVO.getOperator());
				order.setOperatortime(new Date());
				carScrapOrderMapper.updateByPrimaryKeySelective(order);
				//保存历史记录 
				CarScrapOrderAudit auditData = new CarScrapOrderAudit();
				auditData.setOrderId(confirmAmountVO.getId());
				auditData.setStauts(CommonSystemParamter.ORDER_CREATE_STATUS);
				auditData.setAuditor(confirmAmountVO.getOperator());
				auditData.setAuditTime(new Date());
				auditData.setRemark("确认报价");
				auditData.setBusinessType(CommonSystemParamter.BUSINESS_TYPE_ORDER);
				auditData.setCreater(confirmAmountVO.getOperator());
				carScrapOrderAuditService.save(auditData);
			}
			if(rejectFlag) {
				CarScrapOrder order =new CarScrapOrder();
				order.setId(confirmAmountVO.getId());
				order.setOrderStatus(CommonSystemParamter.ORDER_REJECTED_STATUS);
				order.setOperator(confirmAmountVO.getOperator());
				order.setOperatortime(new Date());
				carScrapOrderMapper.updateByPrimaryKeySelective(order);
				//保存历史记录 
				CarScrapOrderAudit auditData = new CarScrapOrderAudit();
				auditData.setOrderId(confirmAmountVO.getId());
				auditData.setStauts(CommonSystemParamter.ORDER_REJECTED_STATUS);
				auditData.setAuditor(confirmAmountVO.getOperator());
				auditData.setAuditTime(new Date());
				auditData.setRemark("确认报价");
				auditData.setBusinessType(CommonSystemParamter.BUSINESS_TYPE_ORDER);
				auditData.setCreater(confirmAmountVO.getOperator());
				carScrapOrderAuditService.save(auditData);
			}
		}else {
			if(confirmAmountVO.getAmountIsaccept()!=null ) {
				CarScrapOrder order =new CarScrapOrder();
				order.setId(confirmAmountVO.getId());
				Integer orderStatue = null;
				if(confirmAmountVO.getAmountIsaccept() == 0) {//接受报价
					orderStatue = CommonSystemParamter.ORDER_CREATE_STATUS;
				}else {
					//不接受报价
					orderStatue = CommonSystemParamter.ORDER_REJECTED_STATUS;
				}
				order.setOrderStatus(orderStatue);
				order.setOperator(confirmAmountVO.getOperator());
				order.setOperatortime(new Date());
				carScrapOrderMapper.updateByPrimaryKeySelective(order);
				//保存历史记录 
				CarScrapOrderAudit auditData = new CarScrapOrderAudit();
				auditData.setOrderId(confirmAmountVO.getId());
				auditData.setStauts(orderStatue);
				auditData.setAuditor(confirmAmountVO.getOperator());
				auditData.setAuditTime(new Date());
				auditData.setRemark("确认报价");
				auditData.setBusinessType(orderByDB.getOrderType());
				auditData.setCreater(confirmAmountVO.getOperator());
				carScrapOrderAuditService.save(auditData);
			}
			
		}
		return true;
	}


	@Override
	public CarScrapOrderMyTradeBO myTrade(String clientId) throws Exception {
		return carScrapOrderMyTradeMapper.queryMyTrade(clientId);
	}


	@Override
	public PageInfo<CarScrapOrderMyTradeInfoBO> myTradeInfo(Integer page, Integer rows, String clientId) throws Exception {
		PageHelper.startPage(page, rows);
		List<CarScrapOrderMyTradeInfoBO> data = carScrapOrderMyTradeMapper.queryPageMyTradeInfo(clientId);
		return new PageInfo<CarScrapOrderMyTradeInfoBO>(data);
	}
	@Override
	public Boolean quote(CarScrapOrderQuoteVO quoteVO) throws Exception {
		/**
		 * 根据用户角色设置订单状态 分总报完 12  总部报完 13
		 */
		Integer userType = -1;
		UserBO userBOByDB = userService.queryUserBOById(quoteVO.getOperator());
		List<String> areasidList = null;
		if(StringUtils.isNotBlank(userBOByDB.getAreasids())) {
			areasidList = Arrays.asList(userBOByDB.getAreasids().split(","));
		}
		if(areasidList!=null) {
			if(areasidList.size()==1 && areasidList.contains(CommonSystemParamter.HEAD_AREA_ID)) {
				userType = 0;//角色为总部
			}else if(areasidList.contains(CommonSystemParamter.HEAD_AREA_ID)){
				userType = 2;//角色为总部/分总
			}else {
				userType = 1;//角色为分总
			}
		}
		if(userType == -1) {
			throw new DataException("用户异常");
		}
		CarScrapOrder orderByDB = carScrapOrderMapper.selectByPrimaryKey(quoteVO.getId());
		if(orderByDB==null) {
			throw new DataException("订单异常");
		}
		Integer orderStatusByDB = orderByDB.getOrderStatus();
		if(userType == 0) {
			if(orderStatusByDB != CommonSystemParamter.ORDER_TO_QUOTED_STATUS) {
				throw new DataException("订单状态异常");
			}
		}else if(userType == 1){
			if(orderStatusByDB != CommonSystemParamter.ORDER_TO_SUB_QUOTED_STATUS) {
				throw new DataException("订单状态异常");
			}
		}else {//分总&总部
			if(orderStatusByDB != CommonSystemParamter.ORDER_TO_QUOTED_STATUS&&orderStatusByDB != CommonSystemParamter.ORDER_TO_SUB_QUOTED_STATUS) {
				throw new DataException("订单状态异常");
			}
			if(orderStatusByDB == CommonSystemParamter.ORDER_TO_SUB_QUOTED_STATUS) {
				userType = 1;//分总
			}else {
				userType = 0;//总部
			}
		}
		if(StringUtils.equals(quoteVO.getOrderType(), CommonSystemParamter.ORDER_OLDPARTS_TYPE)){
			//获取配件数据
			List<CarScrapOrderAutopartsQuoteVO> autopartsQuoteVOList = quoteVO.getCarScrapOrderAutopartsList();
			if(autopartsQuoteVOList!=null&& autopartsQuoteVOList.size()>0){
				CarScrapOrderAutoparts sorder;
				for(int i=0;i<autopartsQuoteVOList.size();i++){
					CarScrapOrderAutopartsQuoteVO autopartsQuoteVO = autopartsQuoteVOList.get(i);
					if(StringUtils.isNotBlank(autopartsQuoteVO.getId())&&StringUtils.isNotBlank(autopartsQuoteVO.getAmount())) {
						sorder = new CarScrapOrderAutoparts();
						sorder.setId(autopartsQuoteVO.getId());
						sorder.setOperator(quoteVO.getOperator());
						sorder.setOperatortime(new Date());
						if(userType == 0) {//总部
							sorder.setAmount(autopartsQuoteVO.getAmount());
							sorder.setOrderAutopartsStatus(CommonSystemParamter.PARTS_TO_COMFIRM_STATUS);//待确认报价
						}else if(userType == 1) {//分总
							sorder.setSubQuote(autopartsQuoteVO.getAmount());
							sorder.setOrderAutopartsStatus(CommonSystemParamter.PARTS_TO_QUOTED_STATUS);//待总部报价
						}
						carScrapOrderAutopartsService.updateAutopartsByIdSelective(sorder);
					}
				}
			}
			/**
			 * 查询旧件配件以更新旧件状态 若全已报价 则更新旧件订单状态
			 */
			Boolean flag= true;
			List<CarScrapOrderAutopartsBO> queryListByDB = carScrapOrderAutopartsService.queryListByOrderId(quoteVO.getId());
			if(userType == 1) {//分总
				for (CarScrapOrderAutopartsBO carScrapOrderAutopartsBO : queryListByDB) {
					if(carScrapOrderAutopartsBO.getOrderAutopartsStatus()==CommonSystemParamter.PARTS_TO_SUB_QUOTED_STATUS)
						flag = false;//还有分总未报价的
				}
			}else if(userType == 0){//总部
				for (CarScrapOrderAutopartsBO carScrapOrderAutopartsBO : queryListByDB) {
					if(carScrapOrderAutopartsBO.getOrderAutopartsStatus()==CommonSystemParamter.PARTS_TO_QUOTED_STATUS)
						flag = false;//还有总部未报价的
				}
			}
			if(flag) {
				CarScrapOrder order =new CarScrapOrder();
				order.setId(quoteVO.getId());
				if(userType == 1) {
					order.setOrderStatus(CommonSystemParamter.ORDER_TO_QUOTED_STATUS);
				}else if(userType == 0){
					order.setOrderStatus(CommonSystemParamter.ORDER_TO_COMFIRM_STATUS);
				}
				order.setOperator(quoteVO.getOperator());
				order.setOperatortime(new Date());
				carScrapOrderMapper.updateByPrimaryKeySelective(order);
			}
		}else {
			if(StringUtils.isNotBlank(quoteVO.getId())&&StringUtils.isNotBlank(quoteVO.getAmount())) {
				CarScrapOrder order =new CarScrapOrder();
				order.setId(quoteVO.getId());
				order.setOperator(quoteVO.getOperator());
				order.setOperatortime(new Date());
				if(userType == 0) {
					order.setOrderAmount(quoteVO.getAmount());
					order.setOrderStatus(CommonSystemParamter.ORDER_TO_COMFIRM_STATUS);
				} else if(userType == 1) {
					order.setSubQuote(quoteVO.getAmount());
					order.setOrderStatus(CommonSystemParamter.ORDER_TO_QUOTED_STATUS);
				}
				carScrapOrderMapper.updateByPrimaryKeySelective(order);
			}
		}
		return true;
	}

	@Override
	public Boolean quoteByPush(CarScrapOrderQuoteVO quoteVO) throws Exception {
		/**
		 * 根据用户角色设置订单状态 分总报完 12  总部报完 13  
		 */
		String orderId = quoteVO.getId();
		List<CarScrapOrderPageBO> orderList = carScrapOrderMapper.queryPageListByPush(orderId);
		String clientId = orderList.get(0).getClientId();

		//查询当前用户的手机号
		ClientBO clientbo = clientService.getClientById(clientId);
		String clientPhone = clientbo.getPhone();

		//短信内容
		//String order_no = "尊敬的车主，您的整车报废订单（订单号:"+orderList.get(0).getOrderNo()+"）已报价，请您前往车互联APP查看（http://www.chehulian.com/）";
		String order_no = orderList.get(0).getOrderNo();

//		根据手机号查询客户端用户ID，用于总部报价完成后推送
//		CarScrapOrder corder = carScrapOrderMapper.getClientId(postPhone);
//		System.out.println(corder);

		Integer userType = -1;
		UserBO userBOByDB = userService.queryUserBOById(quoteVO.getOperator());
		List<String> areasidList = null;
		if(StringUtils.isNotBlank(userBOByDB.getAreasids())) {
			areasidList = Arrays.asList(userBOByDB.getAreasids().split(","));
		}
		if(areasidList!=null) {
			if(areasidList.size()==1 && areasidList.contains(CommonSystemParamter.HEAD_AREA_ID)) {
				System.out.println("进入总部");
				userType = 0;//角色为总部
			 } else if(areasidList.contains(CommonSystemParamter.HEAD_AREA_ID)){
				userType = 2;//角色为总部/分总
			} else {
				System.out.println("进入分部");
				userType = 1;//角色为分部
			}
		}
		if(userType == -1) {
			throw new DataException("用户异常");
		}
		CarScrapOrder orderByDB = carScrapOrderMapper.selectByPrimaryKey(quoteVO.getId());
		if(orderByDB==null) {
			throw new DataException("订单异常");
		}
		Integer orderStatusByDB = orderByDB.getOrderStatus();
		if(userType == 0) {
			if(orderStatusByDB != CommonSystemParamter.ORDER_TO_QUOTED_STATUS) {
				throw new DataException("订单状态异常");
			}			
		}else if(userType == 1){
			if(orderStatusByDB != CommonSystemParamter.ORDER_TO_SUB_QUOTED_STATUS) {
				throw new DataException("订单状态异常");
			}
		}else {//分总&总部
			if(orderStatusByDB != CommonSystemParamter.ORDER_TO_QUOTED_STATUS&&orderStatusByDB != CommonSystemParamter.ORDER_TO_SUB_QUOTED_STATUS) {
				throw new DataException("订单状态异常");
			}
			if(orderStatusByDB == CommonSystemParamter.ORDER_TO_SUB_QUOTED_STATUS) {
				userType = 1;//分部
			}else {
				userType = 0;//总部
			}
		}
		if(StringUtils.equals(quoteVO.getOrderType(), CommonSystemParamter.ORDER_OLDPARTS_TYPE)){
			//获取配件数据
			List<CarScrapOrderAutopartsQuoteVO> autopartsQuoteVOList = quoteVO.getCarScrapOrderAutopartsList();
			if(autopartsQuoteVOList!=null&& autopartsQuoteVOList.size()>0){
				CarScrapOrderAutoparts sorder;
				for(int i=0;i<autopartsQuoteVOList.size();i++){
					CarScrapOrderAutopartsQuoteVO autopartsQuoteVO = autopartsQuoteVOList.get(i);
					if(StringUtils.isNotBlank(autopartsQuoteVO.getId())&&StringUtils.isNotBlank(autopartsQuoteVO.getAmount())) {
						sorder = new CarScrapOrderAutoparts();
						sorder.setId(autopartsQuoteVO.getId());
						sorder.setOperator(quoteVO.getOperator());
						sorder.setOperatortime(new Date());
						if(userType == 0) {//总部
							sorder.setAmount(autopartsQuoteVO.getAmount());
							sorder.setOrderAutopartsStatus(CommonSystemParamter.PARTS_TO_COMFIRM_STATUS);//待确认报价
						}else if(userType == 1) {//分总
							sorder.setSubQuote(autopartsQuoteVO.getAmount());
							sorder.setOrderAutopartsStatus(CommonSystemParamter.PARTS_TO_QUOTED_STATUS);//待总部报价						
						}
						carScrapOrderAutopartsService.updateAutopartsByIdSelective(sorder);
					}
				}
			}
			/**
			 * 查询旧件配件以更新旧件状态 若全已报价 则更新旧件订单状态
			 */
			Boolean flag= true;
			List<CarScrapOrderAutopartsBO> queryListByDB = carScrapOrderAutopartsService.queryListByOrderId(quoteVO.getId());
			if(userType == 1) {//分总
				for (CarScrapOrderAutopartsBO carScrapOrderAutopartsBO : queryListByDB) {
					if(carScrapOrderAutopartsBO.getOrderAutopartsStatus()==CommonSystemParamter.PARTS_TO_SUB_QUOTED_STATUS)
						flag = false;//还有分总未报价的
				}				
			}else if(userType == 0){//总部
				for (CarScrapOrderAutopartsBO carScrapOrderAutopartsBO : queryListByDB) {
					if(carScrapOrderAutopartsBO.getOrderAutopartsStatus()==CommonSystemParamter.PARTS_TO_QUOTED_STATUS)
						flag = false;//还有总部未报价的
				}
			}
			if(flag) {
				CarScrapOrder order =new CarScrapOrder();
				order.setId(quoteVO.getId());
				if(userType == 1) {
					order.setOrderStatus(CommonSystemParamter.ORDER_TO_QUOTED_STATUS);
				}else if(userType == 0){
					order.setOrderStatus(CommonSystemParamter.ORDER_TO_COMFIRM_STATUS);
					if(clientId!=null){
						List<CarPush> carPushList = carPushService.getAllCarPush();
						CarPush carPush = carPushList.get(2);
						JPushUtil.sendToRegistrationId(clientId,carPush.getNotificationTitle(),carPush.getMsgTitle(),carPush.getMsgContent(),"2");
					}
				}
				order.setOperator(quoteVO.getOperator());
				order.setOperatortime(new Date());
				carScrapOrderMapper.updateByPrimaryKeySelective(order);
			}
		}else {
			if(StringUtils.isNotBlank(quoteVO.getId())&&StringUtils.isNotBlank(quoteVO.getAmount())) {
				CarScrapOrder order =new CarScrapOrder();
				order.setId(quoteVO.getId());
				order.setOperator(quoteVO.getOperator());
				order.setOperatortime(new Date());
				if(userType == 0) {
					System.out.println("这是总部报的价格");
					order.setOrderAmount(quoteVO.getAmount());
					order.setOrderStatus(CommonSystemParamter.ORDER_TO_COMFIRM_STATUS);
					if(clientId!=null){
						List<CarPush> carPushList = carPushService.getAllCarPush();
						CarPush carPush = carPushList.get(1);
						//确认报价后通知当前报废车用户
						JPushUtil.sendToRegistrationId(clientId,carPush.getNotificationTitle(),carPush.getMsgTitle(),carPush.getMsgContent(),"3");
						//JPushUtil.sendToRegistrationId("50e1cecec32340c1877894e11d47cc0a",carPush.getNotificationTitle(),carPush.getMsgTitle(),carPush.getMsgContent(),"3");
						System.out.println("发送短信的手机号:"+clientPhone);
						System.out.println("发送短信的模板格式:"+SmsSender.formatmsg);
						System.out.println("发送短信的内容:"+order_no);

						//短信通知用户
						SmsSender.sendSmsByOrder(clientPhone, SmsSender.formatmsg, SmsSender.PUBLICEORDER,order_no);
					}
				} else if(userType == 1) {
					System.out.println("这是分部报的价格");
					order.setSubQuote(quoteVO.getAmount());
					order.setOrderStatus(CommonSystemParamter.ORDER_TO_QUOTED_STATUS);
				}
				carScrapOrderMapper.updateByPrimaryKeySelective(order);
			}
		}
		return true;
	}


	@Override
	public PageInfo<CarScrapOrderQuotePageBO> queryQuotePageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter) throws Exception {
		if(paramter!=null){
			if(StringUtils.isNotBlank(paramter.getAraesids())){
				if(StringUtils.indexOf(paramter.getAraesids(), ",")>0){
					String[] arrayAraes = StringUtils.split(paramter.getAraesids(), ",");
					paramter.setAraesidList(Arrays.asList(arrayAraes));
				}else{
					paramter.setAraesidList(new ArrayList<String>());
					paramter.getAraesidList().add(paramter.getAraesids());
				}
			}
			
			if(StringUtils.isNotBlank(paramter.getOrderStatus())){
				if(StringUtils.indexOf(paramter.getOrderStatus(), ",")>0){
					String[] arrayOrderStatus = StringUtils.split(paramter.getOrderStatus(), ",");
					paramter.setOrderStatusList(Arrays.asList(arrayOrderStatus));
				}else{
					paramter.setOrderStatusList(new ArrayList<String>());
					paramter.getOrderStatusList().add(paramter.getOrderStatus());
				}
			}
			PageHelper.startPage(page, rows);

			List<CarScrapOrderQuotePageBO> data  = carScrapOrderMapper.queryQuotePageListByKeyword(paramter);
			
			return new PageInfo<CarScrapOrderQuotePageBO>(data);
		}
		return null;
	}

	@Override
	public Boolean TellUserTakeCar(String clientId,String salePhone,String saleName,String orderId) {
		try{
			ClientBO cb = clientService.getClientById(clientId);
			System.out.println("cccccccc"+cb);
			CarScrapOrder order = this.queryById(orderId);
			//更新is_take字段为已点击
			order.setIsTake(1);
			int result = super.updateSelective(order);
			if(result>0){
				System.out.println("更新is_take状态成功！");
			}
			//短信通知用户
			System.out.println("要发送的手机号为:"+cb.getPhone());
			SmsSender.sendSmsByTakeCar(cb.getPhone(),SmsSender.formatmbg,SmsSender.PUBLICETAKECAR,salePhone,saleName);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean TellMoneyByUser(String carNumber,String orderId) {
		try{
			//获取所有的财务人员
			List<UserSimpleBO> userList = userService.getAllMoneyPeople(34);
			for (UserSimpleBO money:userList) {
				SmsSender.sendSmsByMoney(money.getContactPhone(), SmsSender.formatmcg, SmsSender.PUBLICETAKEMONEY,carNumber,orderId);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

}
