package com.car.app.carscraporder.attachment.service;
import com.car.app.carscraporder.vo.AttachmentVO;

public interface UploadService {
	
	
	public Integer  saveAttachmentBuniessData(AttachmentVO vo)throws Exception;
	
	public Integer  saveCarAttachmentBuniessData(String buniessid,AttachmentVO vo)throws Exception;

}
