package com.car.app.carscraporder.applet.controller;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.CarAttachmentService;
import com.car.app.common.bean.ResultBean;

@Controller
@RequestMapping(value="/carattachment")
@Api(value = "attachment-API",description="附件删除" )
public class CarAttachmentController {
	
	@Autowired
	private CarAttachmentService  carAttachmentService;
	
	  @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	    @ResponseBody
	    public ResultBean<Boolean> delete(@PathVariable("id")Integer id) throws Exception {
		  Boolean result=carAttachmentService.delete(id);
		  return new ResultBean<Boolean>(result);
	  }

}
