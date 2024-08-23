package task.time.tracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppSettings {

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    @Value("${app.frontend.forgot-password-url}")
    private String forgotPasswordUrl;

    public String getFrontendBaseUrl() {
        return frontendBaseUrl;
    }

    public String getForgotPasswordUrl() {
        return forgotPasswordUrl;
    }
}
