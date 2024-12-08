package responsePOJOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderResponseValidAuth {
    @JsonProperty("success")
    public Boolean success;

    @JsonProperty("name")
    public String name;

    @JsonProperty("order")
    public OrderListValidAuth order;
}
