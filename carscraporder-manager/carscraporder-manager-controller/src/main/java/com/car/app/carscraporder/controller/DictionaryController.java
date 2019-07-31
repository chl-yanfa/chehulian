package com.car.app.carscraporder.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.bo.DictionaryBO;
import com.car.app.carscraporder.bo.DictionarySystemBO;
import com.car.app.carscraporder.pojo.TbAreas;
import com.car.app.carscraporder.pojo.TbCarBrand;
import com.car.app.carscraporder.pojo.TbCarModel;
import com.car.app.carscraporder.pojo.TbCarSystem;
import com.car.app.carscraporder.pojo.TbCityes;
import com.car.app.carscraporder.pojo.TbProvinces;
import com.car.app.carscraporder.service.TbAreasService;
import com.car.app.carscraporder.service.TbCarBrandService;
import com.car.app.carscraporder.service.TbCarModelService;
import com.car.app.carscraporder.service.TbCarSystemService;
import com.car.app.carscraporder.service.TbCityesService;
import com.car.app.carscraporder.service.TbProvincesService;
import com.car.app.carscraporder.util.StringUtils;
import com.car.app.common.bean.ResultBean;

@Controller
@RequestMapping(value="/dictionary")
@Api(value = "dictionary-API",tags={"提供外部调用的订单相关接口,(跳过登录校验,按照自定义鉴权逻辑)"} )
public class DictionaryController {
	
	@Autowired
	private TbProvincesService tbProvincesService;
	
	@Autowired
	private  TbCityesService tbCityesService;
	
	@Autowired
	private TbAreasService tbAreasService;
	
	@Autowired
	private TbCarBrandService tbCarBrandService;
	@Autowired
	private TbCarSystemService tbCarSystemService;
	@Autowired
	private TbCarModelService tbCarModelService;

