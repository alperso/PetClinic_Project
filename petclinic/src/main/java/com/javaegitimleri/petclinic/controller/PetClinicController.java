package com.javaegitimleri.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Controller, Spring Framework'de kullanılan bir anotasyondur ve temel olarak
 * bir sınıfı web katmanında bir kontrolcü olarak tanımlamak için kullanılır.
 * MVC (Model-View-Controller) tasarım deseninde, @Controller bir Controller
 * bileşenini temsil eder.
 */

/**
 * @Controller anotosyonu sayesinde Spring Container bu sınıftan bir Controller
 *             Bean yaratacaktır. Gelen web isteklerini bu ilgili metotlarla
 *             eslestirmeye calistiracaktir.
 */

@Controller
public class PetClinicController {

	/**
	 * @ResponseBody anotosyonu ekliyoruz. Eger bu anotosyonu olmaz ise web
	 *               requestlerini handle edecek ilgili handle metotlarını dispatch
	 *               eden SPRING in dispatcher YAPISI CONTROL METODUNU DONDUGU
	 *               STRING ifadeyi burada view olarak render etmeye calisacaktir.
	 *               Bunun onune gecmek icin responseBody ekleyerek onune geciyoruz.
	 * @RequestMapping Spring Boot eslesmeyen web isteklerini statik resource olarak
	 *                 cozumler. Ayrıca yazmazsak POST,GET ikisinide aliyor. Butun
	 *                 eslesmeyen resourceler (/**) statik resource olarak ele
	 *                 alınır. HTML sayfaları, imajlar, CSS ve JS statik
	 *                 resourcelara örnektir.
	 *
	 *                 İstenirse bu ayar application.properties icerisindeki bir
	 *                 property tanımı ile degistirilebilir. Sadece belirli path
	 *                 uzerindekileri static resource olarak da adlandırabiliriz.
	 *
	 *                 spring.mvc.static-path-pattern=/static-content/**
	 *                 (application.properties icine yazilir.)
	 *
	 *                 Statik Web Resource'lar nerede aranir?
	 *
	 *                 classpath icerisinde sırayla
	 *
	 *                 1-META-INF/resource/ 2-resources/ 3-static/ 4-public/
	 *                 5-ServerContextroot dizini altında (src/main/webapp)
	 *
	 *
	 */

	@RequestMapping("/hello")
	@ResponseBody
	public String sayHello() {
		return "Hello PetClinic";
	}

	/*
	 * Eğer index.html dosyasını templates klasöründen yüklemek istiyorsanız,
	 *
	 * @ResponseBody kullanmamalısınız. Ayrıca, @RequestMapping yerine daha spesifik
	 * olan @GetMapping tercih edilebilir.
	 */
	@RequestMapping("/homePage")
	public String homePage() {
		return "index"; // src/main/resources/public/index.html'e gider
	}
	
	/*
	 * Spring security icin
	 * localhost:8080 ve localhost:8080/index yazidigimda index.jsp calissin diye
	 * 
	 * */
	@RequestMapping(value= {"/","/index.html"})
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

}
