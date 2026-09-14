import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/*
 * ================================================================
 * RECIPE FINDER - DSA-3 PROJECT
 *
 * CO1 -> Problem analysis, algorithm selection and complexity
 * CO2 -> Naive Pattern Matching, KMP, Rabin-Karp
 * CO3 -> Dynamic Programming, Levenshtein Edit Distance
 * CO4 -> Bipartite Matching for ingredient-recipe matching
 * CO5 -> Greedy Set Cover Approximation
 * CO6 -> Randomized Recommendation + Parallel Similarity
 *
 * User Interface:
 * The user searches normally. Algorithms work internally.
 * ================================================================
 */

public class RecipeFinder {

    // ================================================================
    // RECIPE DATA CLASS
    // ================================================================

    static class Recipe {
        String name;
        String fullText;
        Set<String> ingredients;

        Recipe(String name, String fullText, Set<String> ingredients) {
            this.name = name;
            this.fullText = fullText;
            this.ingredients = ingredients;
        }
    }

    // ================================================================
    // CO1 - ALGORITHM INFORMATION
    // ================================================================

    public static void showAlgorithmInformation() {

        System.out.println();
        System.out.println("Algorithmic Components Used Internally");
        System.out.println("--------------------------------------");
        System.out.println("CO1 : Algorithm selection and complexity analysis");
        System.out.println("CO2 : Naive Pattern Matching, KMP, Rabin-Karp, Aho-Corasick");
        System.out.println("CO3 : Dynamic Programming, Levenshtein Distance");
        System.out.println("CO4 : Bipartite Matching");
        System.out.println("CO5 : Greedy Set Cover Approximation");
        System.out.println("CO6 : Randomized Recommendation and Parallel Processing");
        System.out.println();
    }

    // ================================================================
    // LOAD RECIPES
    // ================================================================

    public static List<Recipe> loadRecipes(String fileName) {

        List<Recipe> recipes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            StringBuilder recipeText = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {

                if (!line.isEmpty() &&
                        line.charAt(0) == '\uFEFF') {
                    line = line.substring(1);
                }

                if (isRecipeHeader(line) &&
                        recipeText.length() > 0) {

                    Recipe recipe = createRecipe(recipeText.toString().trim());

                    if (recipe != null) {
                        recipes.add(recipe);
                    }

                    recipeText.setLength(0);
                }

                recipeText.append(line)
                        .append(System.lineSeparator());
            }

            if (recipeText.length() > 0) {

                Recipe recipe = createRecipe(recipeText.toString().trim());

                if (recipe != null) {
                    recipes.add(recipe);
                }
            }

        } catch (FileNotFoundException e) {

            System.out.println();
            System.out.println("Error: recipes.txt file not found.");
            System.out.println(
                    "Make sure data/recipes.txt exists.");

        } catch (IOException e) {

            System.out.println();
            System.out.println("Error while reading recipes.txt.");
        }

