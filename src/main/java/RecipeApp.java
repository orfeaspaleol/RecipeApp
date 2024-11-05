import java.io.File;
import java.io.IOException;

public class RecipeApp {
    public static void main(String[] args) {
        if (args.length == 1) {
            String recipeFile = args[0];
            try {
                Recipe recipe = RecipeParser.parse(new File(recipeFile));
                recipe.display();
            } catch (IOException e) {
                System.out.println("Error reading recipe file: " + e.getMessage());
            }
        } else if (args.length > 1 && args[0].equals("-list")) {
            ShoppingList shoppingList = new ShoppingList();
            for (int i = 1; i < args.length; i++) {
                String recipeFile = args[i];
                try {
                    Recipe recipe = RecipeParser.parse(new File(recipeFile));
                    shoppingList.addRecipe(recipe);
                } catch (IOException e) {
                    System.out.println("Error reading recipe file: " + e.getMessage());
                }
            }
            shoppingList.display();
        } else {
            System.out.println("Usage: java -jar recipes.jar <recipeFile> or java -jar recipes.jar -list <recipeFiles>");
        }
    }
}
