package api.pietunes.snoopy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
public class FeignConf2 {

	@Bean
	Decoder decoder(ObjectMapper objectMapper) {
		return new JacksonDecoder(objectMapper);
	}

	@Bean
	Encoder encoder(ObjectMapper objectMapper) {
		return new JacksonEncoder(objectMapper);
	}

	// @Bean
	// ObjectMapper objectMapper() {
	// 	return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	// }
}
