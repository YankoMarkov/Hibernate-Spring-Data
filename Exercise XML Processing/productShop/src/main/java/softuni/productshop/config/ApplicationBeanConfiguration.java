package softuni.productshop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.productshop.util.ValidatorUtil;
import softuni.productshop.util.ValidatorUtilImpl;
import softuni.productshop.util.XMLParser;
import softuni.productshop.util.XMLParserImpl;

@Configuration
public class ApplicationBeanConfiguration {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public XMLParser xmlParser() {
		return new XMLParserImpl();
	}
	
	@Bean
	public ValidatorUtil validator() {
		return new ValidatorUtilImpl();
	}
}
