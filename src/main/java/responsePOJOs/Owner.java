package responsePOJOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Owner {
    @JsonProperty("name")
    public String name;

    @JsonProperty("email")
    public String email;

    @JsonProperty("createdAt")
    public Date createdAt;

    @JsonProperty("updatedAt")
    public Date updatedAt;
}
