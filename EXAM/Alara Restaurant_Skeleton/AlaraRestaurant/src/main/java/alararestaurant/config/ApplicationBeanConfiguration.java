package alararestaurant.config;

import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtil fileUtil() {
        // TODO : Implement me
        return null;
//        return new FileUtilImpl();
    }

    @Bean
    public Gson gson() {
        // TODO : Implement me
        return null;
//        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    @Bean
    public ValidationUtil validationUtil() {
        // TODO : Implement me
        return null;
//        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        // TODO : Implement me
        return null;
//        return new ModelMapper();
    }
}
