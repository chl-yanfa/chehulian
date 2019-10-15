package com.car.app.carscarporder.web.service;

import java.util.List;

import com.car.app.carscraporder.bo.DictionaryBO;
import com.car.app.carscraporder.bo.DictionarySystemBO;
import com.car.app.carscraporder.pojo.TbAreas;
import com.car.app.carscraporder.pojo.TbCityes;
import com.car.app.carscraporder.pojo.TbProvinces;

public interface DictionaryService {
	
	List<DictionaryBO> getAllProvince()throws Exception;
	
	List<DictionaryBO> getAllCityByProvinceId(String provinceId) throws Exception;
	
	List<DictionaryBO> getAllAreaByCityId(String cityId) throws Exception;
	
	TbProvinces getProvince(String provinceid) throws Exception;
	TbCityes getCity(String cityid) throws Exception;
	TbAreas getTbAreas(String areaid) throws Exception;
	
	List<DictionarySystemBO> getAllCarBrand()throws Exception;
	List<DictionarySystemBO> getCarSystem(String brandId)throws Exception;
	List<DictionaryBO> getCarModel(String systemId)throws Exception;

}
