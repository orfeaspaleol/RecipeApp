import java.util.List;

public class Recipe {
    private List<Ingredient> ingredients;
    private List<String> utensils;
    private List<Step> steps;

    public Recipe(List<Ingredient> ingredients, List<String> utensils, List<Step> steps) {
        this.ingredients = ingredients;
        this.utensils = utensils;
        this.steps = steps;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getTotalTime() {
        int totalTime = 0;
        for (Step step : steps) {
            totalTime += step.getTimeInMinutes();
        }
        return totalTime;
    }

    public void display() {
        System.out.println("Υλικά:");
        for (Ingredient ingredient : ingredients) {
            System.out.println(" - " + ingredient);
        }

        System.out.println("\nΣκεύη:");
        for (String utensil : utensils) {
            System.out.println(" - " + utensil);
        }

        System.out.println("\nΣυνολική ώρα: " + getTotalTime() + " minutes\n");

        System.out.println("Βήματα:");
        for (int i = 0; i < steps.size(); i++) {
            System.out.println((i + 1) + ". " + steps.get(i));
        }
    }
}
