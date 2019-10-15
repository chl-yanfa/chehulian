package com.car.app.carscraporder.attachment.service;
import com.car.app.carscraporder.vo.AttachmentVO;

public interface UploadService {
	
	
	 Integer  saveAttachmentBuniessData(AttachmentVO vo)throws Exception;
	
	 Integer  saveCarAttachmentBuniessData(String buniessid,AttachmentVO vo)throws Exception;

}