	@RequestMapping(value="/getAllProvince",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询所有省份数据",notes = "查询所有省份数据")
	public ResultBean<List<DictionaryBO>> getAllProvince() throws Exception{
		
		
		List<TbProvinces> result =  tbProvincesService.queryAll();
		
		if(result!=null){
			List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
			for(TbProvinces t:result){
				DictionaryBO bo = new DictionaryBO();
				bo.setId(t.getProvinceid());
				bo.setName(t.getProvince());
				dictionaryBOs.add(bo);
			}
			
			return new ResultBean(dictionaryBOs);
			
		}else{
			return new ResultBean(result);
		}
		
		
		

	}
	
	
	

	@RequestMapping(value="/getAllCityByProvinceId/{provinceid}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取区域详情",notes = "根据id获取区域详情")
	public ResultBean<List<DictionaryBO>> getAllAreaByCityId(@PathVariable("provinceid")String provinceid) throws Exception{
		TbCityes whereParam = new TbCityes();
		whereParam.setProvinceid(provinceid);
		List<TbCityes> result = tbCityesService.queryListByWhere(whereParam);
		if(result!=null){
			List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
			for(TbCityes t:result){
				DictionaryBO bo = new DictionaryBO();
				bo.setId(t.getCityid());
				bo.setName(t.getCity());
				dictionaryBOs.add(bo);
			}
			
			return new ResultBean<List<DictionaryBO>>(dictionaryBOs);
			
		}else{
			return new ResultBean(result);
		}

	}

	
	

	
	
	@RequestMapping(value="/getAllAreaByCityId/{cityid}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取区域详情",notes = "根据id获取区域详情")
	public ResultBean<List<DictionaryBO>> getAllCityByProvinceId(@PathVariable("cityid")String cityid) throws Exception{
		TbAreas whereParam = new TbAreas();
		whereParam.setCityid(cityid);
		List<TbAreas> result = tbAreasService.queryListByWhere(whereParam);
		if(result!=null){
			List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
			for(TbAreas t:result){
				DictionaryBO bo = new DictionaryBO();
				bo.setId(t.getAreaid());
				bo.setName(t.getArea());
				dictionaryBOs.add(bo);
			}
			return new ResultBean<List<DictionaryBO>>(dictionaryBOs);
			
		}else{
			return new ResultBean(result);
		}
		

		

	}
	@RequestMapping(value = "/getCarBrand",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取车标数据",notes = "获取车标数据")
	public ResultBean<List<DictionarySystemBO>> queryBrand() throws Exception{
		List<DictionarySystemBO> list = new ArrayList<DictionarySystemBO>();
		List<TbCarBrand> queryAll = tbCarBrandService.queryAll();
		if(queryAll!=null) {
			for(TbCarBrand brand:queryAll){
				DictionaryBO bo = new DictionaryBO();
				bo.setId(brand.getBrandId());
				bo.setName(brand.getBrandName());
//				String szm = ChineseInital.getFirstLetter(brand.getBrandName());
				String szm = ToFirstChar(brand.getBrandName());
				Boolean flag = true;
				if(list != null && list.size() >0) {
					for (int i = 0; i < list.size(); i++) {
						DictionarySystemBO dictionarySystemBO = list.get(i);
						if(StringUtils.equals(dictionarySystemBO.getType(), szm)) {
							dictionarySystemBO.getChildren().add(bo);
							flag = false;
						}
					}
				}
				if(flag) {
					List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
					dictionaryBOs.add(bo);
					DictionarySystemBO dsbo = new DictionarySystemBO();
					dsbo.setType(szm);
					dsbo.setChildren(dictionaryBOs);
					list.add(dsbo);
				}
			}
			Collections.sort(list,new Comparator<DictionarySystemBO>() {
				@Override
				public int compare(DictionarySystemBO o1, DictionarySystemBO o2) {
					return o1.getType().compareTo(o2.getType());
				}
				
			});


			return new ResultBean<List<DictionarySystemBO>>(list);
		}
		return new ResultBean<List<DictionarySystemBO>>();
	}
	
	@RequestMapping(value = "/getCarSystem/{brandId}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据车标id获取车系数据",notes = "根据车标id获取车系数据")
	public ResultBean<List<DictionarySystemBO>> querySystem(@PathVariable("brandId")String brandId) throws Exception{
		List<DictionarySystemBO> list = new ArrayList<DictionarySystemBO>();
		List<String> categoryList = tbCarModelService.getSystemCategory(brandId);
		for (String category : categoryList) {
			DictionarySystemBO dsbo = new DictionarySystemBO();
			dsbo.setType(category);
			TbCarSystem record = new TbCarSystem();
			record.setBrandId(brandId);
			record.setSystemCategory(category);
			record.setIsDelete(0);
			List<TbCarSystem> queryListByWhere = tbCarSystemService.queryListByWhere(record);
			if(queryListByWhere!=null) {
				List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
				for(TbCarSystem system:queryListByWhere){
					DictionaryBO bo = new DictionaryBO();
					bo.setId(system.getSystemId());
					bo.setName(system.getSystemName());
					dictionaryBOs.add(bo);
				}
				dsbo.setChildren(dictionaryBOs);
			}
			list.add(dsbo);
		}
		return new ResultBean<List<DictionarySystemBO>>(list);
		
	}
	@RequestMapping(value = "/getCarModel/{systemId}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据车系id获取车型数据",notes = "根据车系id获取车型数据")
	public ResultBean<List<DictionaryBO>> queryModel(@PathVariable("systemId")String systemId) throws Exception{
		
		TbCarModel record = new TbCarModel();
		record.setSystemId(systemId);
		record.setIsDelete(0);
		List<TbCarModel> queryListByWhere = tbCarModelService.queryListByWhere(record);
		if(queryListByWhere!=null) {
			List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
			for(TbCarModel model:queryListByWhere){
				DictionaryBO bo = new DictionaryBO();
				bo.setId(model.getModelId());
				bo.setName(model.getModelName());
				dictionaryBOs.add(bo);
			}			
			return new ResultBean<List<DictionaryBO>>(dictionaryBOs);
		}
		return new ResultBean<List<DictionaryBO>>();
		
	}
	private static String ToFirstChar(String chinese) {
		String pinyinStr = "";
		char newChar = chinese.toCharArray()[0];
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		if (newChar > 128) {
			try {
				pinyinStr = PinyinHelper.toHanyuPinyinStringArray(newChar, defaultFormat)[0].charAt(0)+"";
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		} else {
			pinyinStr = newChar+"";
		}			
		return pinyinStr.toUpperCase();
	}
	
}
