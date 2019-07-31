package com.car.app.carscraporder.web.controller;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.TbAttachmentService;
import com.car.app.common.bean.ResultBean;


@Controller
@RequestMapping(value="/attachment")
@Api(value = "attachment-API",description = "附件相关")
public class TbAttachmentController {
	
	@Autowired
	private TbAttachmentService  tbAttachmentService;
	
	    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	    @ResponseBody
	    public ResultBean<Boolean> delete(@PathVariable("id")Integer id) throws Exception {
		 
	    	
	    	return new ResultBean<Boolean>(tbAttachmentService.delete(id));
	 }

}
