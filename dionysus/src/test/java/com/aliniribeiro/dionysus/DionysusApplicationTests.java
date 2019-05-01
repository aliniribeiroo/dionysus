package com.aliniribeiro.dionysus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DionysusApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("Teste");
	}

}
