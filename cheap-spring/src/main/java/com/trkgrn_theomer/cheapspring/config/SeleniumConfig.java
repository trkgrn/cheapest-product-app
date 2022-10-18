package com.trkgrn_theomer.cheapspring.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public ChromeDriver driver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

}
