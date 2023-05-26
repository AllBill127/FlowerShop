package PSK.FlowerShop.interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
@ApplicationScope
public class MethodLogger {

    static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("Aspect Method Logger");

    private String logFilePath = null;

    @Around("!execution(* PSK.FlowerShop.security..*.*(..)) && execution(* PSK.FlowerShop..*.*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        if (logFilePath == null) {
            logFilePath = createLogFilePath();
            createNewLogFile(logFilePath);
            logServerStart(logFilePath);
        }

        if(!className.equals("MethodLogger") && !methodName.equals("logToFile")) {
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
        } catch (FileAlreadyExistsException e) {
            logger.info("Log file already exists. Continue appending");
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

    private void logServerStart(String logFilePath) {
        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            writer.write("\n============================\n" +
                    " Flower Shop Server Started" +
                    "\n============================\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
