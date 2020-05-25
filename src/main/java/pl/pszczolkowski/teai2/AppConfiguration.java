package pl.pszczolkowski.teai2;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://vehicles-angular.herokuapp.com")
                .allowedOrigins("https://vehicles-angular.herokuapp.com/vehicles")
                .allowedMethods("GET", "POST", "PUT");
    }
}
