package com.github.gustavopm1.gotcamel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class GotcamelApplicationTests {

	@Test
	public void contextLoads() {
	}

}
