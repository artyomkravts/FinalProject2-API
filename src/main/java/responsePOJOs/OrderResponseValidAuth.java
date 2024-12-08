package responsePOJOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class OrderResponseValidAuth {
    @JsonProperty("success")
    public Boolean success;

    @JsonProperty("name")
    public String name;

    @JsonProperty("order")
    public OrderListValidAuth order;

    @Data
    public static class OrderListValidAuth {
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

        @JsonProperty("createdAt")
        public Date createdAt;

        @JsonProperty("updatedAt")
        public Date updatedAt;

        @JsonProperty("number")
        public Integer number;

        @JsonProperty("price")
        public Integer price;

        @Data
        public static class Ingredient {
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
    }
}
