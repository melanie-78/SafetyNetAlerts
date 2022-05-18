package com.openclassrooms.SafetyNetAlerts;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafetyNetAlertsApplication{

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Bean
	public HttpTraceRepository getHttpTraceRepository(){
		return new InMemoryHttpTraceRepository();
	}

}

