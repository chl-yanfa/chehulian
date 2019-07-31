package com.car.app.carscarporder.web.service;

import java.util.List;

import com.car.app.carscraporder.bo.DictionaryBO;
import com.car.app.carscraporder.bo.DictionarySystemBO;
import com.car.app.carscraporder.pojo.TbAreas;
import com.car.app.carscraporder.pojo.TbCityes;
import com.car.app.carscraporder.pojo.TbProvinces;

public interface DictionaryService {
	
	public List<DictionaryBO> getAllProvince()throws Exception;
	
	public List<DictionaryBO> getAllCityByProvinceId(String provinceId) throws Exception;
	
	public List<DictionaryBO> getAllAreaByCityId(String cityId) throws Exception;
	
	public TbProvinces getProvince(String provinceid) throws Exception;
	public TbCityes getCity(String cityid) throws Exception;
	public TbAreas getTbAreas(String areaid) throws Exception;
	
	public List<DictionarySystemBO> getAllCarBrand()throws Exception;
	public List<DictionarySystemBO> getCarSystem(String brandId)throws Exception;
	public List<DictionaryBO> getCarModel(String systemId)throws Exception;

}
