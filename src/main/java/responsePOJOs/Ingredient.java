package responsePOJOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Ingredient {
    @JsonProperty("_id")
    public String _id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("type")
    public String type;

    @JsonProperty("proteins")
    public Integer proteins;

    @JsonProperty("fat")
    public Integer fat;

    @JsonProperty("carbohydrates")
    public Integer carbohydrates;

    @JsonProperty("calories")
    public Integer calories;

    @JsonProperty("price")
    public Integer price;

    @JsonProperty("image")
    public String image;

    @JsonProperty("image_mobile")
    public String image_mobile;

    @JsonProperty("image_large")
    public String image_large;

    @JsonProperty("__v")
    public Integer __v;
}
