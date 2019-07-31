package com.car.app.carscraporder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsDetailBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.pojo.CarScrapOrderAutoparts;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.github.abel533.mapper.Mapper;

public interface CarScrapOrderAutopartsMapper extends Mapper<CarScrapOrderAutoparts>{
	
	 CarScrapOrderAutopartsBO  queryBOById(String id);

	 CarScrapOrderAutopartsDetailBO getOrderAutopartsDetailById(String id);

	  List<CarScrapOrderAutopartsBO> queryListByOrderId(String orderid);

	 List<CarScrapOrderPageBO> queryBOPageList(@Param("vo")CarScrapOrderKeywordVO paramter);
	
	 List<CarScrapOrderPageBO> getSortingParts(@Param("vo")CarScrapOrderKeywordVO paramter);
	
	 List<CarScrapOrderPageBO> queryHistoryBOPageList(@Param("vo")CarScrapOrderKeywordVO paramter);
	
	 Long queryBOPageListCount(@Param("vo")CarScrapOrderKeywordVO paramter);

	 Integer queryPendingOrder(@Param("agentUserid")String agentUserid,@Param("partsStauts")Integer partsStauts,@Param("areasids")List<String> areasids,@Param("isSorting")Boolean isSorting);

	 Integer selectPartCount(@Param("vo")CarScrapOrderKeywordVO paramter);
}
