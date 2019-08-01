package com.car.app.carscraporder.controller;

import com.car.app.carscraporder.bo.DictionaryBO;
import com.car.app.carscraporder.bo.DictionarySystemBO;
import com.car.app.carscraporder.pojo.*;
import com.car.app.carscraporder.service.ChlAutoLogosService;
import com.car.app.carscraporder.util.StringUtils;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.service.RedisService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(value="/dictionaryJy")
public class ChlAutoLogosController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ChlAutoLogosService chlAutoLogosService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping(value = "/getCarBrandJy",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取车标数据",notes = "获取车标数据")
    public ResultBean<List<DictionarySystemBO>> queryBrand() throws Exception{
        List<DictionarySystemBO> list = new ArrayList<DictionarySystemBO>();
        List<ChlAutoLogos> queryAll = chlAutoLogosService.selectChlAutoLogos();
        if(queryAll!=null) {
            for(ChlAutoLogos brand:queryAll){
                DictionaryBO bo = new DictionaryBO();
                bo.setId(brand.getAutoLogosId().toString());
                bo.setName(brand.getAutoLogosName());
                String szm = ToFirstChar(brand.getAutoLogosName());
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
    @RequestMapping(value = "/getCarSystemJy/{brandId}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据车标id获取车系数据",notes = "根据车标id获取车系数据")
    public ResultBean<List<DictionarySystemBO>> querySystem2(@PathVariable("brandId")String brandId) throws Exception {
        //声明key
        String key = "cbid_" + brandId;
        if (redisService.get(key) != null) {
            System.out.println("==========从缓存中获得数据=========");
            String jsonNode = redisService.get(key);
            System.out.println("jsonNode:"+jsonNode);
            List<DictionarySystemBO> data = MAPPER.readValue(jsonNode, new TypeReference<List<DictionarySystemBO>>() {});
            return new ResultBean<List<DictionarySystemBO>>(data);
        } else {
            System.out.println("==========从数据库中获得数据=========");
            List<DictionarySystemBO> list = new ArrayList<DictionarySystemBO>();
            List<String> categoryList = chlAutoLogosService.selectCategory(brandId);
            for (String category : categoryList) {
                DictionarySystemBO dsbo = new DictionarySystemBO();
                dsbo.setType(category);
                TbCarSystem record = new TbCarSystem();
                record.setBrandId(brandId);
                record.setSystemCategory(category);
                record.setIsDelete(0);
                List<ChlCarModelSeries> queryListByWhere = chlAutoLogosService.selectChlCarModelSeries(record);
                /*   List<TbCarSystem> queryListByWhere = tbCarSystemService.queryListByWhere(record);*/
                if (queryListByWhere != null) {
                    List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
                    for (ChlCarModelSeries system : queryListByWhere) {
                        DictionaryBO bo = new DictionaryBO();
                        bo.setId(system.getSid().toString());
                        bo.setName(system.getVehicleSystemName());
                        dictionaryBOs.add(bo);
                    }
                    dsbo.setChildren(dictionaryBOs);
                }
                list.add(dsbo);

            }
            JSONArray jsonArray = JSONArray.fromObject(list);
            System.out.println(jsonArray.toString());
            // 写入缓存
            redisService.set(key,jsonArray.toString(),86400);

            return new ResultBean<List<DictionarySystemBO>>(list);





        /*List<DictionarySystemBO> list = new ArrayList<DictionarySystemBO>();
        List<String> categoryList = chlAutoLogosService.selectCategory(brandId);
        for (String category : categoryList) {
            DictionarySystemBO dsbo = new DictionarySystemBO();
            dsbo.setType(category);
            TbCarSystem record = new TbCarSystem();
            record.setBrandId(brandId);
            record.setSystemCategory(category);
            record.setIsDelete(0);
            List<ChlCarModelSeries>  queryListByWhere = chlAutoLogosService.selectChlCarModelSeries(record);
         *//*   List<TbCarSystem> queryListByWhere = tbCarSystemService.queryListByWhere(record);*//*
            if(queryListByWhere!=null) {
                List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
                for(ChlCarModelSeries system:queryListByWhere){
                    DictionaryBO bo = new DictionaryBO();
                    bo.setId(system.getSid().toString());
                    bo.setName(system.getVehicleSystemName());
                    dictionaryBOs.add(bo);
                }
                dsbo.setChildren(dictionaryBOs);
            }
            list.add(dsbo);
        }
        return new ResultBean<List<DictionarySystemBO>>(list);*/

        }
    }
    @RequestMapping(value = "/getCarModelJy/{systemId}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据车系id获取车型数据",notes = "根据车系id获取车型数据")
    public ResultBean<List<DictionaryBO>> queryModel2(@PathVariable("systemId")String systemId) throws Exception{
        //声明key
        String key = "cxid_" + systemId;
        if (redisService.get(key) != null) {
            System.out.println("==========从缓存中获得数据=========");
            String jsonNode = redisService.get(key);
            System.out.println("jsonNode:"+jsonNode);
            List<DictionaryBO> data = MAPPER.readValue(jsonNode, new TypeReference<List<DictionaryBO>>() {});
            return new ResultBean<List<DictionaryBO>>(data);
        }else{
            System.out.println("==========从数据库中获得数据=========");
            List<ChlCarModel> queryListByWhere = chlAutoLogosService.selectCarModelSerid(Integer.parseInt(systemId));
            if(queryListByWhere!=null) {
                List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
                for(ChlCarModel model:queryListByWhere){
                    DictionaryBO bo = new DictionaryBO();
                    bo.setId(model.getId().toString());
                    bo.setName(model.getCalled());
                    if(model.getPurchasePrice()!=null){
                        String money= getTenThousandOfANumber(model.getPurchasePrice().intValue());
                        bo.setMoney(money);
                    }
                    dictionaryBOs.add(bo);
                }
                JSONArray jsonArray = JSONArray.fromObject(dictionaryBOs);
                System.out.println(jsonArray.toString());
                // 写入缓存
                redisService.set(key,jsonArray.toString(),86400);
                return new ResultBean<List<DictionaryBO>>(dictionaryBOs);
            }
            return new ResultBean<List<DictionaryBO>>();
        }

        /*List<ChlCarModel> queryListByWhere = chlAutoLogosService.selectCarModelSerid(Integer.parseInt(systemId));
        if(queryListByWhere!=null) {
            List<DictionaryBO> dictionaryBOs = new ArrayList<DictionaryBO>();
            for(ChlCarModel model:queryListByWhere){
                DictionaryBO bo = new DictionaryBO();
                bo.setId(model.getId().toString());
                bo.setName(model.getCalled());
                if(model.getPurchasePrice()!=null){
                   String money= getTenThousandOfANumber(model.getPurchasePrice().intValue());
                    bo.setMoney(money);
                }
                dictionaryBOs.add(bo);
            }
            return new ResultBean<List<DictionaryBO>>(dictionaryBOs);
        }
        return new ResultBean<List<DictionaryBO>>();*/

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
    public static String getTenThousandOfANumber(Integer num) {
        if (num < 10000) {
            return String.valueOf(num);
        }
        String numStr = new DecimalFormat("#.00").format(num / 10000d);
        String[] ss = numStr.split("\\.");
        if ("00".equals(ss[1])) {
            return ss[0] + "万";
        } else if ('0' == (ss[1].charAt(1))) {
            return ss[0] + "." + ss[1].charAt(0) + "万";
        } else {
            return numStr + "万";
        }
    }
}
