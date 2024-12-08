package requestPOJOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private ArrayList<String> ingredients = new ArrayList<>();

    public void addIngredients(String ingredientId) {
        ingredients.add(ingredientId);
    }
}
