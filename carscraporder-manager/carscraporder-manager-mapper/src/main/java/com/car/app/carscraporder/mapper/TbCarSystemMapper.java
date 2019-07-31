package com.car.app.carscraporder.mapper;

import java.util.List;

import com.car.app.carscraporder.pojo.TbCarSystem;
import com.github.abel533.mapper.Mapper;

public interface TbCarSystemMapper extends Mapper<TbCarSystem> {
	List<String> getSystemCategory(String brandId);
}
