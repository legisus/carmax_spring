package scanner.dispetchers.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import scanner.defenitionSteps.MainheimScraper;

@Slf4j
@Component
public class LoginMmrDisp {
    private final MainheimScraper mmr;

    public LoginMmrDisp(MainheimScraper mmr) {
        this.mmr = mmr;
    }

    public void run(String username, String password) {
        mmr.openManheimLoginPage();
        mmr.signInManheim(username, password);
    }
}
