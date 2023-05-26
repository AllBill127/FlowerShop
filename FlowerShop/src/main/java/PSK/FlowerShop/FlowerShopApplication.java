package PSK.FlowerShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy

public class FlowerShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerShopApplication.class, args);
	}

}
