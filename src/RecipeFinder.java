import java.io.*;
import java.util.*;

public class RecipeFinder {

    // ============================================================
    // 1. NAIVE PATTERN MATCHING
    // ============================================================

    public static boolean naivePatternMatch(String text, String pattern) {

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        int n = text.length();
        int m = pattern.length();

        if (m == 0) {
            return true;
        }

        if (m > n) {
            return false;
        }

        // Naive Pattern Matching
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
    // 2. CHECK RECIPE HEADER
    // ============================================================

    public static boolean isRecipeHeader(String line) {

        if (line == null) {
            return false;
        }

        return line.trim()
                .toLowerCase()
                .startsWith("recipe:");
    }


    // ============================================================
    // 3. GET RECIPE NAME
    // ============================================================

    public static String getRecipeName(String firstLine) {

        if (firstLine == null) {
            return "";
        }

        firstLine = firstLine.trim();

        if (!firstLine.toLowerCase().startsWith("recipe:")) {
            return "";
        }

        return firstLine.substring(7).trim();
    }


    // ============================================================
    // 4. DISPLAY COMPLETE RECIPE
    // ============================================================

    public static void displayRecipe(String recipeText) {

        System.out.println();
        System.out.println("======================================");
        System.out.println("             RECIPE FOUND");
        System.out.println("======================================");
        System.out.println();

        System.out.println(recipeText);

        System.out.println("======================================");
        System.out.println();
    }


    // ============================================================
    // 5. LEVENSHTEIN EDIT DISTANCE
    // Used for Fuzzy Search
    // ============================================================

    public static int levenshteinDistance(String a, String b) {

        a = a.toLowerCase();
        b = b.toLowerCase();

        int n = a.length();
        int m = b.length();

        int[][] dp = new int[n + 1][m + 1];

        // Initialize first column
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        // Initialize first row
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        // Fill DP table
        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= m; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    dp[i][j] = dp[i - 1][j - 1];

                } else {

                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j],
                            Math.min(
                                    dp[i][j - 1],
                                    dp[i - 1][j - 1]
                            )
                    );
                }
            }
        }

        return dp[n][m];
    }


    // ============================================================
    // 6. SIMILARITY PERCENTAGE
    // Used by Fuzzy Search
    // ============================================================

    public static double similarity(String a, String b) {

        int distance = levenshteinDistance(a, b);

        int maxLength =
                Math.max(a.length(), b.length());

        if (maxLength == 0) {
            return 100.0;
        }

        return (1.0 -
                (double) distance / maxLength) * 100;
    }


    // ============================================================
    // 7. FUZZY SEARCH
    // ============================================================

    public static void fuzzySearchRecipe(
            String fileName,
            String searchName) {

        String bestRecipe = "";
        String bestRecipeName = "";
        double bestScore = 0;

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(fileName))) {

            StringBuilder recipe =
                    new StringBuilder();

            String line;

            // Read file line by line
            while ((line = br.readLine()) != null) {

                // Remove BOM if present
                if (line.length() > 0 &&
                        line.charAt(0) == '\uFEFF') {

                    line = line.substring(1);
                }

                // New recipe found
                if (isRecipeHeader(line)) {

                    // Process previous recipe
                    if (recipe.length() > 0) {

                        String recipeText =
                                recipe.toString().trim();

                        if (!recipeText.isEmpty()) {

                            String[] lines =
                                    recipeText.split("\\R");

                            if (lines.length > 0) {

                                String recipeName =
                                        getRecipeName(
                                                lines[0].trim()
                                        );

                                if (!recipeName.isEmpty()) {

                                    double score =
                                            similarity(
                                                    recipeName,
                                                    searchName
                                            );

                                    if (score > bestScore) {

                                        bestScore = score;
                                        bestRecipe =
                                                recipeText;
                                        bestRecipeName =
                                                recipeName;
                                    }
                                }
                            }
                        }

                        // Clear previous recipe
                        recipe.setLength(0);
                    }
                }

                // Add current line
                recipe.append(line)
                        .append(System.lineSeparator());
            }


            // ====================================================
            // CHECK LAST RECIPE
            // ====================================================

            if (recipe.length() > 0) {

                String recipeText =
                        recipe.toString().trim();

                if (!recipeText.isEmpty()) {

                    String[] lines =
                            recipeText.split("\\R");

                    if (lines.length > 0) {

                        String recipeName =
                                getRecipeName(
                                        lines[0].trim()
                                );

                        if (!recipeName.isEmpty()) {

                            double score =
                                    similarity(
                                            recipeName,
                                            searchName
                                    );

                            if (score > bestScore) {

                                bestScore = score;
                                bestRecipe =
                                        recipeText;
                                bestRecipeName =
                                        recipeName;
                            }
                        }
                    }
                }
            }


            // ====================================================
            // DISPLAY FUZZY RESULT
            // ====================================================

            if (bestScore >= 70) {

                System.out.println();
                System.out.println("======================================");
                System.out.println("          FUZZY MATCH FOUND");
                System.out.println("======================================");
                System.out.println();

                System.out.println(
                        "Closest Recipe: "
                                + bestRecipeName
                );

                System.out.printf(
                        "Similarity: %.2f%%%n",
                        bestScore
                );

                System.out.println();

                // Display complete recipe
                displayRecipe(bestRecipe);

            } else {

                System.out.println();
                System.out.println("Recipe not found.");
                System.out.println();
            }

        } catch (FileNotFoundException e) {

            System.out.println();
            System.out.println(
                    "Error: recipes.txt file not found."
            );

            System.out.println(
                    "Make sure recipes.txt is inside the data folder."
            );

        } catch (IOException e) {

            System.out.println();
            System.out.println(
                    "Error while reading the recipe file."
            );
        }
    }


    // ============================================================
    // 8. PATTERN MATCHING RECIPE SEARCH
    // ============================================================

    public static void searchRecipe(
            String fileName,
            String searchName) {

        boolean found = false;

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(fileName))) {

            StringBuilder recipe =
                    new StringBuilder();

            String line;

            // Read file line by line
            while ((line = br.readLine()) != null) {

                // Remove BOM if present
                if (line.length() > 0 &&
                        line.charAt(0) == '\uFEFF') {

                    line = line.substring(1);
                }

                // New recipe found
                if (isRecipeHeader(line)) {

                    // Check previous recipe
                    if (recipe.length() > 0) {

                        String recipeText =
                                recipe.toString().trim();

                        if (!recipeText.isEmpty()) {

                            String[] lines =
                                    recipeText.split("\\R");

                            if (lines.length > 0) {

                                String recipeName =
                                        getRecipeName(
                                                lines[0].trim()
                                        );

                                // Naive Pattern Matching
                                if (!recipeName.isEmpty() &&
                                        naivePatternMatch(
                                                recipeName,
                                                searchName)) {

                                    displayRecipe(
                                            recipeText
                                    );

                                    found = true;

                                    return;
                                }
                            }
                        }

                        recipe.setLength(0);
                    }
                }

                // Add current line
                recipe.append(line)
                        .append(System.lineSeparator());
            }


            // ====================================================
            // CHECK LAST RECIPE
            // ====================================================

            if (!found && recipe.length() > 0) {

                String recipeText =
                        recipe.toString().trim();

                if (!recipeText.isEmpty()) {

                    String[] lines =
                            recipeText.split("\\R");

                    if (lines.length > 0) {

                        String recipeName =
                                getRecipeName(
                                        lines[0].trim()
                                );

                        if (!recipeName.isEmpty() &&
                                naivePatternMatch(
                                        recipeName,
                                        searchName)) {

                            displayRecipe(
                                    recipeText
                            );

                            found = true;
                        }
                    }
                }
            }


            // Recipe not found
            if (!found) {

                System.out.println();
                System.out.println(
                        "Recipe not found."
                );

                System.out.println(
                        "Please enter a valid recipe name."
                );

                System.out.println();
            }

        } catch (FileNotFoundException e) {

            System.out.println();
            System.out.println(
                    "Error: recipes.txt file not found."
            );

            System.out.println(
                    "Make sure recipes.txt is inside the data folder."
            );

        } catch (IOException e) {

            System.out.println();
            System.out.println(
                    "Error while reading the recipe file."
            );
        }
    }


    // ============================================================
    // 9. MAIN METHOD
    // ============================================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // File location
        String fileName =
                "data" + File.separator + "recipes.txt";


        // Application title
        System.out.println();
        System.out.println("======================================");
        System.out.println("             RECIPE FINDER");
        System.out.println("======================================");
        System.out.println();


        // Menu
        System.out.println("1. Pattern Matching");
        System.out.println("2. Fuzzy Search");
        System.out.println("3. Exit");

        System.out.println();

        System.out.print("Enter your choice: ");

        String choice =
                sc.nextLine().trim();


        // ========================================================
        // PATTERN MATCHING
        // ========================================================

        if (choice.equals("1")) {

            System.out.print(
                    "Enter recipe name: "
            );

            String searchName =
                    sc.nextLine().trim();

            if (searchName.isEmpty()) {

                System.out.println(
                        "Please enter a recipe name."
                );

            } else {

                searchRecipe(
                        fileName,
                        searchName
                );
            }


        // ========================================================
        // FUZZY SEARCH
        // ========================================================

        } else if (choice.equals("2")) {

            System.out.print(
                    "Enter recipe name: "
            );

            String searchName =
                    sc.nextLine().trim();

            if (searchName.isEmpty()) {

                System.out.println(
                        "Please enter a recipe name."
                );

            } else {

                fuzzySearchRecipe(
                        fileName,
                        searchName
                );
            }


        // ========================================================
        // EXIT
        // ========================================================

        } else if (choice.equals("3")) {

            System.out.println(
                    "Thank you for using Recipe Finder."
            );

        } else {

            System.out.println(
                    "Invalid choice."
            );
        }

        sc.close();
    }
}