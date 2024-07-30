package scanner.dispetchers.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scanner.defenitionSteps.CarMaxScraper;

@Slf4j
@Component
public class LoginToCarMaxPageDisp {
    private final CarMaxScraper cmx;

    @Autowired
    public LoginToCarMaxPageDisp(CarMaxScraper cmx) {
        this.cmx = cmx;
    }

    public void run(String username, String password) {
        cmx.openCarMaxActionPage();
        cmx.signInCarMax(username, password);
    }
}
