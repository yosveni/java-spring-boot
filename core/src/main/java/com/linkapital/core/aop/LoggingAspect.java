package com.linkapital.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
    }

    @Pointcut("within(com.linkapital.core..*)" +
            " || within(com.linkapital.core.services..*)" +
            " || within(com.linkapital.core.resources..*)")
    public void applicationPackagePointcut() {
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}. Details: {}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause().getMessage() : "''", e.getMessage());
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = joinPoint.getSignature();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = "-";

        if (authentication != null) {
            var userDetails = authentication.getPrincipal();
            if (userDetails instanceof UserDetails details)
                user = details.getUsername();
        }

        if (log.isDebugEnabled())
            log.debug("User:{} Enter: {}.{}() with argument[s] = {}", user, signature.getDeclaringTypeName(),
                    signature.getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            var proceed = joinPoint.proceed();
            if (log.isDebugEnabled() || log.isInfoEnabled())
                log.debug("User:{} Exit: {}.{}() with result = {}", user, signature.getDeclaringTypeName(),
                        signature.getName(), mapLogResult(proceed, signature));

            return proceed;
        } catch (IllegalArgumentException e) {
            log.error("User:{} Illegal argument: {} in {}.{}()", user, Arrays.toString(joinPoint.getArgs()),
                    signature.getDeclaringTypeName(), signature.getName());
            throw e;
        }
    }

    /*
     * Map data that is hard to understand, or unusable into a clearer way.
     */
    private Object mapLogResult(Object proceed, Signature signature) {
        /*avoid logging Files*/
        if (proceed instanceof byte[])
            return "File";

        if (signature.getDeclaringTypeName().contains("StorageResource") && proceed instanceof ResponseEntity)
            return ((ResponseEntity<?>) proceed).getStatusCode() + " File";

        return proceed;
    }

}
