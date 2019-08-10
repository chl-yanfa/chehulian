package com.car.app.carscraporder.systemparameter;

public class CommonSystemParamter {
	
	/**
	 * 1.(未接单)(提交订单)
	 */
	public static final Integer ORDER_CREATE_STATUS=1;
	/**
	 * 11.待分部报价
	 */
	public static final Integer ORDER_TO_SUB_QUOTED_STATUS=11;
	/**
	 * 12.待总部报价
	 */
	public static final Integer ORDER_TO_QUOTED_STATUS=12;
	/**
	 * 13.待确定报价
	 */
	public static final Integer ORDER_TO_COMFIRM_STATUS=13;
	/**
	 * 14.报价驳回
	 */
	public static final Integer ORDER_REJECTED_STATUS=14;
	/**
	 * 2.派单
	 */
	public static final Integer ORDER_DISTRIBUTION_STATUS=2;
	/**
	 * 97.用户再次接收
	 */
	public static final Integer ORDER_CLIENT_CONFIRM_STATUS=97;
	/**
	 * 3.业务员接收车辆
	 */
	public static final Integer ORDER_RECEIVE_STATUS=3;
	/**
	 * 4.入场
	 */
	public static final Integer ORDER_STORAGE_STATUS=4;
	/**
	 * 5.报废(出库)
	 */
	public static final Integer ORDER_SCRAP_STATUS=5;
	
	/**
	 * 6.财务结算
	 */
	public static final Integer ORDER_SETTLEMENT_STATUS=6;
	
	/**
	 * 7.订单结束
	 */
	public static final Integer ORDER_END_STATUS=7;
	
	/**
	 * 8.订单异常
	 */
	public static final Integer ORDER_ERROR_STATUS=8;

	/**
	 * 33.业务员待接单
	 */
	public static final Integer ORDER_NOOK_STATUS=33;

	/**
	 * -1.订单取消
	 */
	public static final Integer ORDER_CANCEL_STATUS=-1;

	
	/**
	 * 1.配件初始状态
	 */
	public static final Integer PARTS_CREATE_STATUS=1;
	/**
	 * 11.配件待分部报价
	 */
	public static final Integer PARTS_TO_SUB_QUOTED_STATUS=11;
	/**
	 * 12.配件待总部报价
	 */
	public static final Integer PARTS_TO_QUOTED_STATUS=12;
	/**
	 * 13.配件待确定报价
	 */
	public static final Integer PARTS_TO_COMFIRM_STATUS=13;
	/**
	 * 14.配件报价驳回
	 */
	public static final Integer PARTS_REJECTED_STATUS=14;
	/**
	 * 2.配件接单,派单
	 */
	public static final Integer PARTS_RECEIPT_STATUS=2;
	/**
	 * 3.配件接收
	 */
	public static final Integer PARTS_RECEIVE_STATUS=3;
	
	/**
	 * 4.配件无法接收
	 */
	public static final Integer PARTS_NO_RECEIVE_STATUS=4;
	
	
	/**
	 * 5.配件入库
	 */
	public static final Integer PARTS_STORAGE_STATUS=5;
	
	
	/**
	 * 6.配件出库
	 */
	public static final Integer PARTS_OUT_STORAGE_STATUS=6;
	/**
	 * 7.配件残值发放
	 */
	public static final Integer PARTS_SETTLEMENT_STATUS=7;
	/**
	 * 8.配件结束
	 */
	public static final Integer PARTS_END_STATUS=8;
	/**
	 * 9.配件异常
	 */
	public static final Integer PARTS_ERROR_STATUS=9;

	/**
	 * 业务员拒收车辆
	 */
	public static final Integer ORDER_RE_FUSE=96;
	/**
	 * -1.配件取消
	 */
	public static final Integer PARTS_CANCEL_STATUS=-1;
	
	
	/**
	 * 个人客户
	 */
	public static final String CLIENT_ORDINARY_TYPE="1";
	/**
	 * 大客户类型
	 */
	public static final String CLIENT_VIP_TYPE="2";
	/**
	 * 整车订单
	 */
	public static final String ORDER_WhOLECAR_TYPE="1";
	
	/**
	 * 旧件订单
	 */
	public static final String ORDER_OLDPARTS_TYPE="2";
	
	/**
	 * 用户状态 ：正常
	 */
	public static final String USER_STATUS_NORMAL="1";
	/**
	 * 用户状态 ：删除
	 */
	public static final String USER_STATUS_DELETE="2";
	/**
	 * 用户状态:禁用
	 */
	public static final String USER_STATUS_DISABLE="3";
	/**
	 * 密码hash次数
	 */
	public static final int HASHITERATIONS =2;
	
	/**
	 * 订单业务（相对配件订单业务例如：旧件订单和配件订单）
	 */
	public static final String BUSINESS_TYPE_ORDER="1";
	/**
	 * 配件业务
	 */
	public static final String BUSINESS_TYPE_AUTOPARTS="2";
	
	/**
	 * 车辆照片
	 */
	public static final String ORDER_PICTURE_CAR="1";
	/**
	 * 车辆手续照片
	 */
	public static final String ORDER_PICTURE_CAR_FORMALITIES="2";
	/**
	 *车辆毁型照片
	 */
	public static final String ORDER_PICTURE_CAR_DESTROY="3";
	/**
	 *车辆报废证明照片
	 */
	public static final String ORDER_PICTURE_CAR_CERTIFICATE="4";
	/**
	 *车辆报废证明照片
	 */
	public static final String ORDER_PICTURE_CAR_SINCE="5";
	/**
	 * 配件照片
	 */
	public static final String PARTS_PICTURE="1";
	/**
	 * 配件接收照片
	 */
	public static final String PARTS_PICTURE_RECEIVE="2";
	/**
	 * 配件入库照片
	 */
	public static final String PARTS_PICTURE_WAREHIUSING="3";
	
	/**
	 * 配件入库照片
	 */
	public static final String PARTS_SCARP_RECEIVE="4";
	
	public static final String PARTS_VIDEO="10";
	/**
	 * 总部ID 1
	 */
	public static final String HEAD_AREA_ID="1";
	
	/**
	 * 用户实名认证状态 未实名 0
	 */
	public static final Integer CLIENT_CERTIFICATION_NO_STATE=0;
	/**
	 * 用户实名认证状态 正在审核 1
	 */
	public static final Integer CLIENT_CERTIFICATION_TOAUDIT_STATE=1;
	/**
	 * 用户实名认证状态 审核成功 2
	 */
	public static final Integer CLIENT_CERTIFICATION_ED_STATE=2;
	
	/**
	 * 用户实名认证状态 审核驳回 -1
	 */
	public static final Integer CLIENT_CERTIFICATION_REFUSE_STATE=-1;
	
	/**
	 * 用户实名认证记录表状态 通过 0
	 */
	public static final Integer CLIENT_CERTIFICATION_RECORD_AGREE_STATE=0;
	
	/**
	 * 用户实名认证记录表状态 驳回 1
	 */
	public static final Integer CLIENT_CERTIFICATION_RECORD_REFUSE_STATE=1;

}
