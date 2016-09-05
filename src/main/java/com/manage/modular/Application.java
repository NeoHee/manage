package com.manage.modular;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		//设置外部Sql文件地址－－－－－不过我没有设置
		//System.setProperty("spring.config.location","");
	}
	
	//拦截器
	 @Configuration
	  static class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	      public void addInterceptors(InterceptorRegistry registry) {
	          registry.addInterceptor(new HandlerInterceptorAdapter() {

	              @Override
	              public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	                                       Object handler) throws Exception {
	            	  request.setCharacterEncoding("UTF-8");
	            	  response.setCharacterEncoding("UTF-8");
	                  if(request.getSession().getAttribute("user") != null){
	                	  		return true;
	                  }else{
	                	  		return false;
	                  }
	              }
	          }).addPathPatterns("/*");
	      }
	  }
	 //设置Seesion超时
	 @Bean
	 public EmbeddedServletContainerCustomizer containerCustomizer(){
	        return new EmbeddedServletContainerCustomizer() {
	            @Override
	            public void customize(ConfigurableEmbeddedServletContainer container) {
	                 container.setSessionTimeout(1800);//单位为S
	           }
	     };
	 }
	 
}
