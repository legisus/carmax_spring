package scanner.credentials;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import utils_api.ConfigConstant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
@Slf4j
public class DbCredentialsUtils {

    @Getter
    private  String jdbcUrl;
    @Getter
    private String username;
    @Getter
    private String password;

    private Properties props = new Properties();

     public void loadCredentials() {

            try (FileInputStream fis = new FileInputStream(ConfigConstant.URI_PROPERTIES_FILE_DB)) {
                props.load(fis);
                username = props.getProperty("username.db");
                password = props.getProperty("password.db");
                jdbcUrl = props.getProperty("jdbcUrl");
            } catch (FileNotFoundException fnfe) {
                log.error("Properties file not found: " + fnfe.getMessage());
            } catch (IOException io) {
                log.error("Error reading from properties file: " + io.getMessage());
            }
        }

}
