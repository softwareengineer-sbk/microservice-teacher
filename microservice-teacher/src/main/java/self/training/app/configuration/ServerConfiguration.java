package self.training.app.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Setter @Getter @ToString
@ConfigurationProperties(prefix = "microservice-teacher")
public class ServerConfiguration {

    private String msg;
    private String buildVersion;
    private Map<String, String> mailDetails;

}
