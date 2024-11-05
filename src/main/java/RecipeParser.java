import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeParser {
    public static Recipe parse(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        List<Ingredient> ingredients = new ArrayList<>();
        Set<String> utensils = new HashSet<>();
        List<Step> steps = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue; // Skip empty lines
            }

            String originalLine = line;  // Keep the line intact for step creation

            // Find and add all ingredients in the line
            while (line.contains("@")) {
                int startIdx = line.indexOf("@");
                int endIdx = line.indexOf("}", startIdx) + 1;
                if (endIdx <= startIdx) break;
                String ingredientStr = line.substring(startIdx, endIdx);
                ingredients.add(parseIngredient(ingredientStr));
                line = line.replace(ingredientStr, "");  // Remove ingredient text temporarily
            }

            // Find and add all utensils in the line
            while (line.contains("#")) {
                int startIdx = line.indexOf("#");
                int endIdx = line.indexOf(" ", startIdx);
                if (endIdx == -1) endIdx = line.length();
                String utensil = line.substring(startIdx + 1, endIdx).trim();
                utensils.add(utensil);
                line = line.replace("#" + utensil, "");  // Remove utensil text temporarily
            }

            // Find and add timed steps
            int timeInMinutes = 0;
            if (line.contains("~")) {
                int startIdx = line.indexOf("~{") + 2;
                int endIdx = line.indexOf("}", startIdx);
                if (endIdx > startIdx) {
                    String timeStr = line.substring(startIdx, endIdx).split("%")[0];
                    timeInMinutes = Integer.parseInt(timeStr);
                    line = line.replace("~{" + timeStr + "%minutes}", "");  // Remove time text temporarily
                }
            }
            steps.add(new Step(originalLine.trim(), timeInMinutes));  // Use original line for the step text
        }

        return new Recipe(ingredients, new ArrayList<>(utensils), steps);
    }

    private static Ingredient parseIngredient(String ingredientStr) {
        String[] parts = ingredientStr.split("\\{");
        String name = parts[0].substring(1).trim();

        // Check if quantity and unit are provided in the curly braces
        if (parts.length > 1 && !parts[1].isEmpty()) {
            String quantityPart = parts[1].replace("}", "").trim();

            if (!quantityPart.isEmpty()) {
                String[] quantityUnit = quantityPart.split("%");
                double quantity = Double.parseDouble(quantityUnit[0]);
                String unit = quantityUnit.length > 1 ? quantityUnit[1] : null;
                return new Ingredient(name, quantity, unit);
            }
        }

        // Default to quantity 1 and no unit if quantity is not specified
        return new Ingredient(name, 1, null);
    }
}
