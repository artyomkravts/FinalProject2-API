package responsePOJOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import requestPOJOs.Order;

@Data
public class OrderResponse {
    @JsonProperty("success")
    public Boolean success;

    @JsonProperty("name")
    public String name;

    @JsonProperty("order")
    public Order order;
}
