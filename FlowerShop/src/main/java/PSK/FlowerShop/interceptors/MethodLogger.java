package PSK.FlowerShop.interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
@Scope(scopeName = "Application")
public class MethodLogger {
    private String logFilePath = null;

    @Around("execution(* PSK.FlowerShop.security.JwtFilter..*.*(..)) && execution(* PSK.FlowerShop..*.*(..))")
//    @Around("!execution(* PSK.FlowerShop.controller..*.*(..)) && execution(* PSK.FlowerShop.repository..*.*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        System.out.println("WE HERE 1 path:"+logFilePath);
        if (logFilePath == null) {
            System.out.println("WE HERE 2 path:"+logFilePath);
            logFilePath = createLogFilePath();
            createNewLogFile(logFilePath);
        }

        if(!className.equals("MethodLogger") && !methodName.equals("logToFile")) {
            System.out.println("WE HERE 3 path:"+logFilePath);
            logToFile(this.logFilePath, className, methodName);
        }

        return joinPoint.proceed();
    }

    // Create new log file each day
    private String createLogFilePath() {
        String projectRoot = System.getProperty("user.dir");
        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.format(DateTimeFormatter.ISO_DATE);
        return projectRoot + "/logs" + "/log_" + dateString + ".log";
    }

    private void createNewLogFile(String logFilePath) {
        try {
            Path path = Path.of(logFilePath);
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logToFile(String logFilePath, String className, String methodName) {
        String logMessage = String.format("[%s] Class: %s, Method: %s%n",
                LocalDateTime.now(), className, methodName);

        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            writer.write(logMessage);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
