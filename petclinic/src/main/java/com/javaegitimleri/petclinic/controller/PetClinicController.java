package com.javaegitimleri.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* @Controller, Spring Framework'de kullanılan bir anotasyondur ve temel olarak bir sınıfı web katmanında bir
* kontrolcü olarak tanımlamak için kullanılır.
* MVC (Model-View-Controller) tasarım deseninde, @Controller bir Controller bileşenini temsil eder.
* */

//@Controller anotosyonu sayesinde Spring Container bu sınıftan bir Controller Bean yaratacaktır.
//Gelen web isteklerini bu ilgili metotlarla eslestirmeye calistiracaktir.
@Controller
public class PetClinicController {
	
	//@ResponseBody anotosyonu ekliyoruz. Eger bu anotosyonu olmaz ise web requestlerini handle edecek ilgili
	//handle metotlarını dispatch eden SPRING in dispatcher YAPISI 
	//CONTROL METODUNU DONDUGU STRING iFADEYİ burada view olarak render etmeye calisacaktir. 
	//Bunun onune gecmek icin responseBody ekleyerek onune geciyoruz.
	//POST,GET ikisinide aliyor.
	
	@RequestMapping("/hello")
	@ResponseBody
	public String sayHello() {
		return "Hello PetClinic";
	}
	
	

}
