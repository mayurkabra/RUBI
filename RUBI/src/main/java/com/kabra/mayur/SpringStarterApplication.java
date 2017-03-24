package com.kabra.mayur;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.kabra.mayur.cached.DataStore;

@SpringBootApplication
public class SpringStarterApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false"); 
		SpringApplication.run(SpringStarterApplication.class, args);
		
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				try {
					DataStore.lock.lock();
					DataStore.updatePredictionsMaps();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					DataStore.lock.unlock();
				}
				
			}
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, 0, 30*1000);
	}
	
	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
}
