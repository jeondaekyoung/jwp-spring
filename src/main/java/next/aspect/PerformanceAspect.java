package next.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Component
@Aspect
public class PerformanceAspect {
	private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class); 
	@Pointcut("within(next.service..*) || within(next.dao..*)")
	public void myPointCut(){}
	
	@Pointcut("within(next.Dao..*)")
	public void myPointCut2(){}
	
	@Around("myPointCut()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp)throws Throwable{ 
		StopWatch watch = new StopWatch();
		watch.start();
		
		Object retVal = pjp.proceed();
		
		watch.stop();
		log.debug("execution method : {} , execution time : {}",pjp.toShortString(),watch.getTotalTimeMillis()/1000F+"초");
		return retVal;
	}
	
	
	
}
