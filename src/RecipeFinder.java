import java.io.*;
import java.util.*;

public class RecipeFinder {

    // ============================================================
    // STRING / PATTERN MATCHING
    // Algorithm: Naive Pattern Matching
    // ============================================================

    public static boolean naivePatternMatch(String text, String pattern) {

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        int n = text.length();
        int m = pattern.length();

        // Empty pattern
        if (m == 0) {
            return true;
        }

        // Pattern is longer than text
        if (m > n) {
            return false;
        }

        // ========================================================
        // NAIVE PATTERN MATCHING
        // ========================================================

        for (int i = 0; i <= n - m; i++) {

            int j = 0;

            while (j < m &&
                    text.charAt(i + j) == pattern.charAt(j)) {

                j++;
            }

            if (j == m) {
                return true;
            }
        }

        return false;
    }

    // ============================================================
    // CHECK WHETHER A LINE IS A RECIPE HEADER
    // ============================================================

    public static boolean isRecipeHeader(String line) {

        if (line == null) {
            return false;
        }

        return line.trim().toLowerCase().startsWith("recipe:");
    }

    // ============================================================
    // GET RECIPE NAME SAFELY
    // ============================================================

    public static String getRecipeName(String firstLine) {

        if (firstLine == null) {
            return "";
        }

        firstLine = firstLine.trim();

        // Safety check
        if (!firstLine.toLowerCase().startsWith("recipe:")) {
            return "";
        }

        // Remove "Recipe:"
        String recipeName = firstLine.substring(7).trim();

        return recipeName;
    }

    // ============================================================
    // DISPLAY RECIPE
    // ============================================================

    public static void displayRecipe(String recipeText) {

        System.out.println();
        System.out.println("======================================");
        System.out.println("           RECIPE FOUND");
        System.out.println("======================================");
        System.out.println();

        System.out.println(recipeText);

        System.out.println("======================================");
        System.out.println();
    }

    // ============================================================
    // RECIPE SEARCH
    // ============================================================

    public static void searchRecipe(String fileName, String searchName) {

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            StringBuilder recipe = new StringBuilder();

            String line;

            // ====================================================
            // READ FILE LINE BY LINE
            // ====================================================

            while ((line = br.readLine()) != null) {

                // Remove BOM if it exists at the beginning
                if (line.length() > 0 && line.charAt(0) == '\uFEFF') {
                    line = line.substring(1);
                }

                // =================================================
                // NEW RECIPE FOUND
                // =================================================

                if (isRecipeHeader(line)) {

                    // ---------------------------------------------
                    // CHECK PREVIOUS RECIPE
                    // ---------------------------------------------

                    if (recipe.length() > 0) {

                        String recipeText = recipe.toString().trim();

                        if (!recipeText.isEmpty()) {

                            String[] lines = recipeText.split("\\R");

                            if (lines.length > 0) {

                                String firstLine = lines[0].trim();

                                String recipeName = getRecipeName(firstLine);

                                // -----------------------------
                                // SEARCH RECIPE NAME
                                // -----------------------------

                                if (!recipeName.isEmpty() &&
                                        naivePatternMatch(
                                                recipeName,
                                                searchName)) {

                                    displayRecipe(recipeText);

                                    found = true;

                                    return;
                                }
                            }
                        }

                        // Clear previous recipe
                        recipe.setLength(0);
                    }
                }

                // =================================================
                // ADD CURRENT LINE TO RECIPE
                // =================================================

                recipe.append(line).append(System.lineSeparator());
            }

            // ====================================================
            // CHECK LAST RECIPE
            // ====================================================

            if (!found && recipe.length() > 0) {

                String recipeText = recipe.toString().trim();

                if (!recipeText.isEmpty()) {

                    String[] lines = recipeText.split("\\R");

                    if (lines.length > 0) {

                        String firstLine = lines[0].trim();

                        String recipeName = getRecipeName(firstLine);

                        if (!recipeName.isEmpty() &&
                                naivePatternMatch(
                                        recipeName,
                                        searchName)) {

                            displayRecipe(recipeText);

                            found = true;
                        }
                    }
                }
            }

            // ====================================================
            // RECIPE NOT FOUND
            // ====================================================

            if (!found) {

                System.out.println();
                System.out.println("Recipe not found.");
                System.out.println(
                        "Please enter a valid recipe name.");
                System.out.println();
            }

        } catch (FileNotFoundException e) {

            System.out.println();
            System.out.println(
                    "Error: recipes.txt file not found.");

            System.out.println(
                    "Make sure recipes.txt is inside the data folder.");

        } catch (IOException e) {

            System.out.println();
            System.out.println(
                    "Error while reading the recipe file.");

            System.out.println(
                    "Please check your recipes.txt file.");
        }
    }

    // ============================================================
    // MAIN METHOD
    // ============================================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ========================================================
        // FILE LOCATION
        // ========================================================

        String fileName = "data" + File.separator + "recipes.txt";

        // ========================================================
        // APPLICATION TITLE
        // ========================================================

        System.out.println(
                "======================================");

        System.out.println(
                "             RECIPE FINDER");

        System.out.println(
                "======================================");

        System.out.println();

        // ========================================================
        // USER INPUT
        // ========================================================

        System.out.print("Enter recipe name: ");

        String searchName = sc.nextLine().trim();

        // ========================================================
        // INPUT VALIDATION
        // ========================================================

        if (searchName.isEmpty()) {

            System.out.println();
            System.out.println(
                    "Please enter a recipe name.");

        } else {

            // ====================================================
            // SEARCH RECIPE
            // ====================================================

            searchRecipe(
                    fileName,
                    searchName);
        }

        sc.close();
    }
}