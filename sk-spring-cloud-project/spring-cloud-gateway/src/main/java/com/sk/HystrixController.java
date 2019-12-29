package com.sk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HystrixController {

	/**
	 * 服务异常或超时时进入
	 * @return
	 */
	@RequestMapping("/defaultfallback")
	public Map<String,String> defaultfallback(){
		Map<String,String> map = new HashMap<>();
		map.put("code","405");
		map.put("message","服务异常或超时，被gateway熔断");
		return map;
	}

}
