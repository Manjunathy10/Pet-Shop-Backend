package com.manjunath.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manjunath.responce.ApiResponce;

@RestController
public class HomeController {

//	@Autowired
//	private ApiResponce apiResponce;

	@GetMapping("/")
	public ApiResponce HomeControllerHandler() {
		ApiResponce apiResponce = new ApiResponce();
		apiResponce.setMessage("ApiResponce");
		return apiResponce;
	}
}
