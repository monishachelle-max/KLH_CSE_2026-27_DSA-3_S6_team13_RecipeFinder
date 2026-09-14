# Recipe Finder - Project Report

## Project Title

# Recipe Finder Using String Matching and Similarity Algorithms

---

## Team Members

| S.No | Team Member Name | ID Number |
|------|------------------|-----------|
| 1 | Monisha Raghini Chelle | 2520030537 |
| 2 | Shaik Sabiya Tabassum | 2520030502 |
| 3 | K. Siri Chandana | 2520030453 |

---

## Supervisor

**Dr. V. Sireesha**  
Professor, Department of CSE

---

# 1. Introduction

The Recipe Finder is a Java-based application designed to search and retrieve recipes from a text-based recipe corpus.

The project applies Data Structures and Algorithms to solve a practical recipe-search problem. The system supports exact recipe searching, autocomplete, pattern matching, fuzzy searching, similarity calculation, ingredient-based searching, greedy ingredient coverage, and recipe recommendation.

The application provides a normal search interface where users enter a recipe name or partial recipe name. The algorithms are applied internally by the application rather than being exposed to the user as separate menu options.

---

# 2. Problem Statement

Searching a large recipe collection using only exact text matching can make it difficult to find recipes when the user enters an incomplete name, different capitalization, or a small spelling variation.

The objective of this project is to develop a recipe-search application that can:

- Search recipes efficiently.
- Provide suggestions for partial recipe names.
- Handle case differences.
- Handle small spelling differences.
- Calculate similarity between recipe names.
- Search recipes using ingredients.
- Select recipes based on ingredient coverage.
- Provide random recipe recommendations.

---

# 3. Objectives

The objectives of the project are:

1. To develop a Java-based recipe search application.
2. To implement string and pattern matching algorithms.
3. To implement Naive Pattern Matching.
4. To implement Knuth-Morris-Pratt (KMP).
5. To implement Rabin-Karp.
6. To implement Aho-Corasick.
7. To implement Trie-based autocomplete.
8. To implement fuzzy searching using Levenshtein Edit Distance.
9. To calculate similarity percentages.
10. To implement ingredient-based recipe searching.
11. To implement greedy ingredient coverage.
12. To provide random recipe recommendations.
13. To maintain and search a dataset containing 2,000 distinct recipe records.
14. To analyze the complexity of the implemented algorithms.

---

# 4. Dataset

The recipe corpus is stored in:

