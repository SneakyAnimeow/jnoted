package club.anims.jnoted.data.dtos;

import club.anims.jnoted.data.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link club.anims.jnoted.data.models.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {
    private String name;
    private LocalDateTime joinDate;

    public UserDto(User user) {
        this.name = user.getName();
        this.joinDate = user.getJoinDate();
    }
}