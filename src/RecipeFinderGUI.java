import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RecipeFinderGUI extends JFrame {
    private static final Color NAVY = new Color(24, 52, 78);
    private static final Color TEAL = new Color(45, 127, 132);
    private static final Color BACKGROUND = new Color(244, 247, 249);
    private static final Color MUTED = new Color(92, 108, 120);

    private final List<RecipeFinder.Recipe> recipes;
    private final CardLayout viewLayout = new CardLayout();
    private final JPanel views = new JPanel(viewLayout);
    private final JTextArea randomResultArea = createTextArea();
    private final JLabel globalStatus = new JLabel();

    private static class SearchComponents {
        final JTextField searchField = new JTextField();
        final DefaultListModel<String> suggestionModel = new DefaultListModel<>();
        final JList<String> suggestionList = new JList<>(suggestionModel);
        final JTextArea resultArea = createTextArea();
    }

    private static class IngredientComponents {
        final JTextField inputField = new JTextField();
        final JTextArea resultArea = createTextArea();
    }

    private RecipeFinderGUI(List<RecipeFinder.Recipe> recipes) {
        super("Recipe Finder");
        this.recipes = recipes;
        RecipeFinder.buildAutocomplete(recipes);
        buildWindow();
    }

    private void buildWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 680));
        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        views.setBackground(BACKGROUND);
        views.setMinimumSize(new Dimension(0, 0));
        views.add(createDashboard(), "home");
        views.add(createSearchView(), "search");
        views.add(createIngredientView(), "ingredient");
        views.add(createCoverageView(), "coverage");
        views.add(createRandomView(), "random");
        views.add(createAboutView(), "about");
        add(views, BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
        setLocationByPlatform(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(18, 26, 16, 26));
        JLabel title = new JLabel("RECIPE FINDER");
        title.setFont(new Font("Serif", Font.BOLD, 25));
        title.setForeground(NAVY);
        JLabel subtitle = new JLabel("Search  |  Discover  |  Explore Recipes");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);
        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new javax.swing.BoxLayout(heading, javax.swing.BoxLayout.Y_AXIS));
        heading.add(title);
        heading.add(subtitle);
        header.add(heading, BorderLayout.WEST);
        JLabel count = new JLabel(recipes.size() + " recipes");
        count.setFont(new Font("SansSerif", Font.BOLD, 13));
        count.setForeground(TEAL);
        header.add(count, BorderLayout.EAST);
        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(175, 0));
        sidebar.setBackground(NAVY);
        sidebar.setBorder(BorderFactory.createEmptyBorder(18, 12, 18, 12));
        sidebar.setLayout(new javax.swing.BoxLayout(sidebar, javax.swing.BoxLayout.Y_AXIS));
        addNavigationButton(sidebar, "HOME", "home");
        addNavigationButton(sidebar, "SEARCH", "search");
        addNavigationButton(sidebar, "INGREDIENT SEARCH", "ingredient");
        addNavigationButton(sidebar, "GREEDY COVERAGE", "coverage");
        addNavigationButton(sidebar, "RANDOM RECIPE", "random");
        sidebar.add(javax.swing.Box.createVerticalGlue());
        addNavigationButton(sidebar, "ABOUT", "about");
        return sidebar;
    }

    private void addNavigationButton(JPanel sidebar, String label, String view) {
        JButton button = new JButton(label);
        button.setAlignmentX(0.5f);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setForeground(Color.WHITE);
        button.setBackground(NAVY);
        button.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 4));
        button.setFocusPainted(false);
        button.addActionListener(event -> viewLayout.show(views, view));
        sidebar.add(button);
        sidebar.add(javax.swing.Box.createVerticalStrut(6));
    }

    private JPanel createDashboard() {
        JPanel dashboard = basePanel();
        dashboard.setLayout(new BorderLayout(16, 16));
        dashboard.setMinimumSize(new Dimension(0, 0));
        dashboard.add(createSearchWorkspace(new SearchComponents()), BorderLayout.CENTER);
        JPanel tools = new JPanel(new GridLayout(1, 2, 16, 0));
        tools.setOpaque(false);
        tools.setMinimumSize(new Dimension(0, 0));
        tools.add(createIngredientCard(new IngredientComponents()));
        tools.add(createCoverageCard(new IngredientComponents()));
        dashboard.add(tools, BorderLayout.SOUTH);
        return dashboard;
    }

    private JPanel createSearchWorkspace(SearchComponents components) {
        JPanel workspace = new JPanel(new BorderLayout(12, 12));
        workspace.setOpaque(false);
        workspace.setMinimumSize(new Dimension(0, 0));
        workspace.add(createSearchBar(components), BorderLayout.NORTH);
        JPanel center = new JPanel(new GridLayout(1, 2, 16, 0));
        center.setOpaque(false);
        center.setMinimumSize(new Dimension(0, 0));
        center.add(createSuggestionsCard(components));
        center.add(createResultCard(components));
        workspace.add(center, BorderLayout.CENTER);
        return workspace;
    }

    private JPanel createSearchBar(SearchComponents components) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        JLabel label = new JLabel("Search Recipe");
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(NAVY);
        panel.add(label, BorderLayout.WEST);
        components.searchField.setPreferredSize(new Dimension(300, 38));
        components.searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(components.searchField, BorderLayout.CENTER);
        JButton button = primaryButton("Search");
        button.addActionListener(event -> search(components));
        components.searchField.addActionListener(event -> search(components));
        panel.add(button, BorderLayout.EAST);
        return panel;
    }

    private JPanel createSuggestionsCard(SearchComponents components) {
        JPanel card = createCard("Autocomplete Suggestions");
        components.suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        components.suggestionList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        components.suggestionList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && components.suggestionList.getSelectedValue() != null) {
                components.searchField.setText(components.suggestionList.getSelectedValue());
            }
        });
        components.searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent event) {
                updateSuggestions(components);
            }

            public void removeUpdate(DocumentEvent event) {
                updateSuggestions(components);
            }

            public void changedUpdate(DocumentEvent event) {
                updateSuggestions(components);
            }
        });
        card.add(new JScrollPane(components.suggestionList), BorderLayout.CENTER);
        return card;
    }

    private JPanel createResultCard(SearchComponents components) {
        JPanel card = createCard("Search Result");
        card.add(new JScrollPane(components.resultArea), BorderLayout.CENTER);
        return card;
    }

    private JPanel createIngredientCard(IngredientComponents components) {
        JPanel card = createCard("Ingredient Search (CO4)");
        JPanel controls = new JPanel(new BorderLayout(8, 0));
        controls.setOpaque(false);
        components.inputField.setToolTipText("Example: chicken,onion,tomato");
        controls.add(components.inputField, BorderLayout.CENTER);
        JButton button = primaryButton("Find Recipes");
        button.addActionListener(event -> findIngredients(components));
        controls.add(button, BorderLayout.EAST);
        card.add(controls, BorderLayout.NORTH);
        card.add(new JScrollPane(components.resultArea), BorderLayout.CENTER);
        return card;
    }

    private JPanel createCoverageCard(IngredientComponents components) {
        JPanel card = createCard("Greedy Coverage (CO5)");
        JPanel controls = new JPanel(new BorderLayout(8, 0));
        controls.setOpaque(false);
        components.inputField.setToolTipText("Example: chicken,onion,tomato");
        controls.add(components.inputField, BorderLayout.CENTER);
        JButton button = primaryButton("Find Coverage");
        button.addActionListener(event -> findCoverage(components));
        controls.add(button, BorderLayout.EAST);
        card.add(controls, BorderLayout.NORTH);
        card.add(new JScrollPane(components.resultArea), BorderLayout.CENTER);
        return card;
    }

    private JPanel createSearchView() {
        JPanel panel = basePanel();
        panel.setLayout(new BorderLayout());
        panel.add(createSearchWorkspace(new SearchComponents()), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createIngredientView() {
        JPanel panel = basePanel();
        panel.setLayout(new BorderLayout());
        panel.add(createIngredientCard(new IngredientComponents()), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCoverageView() {
        JPanel panel = basePanel();
        panel.setLayout(new BorderLayout());
        panel.add(createCoverageCard(new IngredientComponents()), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRandomView() {
        JPanel panel = basePanel();
        panel.setLayout(new BorderLayout(12, 12));
        JPanel card = createCard("Random Recommendation (CO6)");
        JButton button = primaryButton("Get Random Recipe");
        button.addActionListener(event -> showRandomRecipe());
        card.add(button, BorderLayout.NORTH);
        card.add(new JScrollPane(randomResultArea), BorderLayout.CENTER);
        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAboutView() {
        JPanel panel = basePanel();
        panel.setLayout(new BorderLayout());
        JPanel card = createCard("About Recipe Finder");
        JTextArea text = createTextArea();
        text.setText("Recipe Finder\n\nA Java Swing interface over the existing DSA-3 implementation.\n\n"
                + "Trie autocomplete, pattern matching, Levenshtein fuzzy search,\n"
                + "bipartite ingredient matching, greedy set cover, randomized recommendation,\n"
                + "and parallel similarity remain in RecipeFinder.java.\n\nRecipes loaded: " + recipes.size());
        card.add(new JScrollPane(text), BorderLayout.CENTER);
        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatusBar() {
        JPanel status = new JPanel(new BorderLayout());
        status.setBackground(Color.WHITE);
        status.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        globalStatus.setText("Ready  |  " + recipes.size() + " recipes loaded from data/recipes.txt");
        globalStatus.setForeground(MUTED);
        status.add(globalStatus, BorderLayout.WEST);
        return status;
    }

    private JPanel basePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        return panel;
    }

    private JPanel createCard(String title) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setMinimumSize(new Dimension(0, 0));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 226, 231)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));
        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.BOLD, 14));
        heading.setForeground(NAVY);
        card.add(heading, BorderLayout.NORTH);
        return card;
    }

    private JButton primaryButton(String label) {
        JButton button = new JButton(label);
        button.setBackground(TEAL);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private static JTextArea createTextArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return area;
    }

    private void updateSuggestions(SearchComponents components) {
        components.suggestionModel.clear();
        String query = components.searchField.getText().trim();
        if (query.isEmpty())
            return;
        List<RecipeFinder.Recipe> suggestions = RecipeFinder.getSuggestions(recipes, query);
        for (int index = 0; index < Math.min(10, suggestions.size()); index++) {
            components.suggestionModel.addElement(suggestions.get(index).name);
        }
    }

    private void search(SearchComponents components) {
        String query = components.searchField.getText().trim();
        if (query.isEmpty()) {
            components.resultArea.setText("");
            showStatus("Please enter a recipe name.");
            return;
        }
        RecipeFinder.Recipe recipe = RecipeFinder.exactSearch(recipes, query);
        if (recipe != null) {
            double score = RecipeFinder.similarity(query, recipe.name);
            components.resultArea.setText(String.format(Locale.ROOT,
                    "Recipe found: %s\n\nPattern Match: Found\nSimilarity: %.0f%%\n\n%s",
                    recipe.name, score, recipe.fullText));
            components.resultArea.setCaretPosition(0);
            showStatus("Recipe found using exact pattern matching.");
            return;
        }
        recipe = RecipeFinder.parallelFuzzySearch(recipes, query);
        if (recipe != null) {
            double score = RecipeFinder.similarity(query, recipe.name);
            if (score >= 70.0) {
                components.resultArea.setText(String.format(Locale.ROOT,
                        "Closest recipe: %s\nSimilarity: %.2f%%\n\n%s",
                        recipe.name, score, recipe.fullText));
                components.resultArea.setCaretPosition(0);
                showStatus("No exact match. Parallel fuzzy search found the closest recipe.");
                return;
            }
        }
        components.resultArea.setText("No recipe found for: " + query);
        showStatus("Recipe not found. Please try another recipe name.");
    }

    private void showRandomRecipe() {
        String output = captureOutput(() -> RecipeFinder.randomizedRecommendation(recipes));
        int markerIndex = output.indexOf("Try:");
        if (markerIndex >= 0) {
            String name = output.substring(markerIndex + 4).trim();
            RecipeFinder.Recipe recipe = RecipeFinder.exactSearch(recipes, name);
            if (recipe != null) {
                randomResultArea.setText(recipe.fullText);
                randomResultArea.setCaretPosition(0);
                viewLayout.show(views, "random");
                showStatus("Random recommendation ready: " + recipe.name);
                return;
            }
        }
        randomResultArea.setText(output.trim());
    }

    private void findIngredients(IngredientComponents components) {
        String input = components.inputField.getText().trim();
        if (input.isEmpty()) {
            showStatus("Enter comma-separated ingredients.");
            return;
        }
        String output = captureOutput(() -> RecipeFinder.ingredientRecommendation(recipes, input));
        components.resultArea.setText(output.trim());
        showStatus(output.contains("No matching recipe") ? "No matching recipe found."
                : "CO4 ingredient matching completed.");
    }

    private void findCoverage(IngredientComponents components) {
        String input = components.inputField.getText().trim();
        if (input.isEmpty()) {
            showStatus("Enter comma-separated ingredients.");
            return;
        }
        String output = captureOutput(() -> RecipeFinder.greedySetCover(recipes, input));
        components.resultArea.setText(output.trim());
        showStatus(output.contains("No suitable recipes") ? "No suitable recipes found."
                : "CO5 greedy coverage completed.");
    }

    private void showStatus(String message) {
        globalStatus.setText(message);
    }

    private String captureOutput(Runnable operation) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            operation.run();
        } finally {
            System.setOut(original);
        }
        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }

    private static List<RecipeFinder.Recipe> loadRecipes() {
        return RecipeFinder.loadRecipes("data" + File.separator + "recipes.txt");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<RecipeFinder.Recipe> recipes = loadRecipes();
            if (recipes.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "No recipes could be loaded from data/recipes.txt.",
                        "Recipe Finder", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            RecipeFinderGUI window = new RecipeFinderGUI(recipes);
            window.setSize(1100, 760);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}