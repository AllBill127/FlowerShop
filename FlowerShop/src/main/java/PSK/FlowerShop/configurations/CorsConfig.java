package PSK.FlowerShop.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${local.url}")
    private String browserUrlLocal;
    @Value("${deployed.url}")
    private String browserUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(browserUrl, browserUrlLocal)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");
    }
}
