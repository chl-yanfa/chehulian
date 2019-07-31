package com.car.app.carscraporder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.CarScrapOrderQuotePageBO;
import com.car.app.carscraporder.pojo.CarScrapOrder;
import com.car.app.carscraporder.pojo.CarScrapOrderExample;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.github.abel533.mapper.Mapper;


public interface CarScrapOrderMapper extends Mapper<CarScrapOrder>{
	
		List<CarScrapOrderPageBO>  queryPageListByWhere(CarScrapOrderExample example);

		List<CarScrapOrderPageBO>  queryPageListByPush(@Param("id")String id);
	
		List<CarScrapOrderPageBO>  queryPageListByKeyword(@Param("vo")CarScrapOrderKeywordVO vo);

		List<CarScrapOrderPageBO>  queryPageListByKeywordByChl(@Param("vo")CarScrapOrderKeywordVO vo);
	
		List<CarScrapOrderPageBO>  queryOldPartsWillReceivePageListByKeyword(@Param("vo")CarScrapOrderKeywordVO vo);
	
		List<CarScrapOrderPageBO> queryAll(@Param("vo")CarScrapOrderKeywordVO vo);

		List<CarScrapOrderPageBO> queryAllByChl(@Param("vo")CarScrapOrderKeywordVO vo);

		List<CarScrapOrderPageBO>  queryPageHistoryListByKeyword(@Param("vo")CarScrapOrderKeywordVO vo);

		List<CarScrapOrderPageBO>  queryPageHistoryListByKeywordByChl(@Param("vo")CarScrapOrderKeywordVO vo);

		List<CarScrapOrderPageBO>  queryPageListByKeywordByExamine(@Param("vo")CarScrapOrderKeywordVO vo);

		List<CarScrapOrderPageBO>  queryPageListByKeywordByExamineOK(@Param("vo")CarScrapOrderKeywordVO vo);

	    List<CarScrapOrderPageBO>  queryPageListByKeywordByCancel(@Param("vo")CarScrapOrderKeywordVO vo);

	    CarScrapOrderBO queryBOById(String id);
	
	    List<CarScrapOrderQuotePageBO>  queryQuotePageListByKeyword(@Param("vo")CarScrapOrderKeywordVO vo);
}
