package club.anims.jnoted.data.dtos;

import club.anims.jnoted.data.models.Token;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link Token}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDto implements Serializable {
    private String token;

    public TokenDto(Token token) {
        this.token = token.getToken();
    }
}