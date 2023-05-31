package PSK.FlowerShop.usecase;

import org.aspectj.lang.ProceedingJoinPoint;
import PSK.FlowerShop.service.EmailService;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Aspect
@Service

public class EmailServiceDecorator implements EmailService {

    private EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceDecorator.class);

    public EmailServiceDecorator(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Override
    public void sendEmail(String receiver, String subject, String text, String link) {

        String formattedText = "<p style=\"font-size: 16px; line-height: 1.5;\">" + text + "</p>";
        String button = "<a href=\"" + link + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none;\">" +
                "Click Here</a>";
        String emailContent = "<html>" +
                "<head><style>body { font-family: Arial, sans-serif; }</style></head>" +
                "<body>" +
                "<h2 style=\"font-family: Arial, sans-serif;\">" + subject + "</h2>" +
                formattedText +
                "<p style=\"font-size: 16px; line-height: 1.5;\">" + button + "</p>" +
                "</body>" +
                "</html>";
        emailService.sendEmail(receiver,subject,emailContent,link);
    }

    @Around("execution(* PSK.FlowerShop.service.EmailService.sendEmail(..))")
    public void decorateEmail(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String receiver = (String) args[0];
        String subject = (String) args[1];
        String text = (String) args[2];
        String link = (String) args[3];

        logger.info("Sent email content:");
        logger.info("Receiver: {}", receiver);
        logger.info("Subject: {}", subject);
        logger.info("Text: {}", text);
        logger.info("Link: {}", link);

        joinPoint.proceed();
    }
}
