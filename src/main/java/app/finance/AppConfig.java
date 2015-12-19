package app.finance;

import app.finance.config.DispatcherConfig;
import app.finance.config.ThymeleafConfig;
import app.finance.config.WebInitializerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Application configuration instead of web.xml config.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "app.finance")
@Import({ WebInitializerConfig.class, DispatcherConfig.class, ThymeleafConfig.class})
public class AppConfig
{
    // does nothing but scan and load
}