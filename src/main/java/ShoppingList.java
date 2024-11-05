import java.util.HashMap;
import java.util.Map;

public class ShoppingList {
    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    public void addRecipe(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientMap.merge(ingredient.getName(), ingredient,
                    (existing, newIng) -> {
                        existing.addQuantity(newIng.getQuantity());
                        return existing;
                    });
        }
    }

    public void display() {
        System.out.println("Shopping List:");
        ingredientMap.values().forEach(ingredient -> System.out.println(" - " + ingredient));
    }
}
