package com.car.app.carscraporder.app.bean;



import com.car.app.carscraporder.bo.OrderAttachmentBO;

public class OrderAttachmentAppBO extends OrderAttachmentBO{
	


	
	


	private String carPictureName;
	
	private boolean isVerify;
	
	
	

	public OrderAttachmentAppBO(String url, String carPictureType,boolean isVerify) {
		super(url, carPictureType);
		this.isVerify = isVerify;
	}





	public OrderAttachmentAppBO(){
		
	}
	




	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	public String getCarPictureName() {
		
		switch(this.getCarPictureType()){
		     case "1":
			      this.carPictureName = "左前45度";
			      break;
		     case "2":
			      this.carPictureName = "正前方";
			      break;     
		     case "3":
			      this.carPictureName = "左前45度";
			      break; 
		     case "4":
			      this.carPictureName = "左后45度";
			      break;
		     case "5":
			      this.carPictureName = "正后方";
			      break;     
		     case "6":
			      this.carPictureName = "左后方45度";
			      break;  
		     case "7":
			      this.carPictureName = "车架号";
			      break;
		     case "8":
			      this.carPictureName = "发动机舱";
			      break;     
		     case "9":
			      this.carPictureName = "仪表台";
			      break;
		     case "10":
			      this.carPictureName = "行驶证";
			      break;
		     case "11":
			      this.carPictureName = "登记卡";
			      break;     
		     case "12":
			      this.carPictureName = "车主身份证后面";
			      break;  
		     case "13":
			      this.carPictureName = "车主身份证后面";
			      break; 
		     default:
		    	 this.carPictureName = "";
			      break; 
			      
		      
		}
		
		
		
		return carPictureName;
	}

	public void setCarPictureName(String carPictureName) {
		this.carPictureName = carPictureName;
	}

	

	
	
	
	
	
}
