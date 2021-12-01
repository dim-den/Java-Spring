package movie.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(Loggable)")
    public void RestControllerCall() { }

    @Before("RestControllerCall()")
    public void beforeCallMethod(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");

    }

    @After("RestControllerCall()")
    public void afterCallAt(JoinPoint jp) {
        log.info("after " + jp.toString());
    }
}