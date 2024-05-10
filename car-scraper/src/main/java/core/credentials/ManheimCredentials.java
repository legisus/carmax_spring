package core.credentials;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import utils_api.ConfigConstant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ManheimCredentials {
    @Getter
    private String username;
    @Getter
    private String password;
    private final Properties props;

    public ManheimCredentials() {
        props = new Properties();
        loadCredentials();
    }

    private void loadCredentials() {

        try (InputStream is = new FileInputStream(ConfigConstant.URI_PROPERTIES_FILE_MANHEIM)) {
            if (is == null) {
                log.error("Properties file not found");
                throw new FileNotFoundException("manheim.properties not found in classpath");
            }
            props.load(is);
            username = props.getProperty("username");
            password = props.getProperty("password");
        } catch (IOException io) {
            log.error("Error reading from properties file: " + io.getMessage());
        }
    }
}