        return recipes;
    }

    // ================================================================
    // CHECK RECIPE HEADER
    // ================================================================

    public static boolean isRecipeHeader(String line) {

        if (line == null) {
            return false;
        }

        return line.trim()
                .toLowerCase(Locale.ROOT)
                .startsWith("recipe:");
    }

    // ================================================================
    // GET RECIPE NAME
    // ================================================================

    public static String getRecipeName(String firstLine) {

        if (firstLine == null) {
            return "";
        }

        String line = firstLine.trim();

        if (!line.toLowerCase(Locale.ROOT)
                .startsWith("recipe:")) {
            return "";
        }

        return line.substring(7).trim();
    }

    // ================================================================
    // CREATE RECIPE OBJECT
    // ================================================================

    public static Recipe createRecipe(String recipeText) {

        String[] lines = recipeText.split("\\R");

        if (lines.length == 0) {
            return null;
        }

        String recipeName = getRecipeName(lines[0]);

        if (recipeName.isEmpty()) {
            return null;
        }

        Set<String> ingredients = new LinkedHashSet<>();

        boolean insideIngredients = false;

        for (String line : lines) {

            String trimmed = line.trim();

            if (trimmed.equalsIgnoreCase("Ingredients:")) {
                insideIngredients = true;
                continue;
            }

            if (trimmed.equalsIgnoreCase("Instructions:")) {
                insideIngredients = false;
                continue;
            }

            if (insideIngredients &&
                    trimmed.contains("-")) {

                String ingredient = trimmed.substring(
                        0,
                        trimmed.indexOf("-"))
                        .trim();

                if (!ingredient.isEmpty()) {

                    ingredients.add(
                            normalizeIngredient(ingredient));
                }
            }
        }

        return new Recipe(
                recipeName,
                recipeText,
                ingredients);
    }

    // ================================================================
    // NORMALIZE INGREDIENT
    // ================================================================

    public static String normalizeIngredient(
            String ingredient) {

        if (ingredient == null) {
            return "";
        }

        return ingredient
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9 ]", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    // ================================================================
    // CO2 - NAIVE PATTERN MATCHING
    // ================================================================

    public static boolean naivePatternMatch(
            String text,
            String pattern) {

        if (text == null || pattern == null) {
            return false;
        }

        text = text.toLowerCase(Locale.ROOT);
        pattern = pattern.toLowerCase(Locale.ROOT);

        int n = text.length();
        int m = pattern.length();

        if (m == 0) {
            return true;
        }

        if (m > n) {
            return false;
        }

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

    // ================================================================
    // CO2 - KMP LPS ARRAY
    // ================================================================

    public static int[] computeLPS(String pattern) {

        pattern = pattern.toLowerCase(Locale.ROOT);

        int[] lps = new int[pattern.length()];

        int length = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) == pattern.charAt(length)) {

                length++;
                lps[i] = length;
                i++;

            } else {

                if (length != 0) {

                    length = lps[length - 1];

                } else {

                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // ================================================================
    // CO2 - KMP SEARCH
    // ================================================================

    public static boolean kmpSearch(
            String text,
            String pattern) {

        if (text == null || pattern == null) {
            return false;
        }

        text = text.toLowerCase(Locale.ROOT);

        pattern = pattern.toLowerCase(Locale.ROOT);

        if (pattern.isEmpty()) {
            return true;
        }

        if (pattern.length() > text.length()) {
            return false;
        }

        int[] lps = computeLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;

                if (j == pattern.length()) {
                    return true;
                }

            } else {

                if (j != 0) {

                    j = lps[j - 1];

                } else {

                    i++;
                }
            }
        }

        return false;
    }

    // ================================================================
    // CO2 - RABIN-KARP
    // ================================================================

    public static boolean rabinKarpSearch(
            String text,
            String pattern) {

        if (text == null || pattern == null) {
            return false;
        }

        text = text.toLowerCase(Locale.ROOT);

        pattern = pattern.toLowerCase(Locale.ROOT);

        int n = text.length();
        int m = pattern.length();

        if (m == 0) {
            return true;
        }

        if (m > n) {
            return false;
        }

        final int BASE = 256;
        final int PRIME = 101;

        long patternHash = 0;
        long textHash = 0;
        long highestPower = 1;

        for (int i = 0; i < m - 1; i++) {

            highestPower = (highestPower * BASE)
                    % PRIME;
        }

        for (int i = 0; i < m; i++) {

            patternHash = (BASE * patternHash +
                    pattern.charAt(i))
                    % PRIME;

            textHash = (BASE * textHash +
                    text.charAt(i))
                    % PRIME;
        }

        for (int i = 0; i <= n - m; i++) {

            if (patternHash == textHash) {

                boolean match = true;

                for (int j = 0; j < m; j++) {

                    if (text.charAt(i + j) != pattern.charAt(j)) {

                        match = false;
                        break;
                    }
                }

                if (match) {
                    return true;
                }
            }

            if (i < n - m) {

                textHash = (BASE *
                        (textHash -
                                text.charAt(i) *
                                        highestPower)
                        +
                        text.charAt(i + m))
                        % PRIME;

                if (textHash < 0) {
                    textHash += PRIME;
                }
            }
        }

        return false;
    }

    // ================================================================
    // CO2 - STRING MATCHING
    //
    // The three algorithms are independently verified.
    // The user never selects the algorithm.
    // ================================================================

    public static boolean patternMatch(
            String text,
            String pattern) {

        boolean naive = naivePatternMatch(
                text,
                pattern);

        boolean kmp = kmpSearch(
                text,
                pattern);

        boolean rabinKarp = rabinKarpSearch(
                text,
                pattern);

        boolean ahoCorasick = ahoCorasickSearch(
                text,
                pattern);

        /*
         * All implemented pattern matching algorithms are
         * independently verified. The result is true only when
         * all four algorithms agree that the pattern exists.
         */
        return naive == kmp &&
                kmp == rabinKarp &&
                rabinKarp == ahoCorasick &&
                naive;
    }

    // ================================================================
    // CO2 - AHO-CORASICK PATTERN MATCHING
    // ================================================================

    static class AhoNode {
        int[] next = new int[128];
        int fail;
        boolean output;

        AhoNode() {
            Arrays.fill(next, -1);
            fail = 0;
            output = false;
        }
    }

    public static boolean ahoCorasickSearch(
            String text,
            String pattern) {

        if (text == null || pattern == null) {
            return false;
        }

        text = text.toLowerCase(Locale.ROOT);
        pattern = pattern.toLowerCase(Locale.ROOT);

        if (pattern.isEmpty()) {
            return true;
        }

        ArrayList<AhoNode> trie = new ArrayList<>();
        trie.add(new AhoNode());

        // Insert the pattern into the Aho-Corasick trie.
        int current = 0;

        for (char ch : pattern.toCharArray()) {
            int c = ch;

            if (c >= 128) {
                return false;
            }

            if (trie.get(current).next[c] == -1) {
                trie.get(current).next[c] = trie.size();
                trie.add(new AhoNode());
            }

            current = trie.get(current).next[c];
        }

        trie.get(current).output = true;

        // Build failure links using BFS.
        Queue<Integer> queue = new ArrayDeque<>();

        for (int c = 0; c < 128; c++) {
            int child = trie.get(0).next[c];

            if (child != -1) {
                trie.get(child).fail = 0;
                queue.add(child);
            } else {
                trie.get(0).next[c] = 0;
            }
        }

        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int c = 0; c < 128; c++) {
                int child = trie.get(current).next[c];

                if (child != -1) {
                    trie.get(child).fail = trie.get(trie.get(current).fail).next[c];

                    if (trie.get(
                            trie.get(child).fail).output) {
                        trie.get(child).output = true;
                    }

                    queue.add(child);
                } else {
                    trie.get(current).next[c] = trie.get(trie.get(current).fail).next[c];
                }
            }
        }

        // Scan the text using the automaton.
        current = 0;

        for (char ch : text.toCharArray()) {
            int c = ch;

            if (c >= 128) {
                current = 0;
                continue;
            }

            current = trie.get(current).next[c];

            if (trie.get(current).output) {
                return true;
            }
        }

        return false;
    }

    // ================================================================
    // AUTOMATIC PREFIX SUGGESTIONS - TRIE
    // ================================================================

    static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();
        List<Recipe> recipes = new ArrayList<>();
    }

    static class AutocompleteTrie {
        TrieNode root = new TrieNode();

        void insert(Recipe recipe) {
            String name = recipe.name.toLowerCase(Locale.ROOT);

            TrieNode current = root;

            for (char ch : name.toCharArray()) {
                current = current.children.computeIfAbsent(
                        ch, k -> new TrieNode());
            }

            current.recipes.add(recipe);
        }

        List<Recipe> search(String prefix) {
            List<Recipe> result = new ArrayList<>();

            String p = prefix.toLowerCase(Locale.ROOT)
                    .trim();

            if (p.isEmpty()) {
                return result;
            }

            TrieNode current = root;

            for (char ch : p.toCharArray()) {
                current = current.children.get(ch);

                if (current == null) {
                    return result;
                }
            }

            collect(current, result);

            result.sort(
                    Comparator.comparing(
                            r -> r.name.toLowerCase(Locale.ROOT)));

            return result;
        }

        void collect(
                TrieNode node,
                List<Recipe> result) {

            result.addAll(node.recipes);

            for (TrieNode child : node.children.values()) {
                collect(child, result);
            }
        }
    }

    static AutocompleteTrie autocompleteTrie = new AutocompleteTrie();

    public static void buildAutocomplete(
            List<Recipe> recipes) {

        autocompleteTrie = new AutocompleteTrie();

        for (Recipe recipe : recipes) {
            autocompleteTrie.insert(recipe);
        }
    }

    public static List<Recipe> getSuggestions(
            List<Recipe> recipes,
            String query) {

        return autocompleteTrie.search(query);
    }

    // ================================================================
    // DISPLAY SUGGESTIONS
    // ================================================================

    // ================================================================
    // DISPLAY SUGGESTIONS
    // ================================================================

    public static void displaySuggestions(
            List<Recipe> suggestions) {

        if (suggestions.isEmpty()) {

            System.out.println(
                    "No direct suggestions found.");

            return;
        }

        System.out.println();
        System.out.println("Suggestions:");

        int limit = Math.min(10, suggestions.size());

        for (int i = 0; i < limit; i++) {

            System.out.println(
                    (i + 1) + ". " +
                            suggestions.get(i).name);
        }

        if (suggestions.size() > limit) {
            System.out.println(
                    "... and " +
                            (suggestions.size() - limit) +
                            " more suggestions.");
        }

        System.out.println();
    }

    // ================================================================
    // EXACT RECIPE SEARCH
    // ================================================================

    public static Recipe exactSearch(
            List<Recipe> recipes,
            String query) {

        for (Recipe recipe : recipes) {

            if (recipe.name.equalsIgnoreCase(query)) {

                /*
                 * CO2 algorithms are used internally
                 * to verify the exact recipe name.
                 */
                if (patternMatch(
                        recipe.name,
                        query)) {

                    return recipe;
                }
            }
        }

        return null;
    }

    // ================================================================
    // CO3 - LEVENSHTEIN EDIT DISTANCE
    // Dynamic Programming
    // ================================================================

    public static int levenshteinDistance(
            String a,
            String b) {

        if (a == null || b == null) {
            return Integer.MAX_VALUE;
        }

        a = a.toLowerCase(Locale.ROOT);

        b = b.toLowerCase(Locale.ROOT);

        int n = a.length();
        int m = b.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= m; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    dp[i][j] = dp[i - 1][j - 1];

                } else {

                    int insertion = dp[i][j - 1];

                    int deletion = dp[i - 1][j];

                    int substitution = dp[i - 1][j - 1];

                    dp[i][j] = 1 + Math.min(
                            insertion,
                            Math.min(
                                    deletion,
                                    substitution));
                }
            }
        }

        return dp[n][m];
    }

    // ================================================================
    // CO3 - SIMILARITY
    // ================================================================

    public static double similarity(
            String a,
            String b) {

        int distance = levenshteinDistance(a, b);

        int maxLength = Math.max(
                a.length(),
                b.length());

        if (maxLength == 0) {
            return 100.0;
        }

        return (1.0 -
                (double) distance /
                        maxLength)
                * 100.0;
    }

    // ================================================================
    // CO3 - SEQUENTIAL FUZZY SEARCH
    // ================================================================

    public static Recipe fuzzySearch(
            List<Recipe> recipes,
            String query) {

        Recipe bestRecipe = null;
        double bestScore = -1.0;

        for (Recipe recipe : recipes) {

            double score = similarity(
                    query,
                    recipe.name);

            if (score > bestScore) {

                bestScore = score;
                bestRecipe = recipe;
            }
        }

        if (bestRecipe != null &&
                bestScore >= 70.0) {

            System.out.println();
            System.out.println(
                    "No exact recipe found.");

            System.out.println(
                    "Closest Recipe: " +
                            bestRecipe.name);

            System.out.printf(
                    "Similarity: %.2f%%%n",
                    bestScore);

            System.out.println();

            return bestRecipe;
        }

        return null;
    }

    // ================================================================
    // CO4 - INGREDIENT MATCH
    // ================================================================

    public static boolean ingredientMatch(
            String requested,
            String recipeIngredient) {

        requested = normalizeIngredient(requested);

        recipeIngredient = normalizeIngredient(recipeIngredient);

        return requested.equals(recipeIngredient);
    }

    // ================================================================
    // CO4 - BIPARTITE MATCHING
    // ================================================================

    public static int bipartiteMatching(
            List<String> requestedIngredients,
            Recipe recipe) {

        int leftSize = requestedIngredients.size();

        List<String> recipeIngredients = new ArrayList<>(
                recipe.ingredients);

        int rightSize = recipeIngredients.size();

        int[] matchedRight = new int[rightSize];

        Arrays.fill(
                matchedRight,
                -1);

        int matches = 0;

        for (int u = 0; u < leftSize; u++) {

            boolean[] visited = new boolean[rightSize];

            if (tryMatch(
                    u,
                    requestedIngredients,
                    recipeIngredients,
                    matchedRight,
                    visited)) {

                matches++;
            }
        }

        return matches;
    }

    // ================================================================
    // CO4 - AUGMENTING PATH
    // ================================================================

    public static boolean tryMatch(
            int u,
            List<String> requestedIngredients,
            List<String> recipeIngredients,
            int[] matchedRight,
            boolean[] visited) {

        for (int v = 0; v < recipeIngredients.size(); v++) {

            if (visited[v]) {
                continue;
            }

            if (ingredientMatch(
                    requestedIngredients.get(u),
                    recipeIngredients.get(v))) {

                visited[v] = true;

                if (matchedRight[v] == -1 ||
                        tryMatch(
                                matchedRight[v],
                                requestedIngredients,
                                recipeIngredients,
                                matchedRight,
                                visited)) {

                    matchedRight[v] = u;

                    return true;
                }
            }
        }

        return false;
    }

    // ================================================================
    // CO4 - INGREDIENT RECOMMENDATION
    // ================================================================

    public static void ingredientRecommendation(
            List<Recipe> recipes,
            String input) {

        String[] parts = input.split(",");

        List<String> requested = new ArrayList<>();

        for (String part : parts) {

            String ingredient = normalizeIngredient(part);

            if (!ingredient.isEmpty()) {
                requested.add(ingredient);
            }
        }

        if (requested.isEmpty()) {

            System.out.println(
                    "Please enter at least one ingredient.");

            return;
        }

        Recipe bestRecipe = null;
        int bestMatches = -1;

        for (Recipe recipe : recipes) {

            int matches = bipartiteMatching(
                    requested,
                    recipe);

            if (matches > bestMatches) {

                bestMatches = matches;
                bestRecipe = recipe;
            }
        }

        System.out.println();
        System.out.println(
                "Ingredient Matching Result");
        System.out.println(
                "--------------------------");

        if (bestRecipe == null ||
                bestMatches == 0) {

            System.out.println(
                    "No matching recipe found.");

            System.out.println();

            return;
        }

        System.out.println(
                "Best Recipe: " +
                        bestRecipe.name);

        System.out.println(
                "Matched Ingredients: " +
                        bestMatches +
                        "/" +
                        requested.size());

        System.out.println();
    }

    // ================================================================
    // CO5 - GREEDY SET COVER APPROXIMATION
    // ================================================================

    public static void greedySetCover(
            List<Recipe> recipes,
            String input) {

        String[] parts = input.split(",");

        Set<String> remaining = new LinkedHashSet<>();

        for (String part : parts) {

            String ingredient = normalizeIngredient(part);

            if (!ingredient.isEmpty()) {
                remaining.add(ingredient);
            }
        }

        if (remaining.isEmpty()) {

            System.out.println(
                    "Please enter ingredients.");

            return;
        }

        List<Recipe> selected = new ArrayList<>();

        Set<String> uncovered = new LinkedHashSet<>(
                remaining);

        Set<Recipe> alreadySelected = new HashSet<>();

        while (!uncovered.isEmpty()) {

            Recipe bestRecipe = null;

            Set<String> bestCoverage = new LinkedHashSet<>();

            for (Recipe recipe : recipes) {

                if (alreadySelected.contains(recipe)) {
                    continue;
                }

                Set<String> coverage = new LinkedHashSet<>();

                for (String requested : uncovered) {

                    for (String ingredient : recipe.ingredients) {

                        if (ingredientMatch(
                                requested,
                                ingredient)) {

                            coverage.add(requested);
                            break;
                        }
                    }
                }

                if (coverage.size() > bestCoverage.size()) {

                    bestCoverage = coverage;

                    bestRecipe = recipe;
                }
            }

            if (bestRecipe == null ||
                    bestCoverage.isEmpty()) {

                break;
            }

            selected.add(bestRecipe);
            alreadySelected.add(bestRecipe);

            uncovered.removeAll(
                    bestCoverage);
        }

        System.out.println();
        System.out.println(
                "Recipe Set-Cover Recommendation");
        System.out.println(
                "--------------------------------");

        if (selected.isEmpty()) {

            System.out.println(
                    "No suitable recipes found.");

            System.out.println();

            return;
        }

        for (int i = 0; i < selected.size(); i++) {

            System.out.println(
                    (i + 1) + ". " +
                            selected.get(i).name);
        }

        System.out.println();

        if (uncovered.isEmpty()) {

            System.out.println(
                    "All requested ingredients are covered.");

        } else {

            System.out.println(
                    "Ingredients not covered: " +
                            uncovered);
        }

        System.out.println();
    }

    // ================================================================
    // CO6 - RANDOMIZED RECOMMENDATION
    // ================================================================

    public static void randomizedRecommendation(
            List<Recipe> recipes) {

        if (recipes.isEmpty()) {
            return;
        }

        Random random = new Random();

        int index = random.nextInt(
                recipes.size());

        Recipe selected = recipes.get(index);

        System.out.println();
        System.out.println(
                "Random Recipe Recommendation");
        System.out.println(
                "-----------------------------");

        System.out.println(
                "Try: " +
                        selected.name);

        System.out.println();
    }

    // ================================================================
    // CO6 - PARALLEL SIMILARITY
    // ================================================================

    static class SimilarityResult {

        Recipe recipe;
        double score;

        SimilarityResult(
                Recipe recipe,
                double score) {

            this.recipe = recipe;
            this.score = score;
        }
    }

    public static Recipe parallelFuzzySearch(
            List<Recipe> recipes,
            String query) {

        if (recipes.isEmpty()) {
            return null;
        }

        int threadCount = Math.max(
                1,
                Math.min(
                        Runtime.getRuntime()
                                .availableProcessors(),
                        recipes.size()));

        ExecutorService executor = Executors.newFixedThreadPool(
                threadCount);

        List<Future<SimilarityResult>> futures = new ArrayList<>();

        try {

            for (Recipe recipe : recipes) {

                Future<SimilarityResult> future = executor.submit(() -> {

                    double score = similarity(
                            query,
                            recipe.name);

                    return new SimilarityResult(
                            recipe,
                            score);
                });

                futures.add(future);
            }

            Recipe bestRecipe = null;
            double bestScore = -1.0;

            for (Future<SimilarityResult> future : futures) {

                SimilarityResult result = future.get();

                if (result.score > bestScore) {

                    bestScore = result.score;

                    bestRecipe = result.recipe;
                }
            }

            return bestRecipe;

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return null;

        } catch (ExecutionException e) {

            System.out.println(
                    "Error during parallel similarity.");

            return null;

        } finally {

            executor.shutdown();
        }
    }

    // ================================================================
    // DISPLAY COMPLETE RECIPE
    // ================================================================

    public static void displayRecipe(
            Recipe recipe) {

        System.out.println();
        System.out.println(
                "======================================");
        System.out.println(
                "                RECIPE");
        System.out.println(
                "======================================");
        System.out.println();

        System.out.println(
                recipe.fullText);

        System.out.println(
                "======================================");
        System.out.println();
    }

    // ================================================================
    // NORMAL RECIPE SEARCH
    // ================================================================

    public static void processSearch(
            List<Recipe> recipes,
            String query) {

        if (query == null ||
                query.trim().isEmpty()) {

            System.out.println(
                    "Please enter a recipe name.");

            return;
        }

        query = query.trim();

        // ------------------------------------------------------------
        // EXACT SEARCH
        // ------------------------------------------------------------

        Recipe exact = exactSearch(
                recipes,
                query);

        if (exact != null) {

            System.out.println();
            System.out.println(
                    "Recipe found: " +
                            exact.name);

            displayRecipe(exact);

            return;
        }

        // ------------------------------------------------------------
        // FUZZY SEARCH USING PARALLEL SIMILARITY
        // ------------------------------------------------------------

        Recipe fuzzy = parallelFuzzySearch(
                recipes,
                query);

        if (fuzzy != null) {

            double score = similarity(
                    query,
                    fuzzy.name);

            if (score >= 70.0) {

                System.out.println();
                System.out.println(
                        "No exact recipe found.");

                System.out.println(
                        "Closest Recipe: " +
                                fuzzy.name);

                System.out.printf(
                        "Similarity: %.2f%%%n",
                        score);

                System.out.println();

                displayRecipe(fuzzy);

                return;
            }
        }

        System.out.println();
        System.out.println(
                "Recipe not found.");

        System.out.println(
                "Please try another recipe name.");

        System.out.println();
    }

    // ================================================================
    // HELP
    // ================================================================

    public static void showHelp() {

        System.out.println();
        System.out.println(
                "Recipe Finder Commands");
        System.out.println(
                "----------------------");

        System.out.println(
                "Recipe name"
                        + " -> Search for a recipe");

        System.out.println(
                "ingredient: X,Y"
                        + " -> Ingredient matching");

        System.out.println(
                "cover: X,Y"
                        + " -> Ingredient coverage");

        System.out.println(
                "random"
                        + " -> Random recipe recommendation");

        System.out.println(
                "help"
                        + " -> Show commands");

        System.out.println(
                "exit"
                        + " -> Close Recipe Finder");

        System.out.println();
    }

    // ================================================================
    // MAIN APPLICATION
    // ================================================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String fileName = "data" +
                File.separator +
                "recipes.txt";

        List<Recipe> recipes = loadRecipes(fileName);

        if (recipes.isEmpty()) {

            System.out.println(
                    "No recipes were loaded.");

            sc.close();

            return;
        }

        // Build the Trie once so autocomplete is fast for repeated searches.
        buildAutocomplete(recipes);

        System.out.println();
        System.out.println(
                "========================================");
        System.out.println(
                "              RECIPE FINDER");
        System.out.println(
                "========================================");

        System.out.println(
                "Recipes loaded: " +
                        recipes.size());

        if (recipes.size() < 2000) {
            System.out.println(
                    "WARNING: Dataset contains fewer than 2000 recipes.");
        } else {
            System.out.println(
                    "Dataset requirement: 2000+ recipes - satisfied.");
        }

        System.out.println();

        System.out.println(
                "Search for a recipe below.");

        System.out.println(
                "Type 'help' for additional features.");

        System.out.println();

        while (true) {

            System.out.print(
                    "Search recipe: ");

            String query = sc.nextLine().trim();

            // --------------------------------------------------------
            // EXIT
            // --------------------------------------------------------

            if (query.equalsIgnoreCase("exit")) {

                System.out.println();
                System.out.println(
                        "Thank you for using Recipe Finder.");

                break;
            }

            // --------------------------------------------------------
            // HELP
            // --------------------------------------------------------

            if (query.equalsIgnoreCase("help")) {

                showHelp();

                continue;
            }

            // --------------------------------------------------------
            // CO6 - RANDOM
            // --------------------------------------------------------

            if (query.equalsIgnoreCase("random")) {

                randomizedRecommendation(
                        recipes);

                continue;
            }

            // --------------------------------------------------------
            // CO4 - INGREDIENT MATCHING
            // --------------------------------------------------------

            if (query.toLowerCase(Locale.ROOT)
                    .startsWith("ingredient:")) {

                String ingredients = query.substring(
                        "ingredient:".length())
                        .trim();

                ingredientRecommendation(
                        recipes,
                        ingredients);

                continue;
            }

            // --------------------------------------------------------
            // CO5 - SET COVER
            // --------------------------------------------------------

            if (query.toLowerCase(Locale.ROOT)
                    .startsWith("cover:")) {

                String ingredients = query.substring(
                        "cover:".length())
                        .trim();

                greedySetCover(
                        recipes,
                        ingredients);

                continue;
            }

            // --------------------------------------------------------
            // EMPTY INPUT
            // --------------------------------------------------------

            if (query.isEmpty()) {

                System.out.println(
                        "Please enter a recipe name.");

                continue;
            }

            // --------------------------------------------------------
            // AUTOMATIC SUGGESTIONS
            // --------------------------------------------------------

            List<Recipe> suggestions = getSuggestions(
                    recipes,
                    query);

            displaySuggestions(
                    suggestions);

            // --------------------------------------------------------
            // SUGGESTION SELECTION
            // --------------------------------------------------------

            if (!suggestions.isEmpty()) {

                System.out.print(
                        "Enter suggestion number, "
                                + "recipe name, or press Enter to search: ");

                String selection = sc.nextLine().trim();

                // User presses Enter
                if (selection.isEmpty()) {

                    processSearch(
                            recipes,
                            query);

                    continue;
                }

                // User selects suggestion number
                try {

                    int choice = Integer.parseInt(
                            selection);

                    if (choice >= 1 &&
                            choice <= Math.min(10, suggestions.size())) {

                        Recipe selected = suggestions.get(
                                choice - 1);

                        System.out.println();
                        System.out.println(
                                "Selected Recipe: " +
                                        selected.name);

                        displayRecipe(
                                selected);

                    } else {

                        System.out.println();
                        System.out.println(
                                "Invalid suggestion number.");

                        System.out.println();
                    }

                } catch (NumberFormatException e) {

                    // User entered a recipe name
                    processSearch(
                            recipes,
                            selection);
                }

            } else {

                // ----------------------------------------------------
                // NO SUGGESTION
                // USE EXACT/FUZZY SEARCH
                // ----------------------------------------------------

                processSearch(
                        recipes,
                        query);
            }
        }

        sc.close();
    }
}