package pegas.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class AspectForAnnotation {

    @Pointcut("@annotation(pegas.aspect.TrackUserAction)")
    public void isTrackUserAction() {
    }

    @Around("isTrackUserAction()")
    public void addLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        long timeStart = LocalDateTime.now().getNano();
        Object proceed = joinPoint.proceed();
        long timeFinish = LocalDateTime.now().getNano();
        log.info("Method " +joinPoint.getSignature().getName() + "was executed for " +
                ((timeFinish - timeStart)/1000000) + " milliseconds");
    }
}
