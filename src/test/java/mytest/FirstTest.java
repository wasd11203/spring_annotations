package mytest;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.controller.HelloController;
public class FirstTest {

	@Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("conf/spring-context.xml");
		HelloController hello = ac.getBean("helloController", HelloController.class);
		System.out.println(hello.hello());
	}
	
}
