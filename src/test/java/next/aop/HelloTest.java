package next.aop;

import java.lang.reflect.Proxy;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import next.config.AppConfig;
import next.service.QnaService;

public class HelloTest {
	@Test
	public void hello() throws Exception{
		Hello hello = new HelloTarget();
		HelloUppercase proxy = new HelloUppercase(hello);
		System.out.println(proxy.sayHello("dae kyoung"));
	}
	@Test
	public void simpleProxy(){
		Hello hello = new HelloTarget();
		Hello proxiedHello = (Hello)Proxy.newProxyInstance
				(getClass().getClassLoader(),
						new Class[] {Hello.class},
						new UppercaseHandler(new HelloTarget()));
		System.out.println(proxiedHello.sayHello("test"));
		System.out.println(proxiedHello.sayHi("test"));

	}
	@Test
	public void proxyFactoryBean() { 
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		pfBean.addAdvice(new UppercaseAdvice());
		Hello proxiedHello = (Hello) pfBean.getObject(); 
		System.out.println(proxiedHello.sayHello("test"));
		System.out.println(proxiedHello.sayHi("test"));


	}
	

}