```text
data/recipes.txt

The dataset contains:

2,000 distinct recipe records

Each recipe contains information such as:

Recipe name
Ingredients
Instructions
Preparation time
Cooking time

Each recipe is identified using the Recipe: header.

Example:

Recipe: Biryani

Ingredients:
Basmati Rice - 2 cups
Chicken - 500 grams
Onion - 2
Spices - As required

Instructions:
1. Wash and soak the rice.
2. Prepare the chicken and spices.
3. Cook the rice and chicken together.
4. Serve hot.

Preparation Time: 20 minutes
Cooking Time: 40 minutes
5. System Features

The application provides the following features:

Recipe name search
Case-insensitive search
Autocomplete suggestions
Trie-based prefix search
Naive Pattern Matching
KMP Pattern Matching
Rabin-Karp Pattern Matching
Aho-Corasick Pattern Matching
Fuzzy Search
Levenshtein Edit Distance
Similarity calculation
Ingredient-based search
Greedy ingredient coverage
Random recipe recommendation
Complete recipe display
Input validation
Recipe-not-found handling
6. Data Structures Used
6.1 ArrayList

The ArrayList is used to store and process the collection of recipe records.

6.2 Trie

A Trie is used for prefix-based recipe-name searching and autocomplete.

When the user enters a prefix such as:

c

the Trie can be used to obtain recipe names beginning with that prefix.

6.3 HashMap

HashMap is used for efficient storage and lookup of recipe-related information.

6.4 HashSet

HashSet is used where unique recipe or ingredient elements are required.

6.5 Queue

Queue is used during the construction and processing of Aho-Corasick failure links.

6.6 Dynamic Programming Table

A dynamic programming table is used for calculating Levenshtein Edit Distance.

7. Algorithms Implemented
7.1 Naive Pattern Matching

The Naive Pattern Matching algorithm compares a search pattern with the text character by character.

It provides a straightforward approach to pattern matching.

Worst-Case Complexity
O(n × m)

where:

n = text length
m = pattern length
7.2 Knuth-Morris-Pratt (KMP)

The KMP algorithm improves pattern matching by avoiding unnecessary comparisons.

It uses an LPS (Longest Prefix Suffix) array to determine the next comparison position after a mismatch.

Complexity
Preprocessing: O(m)
Searching:     O(n)
Overall:       O(n + m)
7.3 Rabin-Karp

The Rabin-Karp algorithm uses hashing to compare a pattern with portions of the text.

A rolling hash is used to efficiently calculate hash values for successive text windows.

Complexity
Average Case: O(n + m)
Worst Case:   O(n × m)
7.4 Aho-Corasick

Aho-Corasick is a multiple-pattern matching algorithm.

It combines:

Trie
Failure links
Queue-based construction

It is designed to search multiple patterns efficiently within a text.

Complexity

For total pattern length P, text length N, and Z matches:

O(P + N + Z)
8. Autocomplete

Autocomplete provides recipe-name suggestions based on the prefix entered by the user.

The feature is implemented using a Trie.

Example:

Search recipe: c

Possible suggestions include:

1. Cake
2. Chole
3. Chicken Curry
4. Chocolate Brownie
5. Chocolate Mug Cake

The user can select a suggestion or continue searching.

The autocomplete feature improves usability and reduces typing effort.

9. Fuzzy Search

Fuzzy Search allows the application to find a close recipe match when the input is incomplete or contains a small spelling difference.

Example:

Search recipe: cak

Possible result:

Closest Recipe: Cake

The similarity percentage is calculated by comparing the entered query with recipe names.

10. Levenshtein Edit Distance

Levenshtein Edit Distance determines the minimum number of operations required to transform one string into another.

The operations are:

Insertion
Deletion
Substitution

The implementation uses Dynamic Programming.

The recurrence is:

dp[i][j] = minimum of

dp[i-1][j] + 1
dp[i][j-1] + 1
dp[i-1][j-1] + cost

where cost is:

0 if characters are equal
1 if characters are different
Complexity
Time Complexity:  O(n × m)
Space Complexity: O(n × m)
11. Similarity Calculation

The application converts edit distance into a similarity percentage.

The conceptual formula is:

Similarity = (1 - Edit Distance / Maximum String Length) × 100

The similarity value is used to identify the closest matching recipe.

The actual percentage depends on the input and the recipe being compared.

12. Ingredient-Based Search

The application supports searching recipes based on multiple ingredients.

Example:

ingredient: chicken,onion,tomato

The application compares the requested ingredients with recipe ingredients and identifies relevant recipes.

This feature demonstrates ingredient-based retrieval using data structures and matching techniques.

13. Greedy Recipe Coverage

The project implements a greedy approach for ingredient coverage.

For a set of requested ingredients, the application evaluates recipes according to the number of requested ingredients they cover.

Example:

chicken, onion, tomato

The greedy approach repeatedly selects a recipe that provides useful coverage of the requested ingredients.

This demonstrates the application of a greedy strategy to an optimization-oriented recipe-selection problem.

14. Random Recipe Recommendation

The application supports random recipe recommendation.

The user can enter:

random

The application selects a recipe from the available recipe collection and displays it.

This provides an additional recommendation feature when the user does not have a specific recipe in mind.

15. Case-Insensitive Search

The application supports case-insensitive searching.

For example:

Biryani
biryani
BIRYANI
BiRyAnI

can be treated as the same recipe search.

This improves usability by avoiding dependence on exact capitalization.

16. Application Workflow

The application follows the following workflow:

Start
  ↓
Load Recipe Dataset
  ↓
Build Recipe Structures
  ↓
Build Trie
  ↓
Accept User Query
  ↓
Generate Autocomplete Suggestions
  ↓
User Selects / Enters Recipe
  ↓
Perform Pattern Matching or Fuzzy Search
  ↓
Calculate Similarity if Required
  ↓
Display Recipe
  ↓
Continue Searching
  ↓
Exit

Ingredient queries follow the ingredient matching and coverage process.

17. User Interface

The application uses a normal search-based interface.

The user is prompted with:

Search recipe:

The user can enter:

Complete recipe name
Partial recipe name
Ingredient query
Coverage query
Random recommendation command

The application automatically provides recipe suggestions for partial recipe names.

The algorithms are not exposed as a separate algorithm-selection menu.

18. Example Test Cases
Test Case	Input	Expected Result
Exact Search	Cake	Cake recipe displayed
Case-Insensitive Search	CAKE	Cake recipe identified
Prefix Search	c	Recipe suggestions displayed
Fuzzy Search	cak	Closest recipe identified
Ingredient Search	ingredient: chicken,onion,tomato	Matching recipes displayed
Coverage	cover: chicken,onion,tomato	Ingredient coverage results displayed
Random Recommendation	random	Random recipe displayed
Empty Input	Enter	Input validation message
Invalid Recipe	Burger	Recipe-not-found handling
19. Results

The application was tested using different types of inputs.

Pattern Matching Results

The following recipe names were tested:

Cake
Pasta
Biryani
Pizza

The application successfully identifies matching recipes and displays the corresponding recipe information.

Autocomplete Result

Example:

Search recipe: c

The application displays recipe suggestions beginning with the entered prefix.

Fuzzy Search Result

Example:

Search recipe: cak

The application identifies the closest recipe.

Closest Recipe: Cake

The similarity percentage is obtained from the actual program output.

Ingredient Search Result

Example:

ingredient: chicken,onion,tomato

The application searches the recipe corpus for recipes containing the requested ingredients.

Recipe Coverage Result

Example:

cover: chicken,onion,tomato

The application evaluates recipes according to the requested ingredient coverage.

Random Recommendation Result

Example:

random

The application displays a randomly selected recipe.

Invalid Input Result

For an empty input, the application displays an input validation message.

For an unavailable recipe such as:

Burger

the application displays a recipe-not-found message.

20. CO Mapping
CO1 – Problem Analysis and Algorithm Selection

The project analyzes the recipe-search problem and identifies suitable data structures and algorithms.

The implementation demonstrates:

Problem decomposition
Recipe corpus organization
Data structure selection
Algorithm selection
Complexity analysis
Search workflow design
CO2 – Pattern Matching

The project implements multiple pattern matching algorithms:

Naive Pattern Matching
Knuth-Morris-Pratt (KMP)
Rabin-Karp
Aho-Corasick

These algorithms demonstrate different approaches to pattern searching in recipe-related text.

CO3 – Similarity and Fuzzy Searching

The project implements:

Levenshtein Edit Distance
Dynamic Programming
Similarity calculation
Fuzzy recipe searching
Closest recipe identification

These techniques allow the application to handle incomplete and slightly misspelled recipe names.

CO4 – Ingredient Matching

The project implements ingredient-based recipe matching.

The application:

Accepts multiple ingredients.
Compares requested ingredients with recipe ingredients.
Identifies relevant recipes.
Processes ingredient matching using appropriate data structures.
CO5 – Greedy Coverage

The project applies a greedy approach to ingredient coverage.

The algorithm evaluates available recipes and repeatedly selects recipes that provide useful coverage of the requested ingredients.

This demonstrates the use of a greedy strategy for a recipe-selection optimization problem.

CO6 – Recommendation and Advanced Processing

The project includes recommendation-oriented functionality.

The implementation includes:

Random recipe recommendation
Recipe selection from the dataset
Similarity-based processing
Efficient recipe processing

The recommendation feature provides additional recipe suggestions to the user.

21. Complexity Analysis
Algorithm / Data Structure	Time Complexity
Naive Pattern Matching	O(n × m) worst case
KMP	O(n + m)
Rabin-Karp	O(n + m) average / O(n × m) worst case
Aho-Corasick	O(P + N + Z)
Trie Prefix Search	O(p + k) approximately
Levenshtein Distance	O(n × m)
Greedy Coverage	Depends on number of recipes and requested ingredients
Random Recommendation	O(1) for random selection after dataset loading

Where:

n = text length
m = pattern length
P = total length of patterns
N = text length
Z = number of matches
p = prefix length
k = number of returned suggestions
22. Repository Structure
DSA3_Project
│
├── src
│   └── RecipeFinder.java
│
├── data
│   └── recipes.txt
│
├── docs
│   ├── RecipeFinder.pptx
│   ├── RecipeFinder_Review-2_ppt_FINAL.pptx
│   └── Team No_13_DSA-3_Project Abstract.docx
│
├── results
│   ├── Pattern_Matching_Cake.png
│   ├── Pattern_Matching_Pasta.png
│   ├── Fuzzy_Search_cak.png
│   ├── Fuzzy_Search_Pizza_Exact.png
│   ├── Empty_Input.png
│   └── Recipe_Not_Found_Burger.png
│
├── reports
│   └── README.md
│
└── README.md
23. Execution
Requirements
Java Development Kit (JDK)
VS Code, IntelliJ IDEA, or Eclipse
Git/GitHub
Compile
javac src/RecipeFinder.java
Run
java -cp src RecipeFinder

The application starts with the recipe search interface.

Example:

RECIPE FINDER

Search recipe:
24. Current Project Status

The project has progressed from basic recipe searching to a DSA-oriented recipe retrieval application.

Completed Components
GitHub repository created
Required project structure created
Recipe corpus prepared
2,000 distinct recipe records maintained
Java implementation completed
Recipe loading and parsing implemented
Naive Pattern Matching implemented
KMP implemented
Rabin-Karp implemented
Aho-Corasick implemented
Trie-based autocomplete implemented
Case-insensitive searching implemented
Levenshtein Edit Distance implemented
Fuzzy Search implemented
Similarity calculation implemented
Ingredient-based matching implemented
Greedy coverage implemented
Random recommendation implemented
Testing performed
Output screenshots stored in results/
25. Evidence and Results Files

Testing evidence is maintained in the results directory.

The results include screenshots demonstrating:

Pattern matching
Fuzzy search
Autocomplete/search behavior
Empty input handling
Recipe-not-found handling

The source implementation is available in:

src/RecipeFinder.java

The recipe dataset is available in:

data/recipes.txt
26. Future Scope

Future improvements may include:

Graph-based recipe relationships
More advanced recommendation algorithms
Nutritional information
User-specific recipe preferences
Improved search-result ranking
Larger real-world recipe datasets
Web-based or graphical user interface
Additional optimization techniques
Performance comparison of implemented algorithms
27. Conclusion

The Recipe Finder Using String Matching and Similarity Algorithms demonstrates the practical application of Data Structures and Algorithms to a real-world recipe-search problem.

The project integrates multiple pattern matching algorithms, including Naive Pattern Matching, KMP, Rabin-Karp, and Aho-Corasick. It also incorporates Trie-based autocomplete, fuzzy searching using Levenshtein Edit Distance, similarity calculation, ingredient matching, greedy ingredient coverage, and recipe recommendation.

The application provides a simple search-based user interface while internally applying different algorithmic techniques to process and retrieve recipe information.

The project demonstrates the practical use of data structures and algorithms for searching, matching, similarity analysis, optimization, and recommendation in a recipe retrieval system.


### Your `reports` folder should therefore look like this

```text
reports/
└── README.md

