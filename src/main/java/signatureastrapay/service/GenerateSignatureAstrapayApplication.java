package signatureastrapay.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GenerateSignatureAstrapayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateSignatureAstrapayApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(){
		var factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(1000000);
		factory.setReadTimeout(1000000);
		return new RestTemplate(factory);
	}
}
