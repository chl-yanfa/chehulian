package com.car.app.carscraporder.applet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.DictionaryService;
import com.car.app.carscarporder.web.service.TbCarPartsCategoryService;
import com.car.app.carscarporder.web.service.TbCarPartsService;
import com.car.app.carscraporder.bo.DictionaryBO;
import com.car.app.carscraporder.bo.DictionarySystemBO;
import com.car.app.carscraporder.pojo.TbAreas;
import com.car.app.carscraporder.pojo.TbCarParts;
import com.car.app.carscraporder.pojo.TbCarPartsCategory;
import com.car.app.carscraporder.pojo.TbCityes;
import com.car.app.carscraporder.pojo.TbProvinces;
import com.car.app.common.bean.ResultBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value="/dictionary")
@Api(value = "dictionary-API",description = "省份地区字典查询")
public class DictionaryController {
	
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private TbCarPartsCategoryService  tbCarPartsCategoryService;
	
	@Autowired
	private TbCarPartsService   tbCarPartsService;
	
	
	
	@RequestMapping(value="getAllProvince",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询所有省份数据",notes = "查询所有省份数据")
	public ResultBean<List<DictionaryBO>> getAllProvince() throws Exception{
		
		return new ResultBean<List<DictionaryBO>>(dictionaryService.getAllProvince());

	}
	
	@RequestMapping(value="getProvince/{provinceid}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据省份id查询省份数据",notes = "根据省份id查询省份数据")
	public ResultBean<TbProvinces> getProvince(@PathVariable("provinceid")String provinceid) throws Exception{
		
		return new ResultBean<TbProvinces>(dictionaryService.getProvince(provinceid));

	}
	
	@RequestMapping(value="getCity/{cityid}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据省份id查询省份数据",notes = "根据省份id查询省份数据")
	public ResultBean<TbCityes> getCity(@PathVariable("cityid")String cityid) throws Exception{
		
		return new ResultBean<TbCityes>(dictionaryService.getCity(cityid));

	}
	
	@RequestMapping(value="getTbAreas/{areaid}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据省份id查询省份数据",notes = "根据省份id查询省份数据")
	public ResultBean<TbAreas> getTbAreas(@PathVariable("areaid")String areaid) throws Exception{
		
		return new ResultBean<TbAreas>(dictionaryService.getTbAreas(areaid));

	}
	
	@RequestMapping(value="getAllCityByProvinceId/{provinceid}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取区域详情",notes = "根据id获取区域详情")
	public ResultBean<List<DictionaryBO>> getAllCityByProvinceId(@PathVariable("provinceid")String provinceid) throws Exception{
		return new ResultBean<List<DictionaryBO>>(dictionaryService.getAllCityByProvinceId(provinceid));

	}
	
	@RequestMapping(value="getAllAreaByCityId/{cityid}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取区域详情",notes = "根据id获取区域详情")
	public ResultBean<List<DictionaryBO>> getAllAreaByCityId(@PathVariable("cityid")String cityid) throws Exception{
		return new ResultBean<List<DictionaryBO>>(dictionaryService.getAllAreaByCityId(cityid));

	}
	
	@RequestMapping(value="/getAll",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取配件下拉框数据",notes = "获取配件下拉框数据")
	public ResultBean<List<TbCarPartsCategory>> getCarPartsCategory() throws Exception{
		
		List<TbCarPartsCategory> carPartsCategoryList = tbCarPartsCategoryService.getAll();
		
		
	
		return new ResultBean<List<TbCarPartsCategory>>(carPartsCategoryList);
	}
	
	@RequestMapping(value="getByCategoryId",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取配件下拉框数据",notes = "获取配件下拉框数据")
	public ResultBean<List<TbCarParts>> getCarPartsByCategoryId(@RequestParam(value="categoryId",required = true)Integer categoryId) throws Exception{
		
	
		List<TbCarParts> carPartsList = tbCarPartsService.getAllByCategory(categoryId);
		return new ResultBean<List<TbCarParts>> (carPartsList);
		
	}
	
	@RequestMapping(value = "/getCarBrand",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取车标数据",notes = "获取车标数据")
	public ResultBean<List<DictionarySystemBO>> queryBrand() throws Exception{
		return new ResultBean<List<DictionarySystemBO>>(dictionaryService.getAllCarBrand());
	}
	
	@RequestMapping(value = "/getCarSystem/{brandId}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据车标id获取车系数据",notes = "根据车标id获取车系数据")
	public ResultBean<List<DictionarySystemBO>> querySystem(@PathVariable("brandId")String brandId) throws Exception{
		
		return new ResultBean<List<DictionarySystemBO>>(dictionaryService.getCarSystem(brandId));
		
	}
	@RequestMapping(value = "/getCarModel/{systemId}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据车系id获取车型数据",notes = "根据车系id获取车型数据")
	public ResultBean<List<DictionaryBO>> queryModel(@PathVariable("systemId")String systemId) throws Exception{
		System.out.println("nrz_list_3"+dictionaryService.getCarModel(systemId));
		return new ResultBean<List<DictionaryBO>>(dictionaryService.getCarModel(systemId));
		
	}
	
	
	

}
