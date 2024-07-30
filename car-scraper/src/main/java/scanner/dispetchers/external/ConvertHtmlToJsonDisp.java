package scanner.dispetchers.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import scanner.utils.HtmlToJsonConverter;

@Slf4j
@Component
public class ConvertHtmlToJsonDisp {
    private final HtmlToJsonConverter converter;

    public ConvertHtmlToJsonDisp(HtmlToJsonConverter converter) {
        this.converter = converter;
    }

    public String convertHtmlFileToJsonFile(String inputFilePath, String outputFilePath) {
//        converter.convertFromTxtFileToJsonFile(inputFilePath, outputFilePath);
        converter.convertFromHtmlFileToJsonFilePreAuctionLines(inputFilePath, outputFilePath);
        return "File converted successfully";
    }
}
