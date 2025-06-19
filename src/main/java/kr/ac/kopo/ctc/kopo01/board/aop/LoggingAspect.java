package kr.ac.kopo.ctc.kopo01.board.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 📌 LoggingAspect 클래스
 *
 * 이 클래스는 AOP를 활용해 서비스 계층의 모든 메서드에 대해 실행 전후, 성공 반환, 예외 발생, 실행 시간 측정 등의 로깅을 수행합니다.
 * 모든 로깅은 패키지: kr.ac.kopo.ctc.kopo01.board.service 하위의 클래스와 메서드를 대상으로 합니다.
 */
@Aspect  // 이 클래스가 AOP 관점(Aspect)을 정의하고 있음을 의미
@Component // 스프링 빈으로 등록되도록 지정
public class LoggingAspect {

    /**
     * ✅ @Before
     * - 메서드 실행 직전에 실행됩니다.
     * - 주로 메서드 진입 로그, 파라미터 로깅 등에 사용됩니다.
     */
    @Before("execution(* kr.ac.kopo.ctc.kopo01.board.service.*.*(..))")
    public void oneBefore(JoinPoint joinPoint) {
        System.out.println("LoggingAspect.onBefore() 메소드 호출 : " + joinPoint.getSignature());
    }

    /**
     * ✅ @After
     * - 메서드 실행이 끝난 직후 (정상/예외 관계없이) 무조건 실행됩니다.
     */
    @After("execution(* kr.ac.kopo.ctc.kopo01.board.service.*.*(..))")
    public void onAfter(JoinPoint joinPoint) {
        System.out.println("LoggingAspect.onAfter() 메소드 호출 : " + joinPoint.getSignature());
    }

    /**
     * ✅ @AfterReturning
     * - 메서드가 예외 없이 정상적으로 반환되었을 때 실행됩니다.
     * - 반환된 결과를 로그로 출력하거나, 후처리에 활용할 수 있습니다.
     */
    @AfterReturning(
            pointcut = "execution(* kr.ac.kopo.ctc.kopo01.board.service.*.*(..))",
            returning = "result"
    )
    public void onAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("LoggingAspect.onAfterReturning() 메소드 호출 : " + joinPoint.getSignature());
        System.out.println("성공 리턴값 : " + result);
    }

    /**
     * ✅ @AfterThrowing
     * - 메서드에서 예외가 발생했을 때 실행됩니다.
     * - 예외 로그 기록 또는 알림 전송 등에 활용됩니다.
     */
    @AfterThrowing(
            pointcut = "execution(* kr.ac.kopo.ctc.kopo01.board.service.*.*(..))",
            throwing = "exception"
    )
    public void onAfterThrowing(JoinPoint joinPoint, Exception exception) {
        System.out.println("LoggingAspect.onAfterThrowing() 메소드 호출 : " + joinPoint.getSignature());
        System.out.println("예외 발생 : " + exception.getMessage());
    }

    /**
     * ✅ @Around
     * - 메서드 실행 전과 후 모두 제어할 수 있는 가장 강력한 AOP 어노테이션입니다.
     * - 실행 시간 측정, 트랜잭션 처리, 권한 검사 등 다양한 목적으로 활용됩니다.
     */
    @Around("execution(* kr.ac.kopo.ctc.kopo01.board.service.*.*(..))")
    public Object onAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("LoggingAspect.onAround() Before 실행 : " + joinPoint.getSignature());

        long start = System.currentTimeMillis(); // ⏱ 시작 시간 측정
        Object result = joinPoint.proceed();     // 👉 실제 대상 메서드 실행
        long end = System.currentTimeMillis();   // ⏱ 종료 시간 측정

        System.out.println("LoggingAspect.onAround() After 실행 : " + joinPoint.getSignature());
        System.out.println("실행 시간 : " + (end - start) + "ms");

        return result; // 원래의 반환값 전달
    }
}
