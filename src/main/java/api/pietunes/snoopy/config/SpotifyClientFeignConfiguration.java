package api.pietunes.snoopy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
public class SpotifyClientFeignConfiguration {

	@Bean
	Decoder decoder(ObjectMapper objectMapper) {
		return new JacksonDecoder(objectMapper);
	}

	@Bean
	Encoder encoder(ObjectMapper objectMapper) {
		return new JacksonEncoder(objectMapper);
	}
}
