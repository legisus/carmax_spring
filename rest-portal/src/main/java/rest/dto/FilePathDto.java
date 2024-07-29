package rest.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class FilePathDto {
    @NonNull
    private String inputFilePath;
    @NonNull
    private String outputFilePath;
}
