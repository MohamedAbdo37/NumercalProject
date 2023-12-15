package com.CSED26.Numercal.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestNumercalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.from(NumercalProjectApplication::main).with(TestNumercalProjectApplication.class).run(args);
	}

}
