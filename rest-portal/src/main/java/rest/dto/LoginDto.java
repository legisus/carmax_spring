package rest.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
