package com.car.app.carscraporder.app.systemparameter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SystemParameter {

   public final static String TICKET= "appSessionId";

   public final static String FUNCTION_ORDER_CAR_WHOLE = "wholeCar";
   public final static String FUNCTION_ORDER_CAR_PARTS = "parts";

	
	public static final ObjectMapper MAPPER = new ObjectMapper();
}
