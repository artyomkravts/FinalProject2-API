package responsePOJOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class OrderListValidAuth {
    @JsonProperty("ingredients")
    public ArrayList<Ingredient> ingredients;

    @JsonProperty("_id")
    public String _id;

    @JsonProperty("owner")
    public Owner owner;

    @JsonProperty("status")
    public String status;

    @JsonProperty("name")
    public String name;

    @JsonProperty("created_at")
    public Date createdAt;

    @JsonProperty("updated_at")
    public Date updatedAt;

    @JsonProperty("number")
    public Integer number;

    @JsonProperty("price")
    public Integer price;
}
