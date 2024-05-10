package utils_api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TreadUtils {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
