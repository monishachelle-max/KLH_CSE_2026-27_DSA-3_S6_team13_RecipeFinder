# Recipe Finder 

## Team Members

| S.No | Team Member Name | ID Number |
|------|------------------|-----------|
| 1 | Monisha Raghini Chelle | 2520030537 |
| 2 | Shaik Sabiya Tabassum | 2520030502 |
| 3 | K. Siri Chandana | 2520030453 |

## Supervisor

**Dr. V. Sireesha**  
Professor, Department of CSE

---

## Abstract

The **Recipe Finder** is a Java-based application developed to efficiently search and retrieve recipes from a text-based recipe corpus. The project demonstrates the practical application of Data Structures and Algorithms for pattern matching, autocomplete, fuzzy searching, similarity calculation, ingredient matching, recipe coverage, and recommendation.

The application uses a recipe dataset stored in `data/recipes.txt`. Users can enter a complete recipe name or a partial recipe name, and the application provides automatic recipe suggestions. Pattern matching algorithms are used internally to search the recipe corpus, while fuzzy search handles incomplete or slightly misspelled recipe names.

The project incorporates **Naive Pattern Matching, Knuth-Morris-Pratt (KMP), Rabin-Karp, Aho-Corasick, Trie-based Autocomplete, Levenshtein Edit Distance, similarity calculation, ingredient matching, greedy coverage, and randomized recommendation techniques**.

---

## Objectives

The main objectives of the Recipe Finder project are:

- To develop a Java-based recipe search application.
- To implement string and pattern matching algorithms.
- To provide autocomplete suggestions for partial recipe names.
- To implement fuzzy search for incomplete or slightly misspelled recipe names.
- To calculate similarity between the entered query and recipe names.
- To provide ingredient-based recipe searching.
- To implement recipe coverage using a greedy approach.
- To provide randomized recipe recommendations.
- To organize and search a dataset containing **2,000 distinct recipe records**.
- To analyze the time and space complexity of the implemented algorithms.

---

## Features

The application provides the following features:

- Recipe name search
- Case-insensitive searching
- Automatic autocomplete suggestions
- Prefix-based recipe suggestions using Trie
- Naive Pattern Matching
- Knuth-Morris-Pratt (KMP)
- Rabin-Karp
- Aho-Corasick pattern matching
- Fuzzy recipe search
- Levenshtein Edit Distance
- Similarity percentage calculation
- Ingredient-based search
- Ingredient coverage using a greedy approach
- Random recipe recommendation
- Complete recipe information display
- Input validation
- Recipe-not-found handling

The algorithms are used **internally by the application**. They are not presented to the user as a separate algorithm-selection menu.

---

## Dataset

The recipe corpus is stored in:

```text
data/recipes.txt

The dataset contains 2,000 distinct recipe records.

Each recipe record contains information such as:

Recipe name
Ingredients
Instructions
Preparation time
Cooking time

Each recipe is identified using the Recipe: header.

Example Recipe
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
Data Structures Used

The project uses the following data structures:

1. ArrayList

Used to store and process the collection of recipe records.

2. Trie

Used for prefix-based autocomplete and efficient recipe-name suggestions.

3. HashMap

Used for efficient storage and lookup of recipe-related information.

4. HashSet

Used where unique elements and ingredient sets are required.

5. Queue

Used during the construction and traversal of Aho-Corasick failure links.

6. Dynamic Programming Table

Used for calculating Levenshtein Edit Distance.

Algorithms Implemented
1. Naive Pattern Matching

The Naive Pattern Matching algorithm compares the search pattern with the text character by character.

It is used as a basic pattern matching technique for searching recipe-related text.

For a pattern of length m and text of length n, the worst-case time complexity is:

O(n × m)
2. Knuth-Morris-Pratt (KMP)

The Knuth-Morris-Pratt (KMP) algorithm improves pattern matching by avoiding unnecessary comparisons.

It uses the Longest Prefix Suffix (LPS) array to determine where the pattern comparison should continue after a mismatch.

Time Complexity
Preprocessing: O(m)
Searching:     O(n)
Overall:       O(n + m)
3. Rabin-Karp

The Rabin-Karp algorithm uses hashing to compare the search pattern with portions of the recipe text.

A rolling hash is used to efficiently calculate hash values for successive text windows.

Time Complexity
Average Case: O(n + m)
Worst Case:   O(n × m)
4. Aho-Corasick

The Aho-Corasick algorithm is a multiple-pattern matching algorithm.

It combines:

Trie
Failure links
Queue-based construction

The algorithm allows multiple patterns to be searched efficiently within a text corpus.

In the Recipe Finder, Aho-Corasick is included as an additional pattern-matching technique for searching recipe-related text and patterns.

Complexity

If P is the total length of all patterns, N is the text length, and Z is the number of matches:

O(P + N + Z)
Autocomplete

The application provides automatic recipe suggestions based on the characters entered by the user.

Autocomplete is implemented using a Trie data structure.

For example, when the user enters:

c

the application can display recipe names beginning with c, such as:

Cake
Chole
Chicken Curry
Chocolate Brownie
Chocolate Mug Cake

The user can select a suggested recipe or continue searching.

The autocomplete functionality improves usability by reducing the amount of typing required.

Fuzzy Search

Fuzzy Search allows the application to find the closest recipe when the entered name is incomplete or contains a small spelling difference.

For example:

Input:
cak

The application can identify:

Closest Recipe: Cake

Fuzzy searching is implemented using Levenshtein Edit Distance.

Levenshtein Edit Distance

Levenshtein Edit Distance calculates the minimum number of:

Insertions
Deletions
Substitutions

required to transform one string into another.

A dynamic programming approach is used.

The basic recurrence is:

dp[i][j] = minimum of

dp[i-1][j] + 1
dp[i][j-1] + 1
dp[i-1][j-1] + cost

where cost is 0 when the characters match and 1 otherwise.

For strings of lengths n and m:

Time Complexity:  O(n × m)
Space Complexity: O(n × m)
Similarity Calculation

The application converts the edit distance into a similarity percentage.

The similarity is calculated using the relationship between the edit distance and the maximum string length.

Conceptually:

Similarity = (1 - Edit Distance / Maximum String Length) × 100

The resulting percentage is used to identify the closest matching recipe.

The actual similarity percentage displayed by the application depends on the entered query and the recipe being compared.

Ingredient-Based Search

The application supports searching recipes based on ingredients.

Example:

ingredient: chicken,onion,tomato

The application searches the recipe corpus and identifies recipes containing the requested ingredients.

This functionality demonstrates matching between the user's ingredient requirements and the ingredients associated with recipes.

Greedy Recipe Coverage

The project implements a greedy coverage approach for selecting recipes that cover the maximum number of requested ingredients.

For example, when the user provides:

chicken, onion, tomato

the application evaluates recipes according to how many of the requested ingredients they contain.

The greedy approach repeatedly selects a recipe that provides useful coverage of the requested ingredients.

This demonstrates the application of a greedy strategy to a recipe-selection problem.

Randomized Recipe Recommendation

The application provides a random recipe recommendation feature.

When the user enters:

random

the program selects and displays a recipe from the available recipe collection.

This provides an additional recommendation feature for users who do not have a specific recipe in mind.

Case-Insensitive Searching

The application supports case-insensitive recipe searching.

For example, the following inputs can refer to the same recipe:

Biryani
biryani
BIRYANI
BiRyAnI

This improves usability and avoids requiring the user to enter the exact capitalization of a recipe name.

Application Workflow

The overall application workflow is:

Start
  ↓
Load Recipe Dataset
  ↓
Build Recipe Structures
  ↓
Build Trie for Autocomplete
  ↓
Accept User Query
  ↓
Generate Recipe Suggestions
  ↓
User Selects / Enters Recipe
  ↓
Pattern Matching / Fuzzy Search
  ↓
Calculate Similarity if Required
  ↓
Display Matching Recipe
  ↓
Continue Searching
  ↓
Exit

For ingredient-based queries, the application follows the ingredient matching and coverage process.

User Interface

The application uses a normal search-based interface.

The user is prompted with:

Search recipe:

The application provides suggestions based on the entered characters.

For example:

Search recipe: c

Suggestions:
1. Cake
2. Chole
3. Chicken Curry
4. Chocolate Brownie
5. Chocolate Mug Cake

The user can select a suggestion or enter a recipe name.

The algorithms are not exposed as a numbered menu.

Setup Instructions
Requirements
Java Development Kit (JDK)
Java-compatible IDE such as:
VS Code
IntelliJ IDEA
Eclipse
Git/GitHub
Project Structure
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
│
└── README.md
Execution Instructions

Make sure that recipes.txt is located inside the data folder.

Compile the Program
javac src/RecipeFinder.java
Run the Program
java -cp src RecipeFinder

The application then displays the recipe search interface.

Example:

RECIPE FINDER

Search recipe:

The user can enter a complete recipe name, partial recipe name, ingredient query, or supported command.

Example Searches
Exact Recipe Search
Search recipe: Biryani

The application displays the matching recipe.

Autocomplete
Search recipe: c

The application displays recipe suggestions beginning with c.

Fuzzy Search
Search recipe: cak

The application can identify:

Closest Recipe: Cake

with the similarity percentage generated by the program.

Ingredient Search
Search recipe: ingredient: chicken,onion,tomato

The application searches recipes based on the specified ingredients.

Recipe Coverage
Search recipe: cover: chicken,onion,tomato

The greedy coverage functionality evaluates recipes according to ingredient coverage.

Random Recommendation
Search recipe: random

The application displays a randomly selected recipe.

Results

The application was tested using different types of inputs.

Pattern Matching

Test cases include:

Cake
Pasta
Biryani
Pizza

The application successfully identifies matching recipes and displays their complete information.

Fuzzy Search

Example:

Input: cak
Closest Recipe: Cake

The similarity percentage displayed should be taken directly from the actual program output.

Autocomplete

Example:

Input: c

The application displays matching recipe-name suggestions beginning with the entered prefix.

Ingredient Search

Example:

ingredient: chicken,onion,tomato

The application searches the recipe corpus using the specified ingredients.

Invalid Input

For an empty input, the application requests the user to enter a recipe name.

For an unavailable recipe such as:

Burger

the application displays an appropriate recipe-not-found message.

The output screenshots obtained during testing are stored in:

results/
CO Mapping
CO1 – Problem Analysis and Algorithm Selection

The project analyzes the recipe-search problem and identifies suitable data structures and algorithms.

Implemented concepts include:

Problem decomposition
Recipe corpus organization
Algorithm selection
Time and space complexity analysis
Search workflow design
CO2 – Pattern Matching Algorithms

The project implements multiple pattern matching algorithms:

Naive Pattern Matching
Knuth-Morris-Pratt (KMP)
Rabin-Karp
Aho-Corasick

These algorithms demonstrate different approaches to searching patterns in recipe-related text.

CO3 – Similarity and Fuzzy Searching

The project implements:

Levenshtein Edit Distance
Dynamic Programming
Similarity percentage calculation
Fuzzy recipe search
Closest recipe identification

These techniques allow the application to handle incomplete and slightly misspelled recipe names.

CO4 – Ingredient Matching

The project implements ingredient-based recipe matching.

The application:

Accepts multiple ingredients from the user.
Compares them with recipe ingredients.
Identifies recipes containing the requested ingredients.
Uses matching logic to determine relevant recipes.

This demonstrates the application of data structures and matching techniques to ingredient-based retrieval.

CO5 – Greedy Coverage

The project applies a greedy algorithm for recipe and ingredient coverage.

The algorithm evaluates recipes and repeatedly selects a recipe that provides useful coverage of the requested ingredients.

This demonstrates the use of a greedy strategy for an optimization-oriented recipe selection problem.

CO6 – Recommendation and Advanced Processing

The project includes recommendation-oriented functionality.

Implemented features include:

Random recipe recommendation
Recipe selection from the available dataset
Similarity-based processing
Efficient processing of recipe information

The recommendation functionality provides users with recipe suggestions when they do not have a specific recipe in mind.

Complexity Analysis
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
P = total length of all patterns
N = text length
Z = number of Aho-Corasick matches
p = prefix length
k = number of returned suggestions
Testing

The application was tested using the following cases:

Test Case	Input	Expected Result
Exact search	Cake	Cake recipe displayed
Case-insensitive search	CAKE	Cake recipe identified
Prefix search	c	Recipe suggestions displayed
Fuzzy search	cak	Closest recipe identified
Ingredient search	ingredient: chicken,onion,tomato	Matching recipes displayed
Coverage	cover: chicken,onion,tomato	Coverage results displayed
Random recommendation	random	Random recipe displayed
Empty input	Enter	Input validation message
Invalid recipe	Burger	Recipe-not-found handling
Current Project Status

The project has progressed from basic recipe searching to a complete DSA-oriented recipe retrieval application.

Completed
GitHub repository created
Required repository structure created
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
Multiple test cases performed
Output screenshots stored in results/
GitHub Repository

The project repository is maintained on GitHub:

Recipe Finder – DSA-3 Team 13

GitHub Repository

The repository contains the source code, dataset, documentation, results, reports, and README.

Future Scope

Future improvements may include:

Graph-based recipe relationships
More advanced recommendation techniques
Nutritional information
User-specific recipe preferences
Improved ranking of search results
Larger real-world recipe datasets
Web-based or graphical user interface
Additional optimization techniques
Performance comparison of all implemented algorithms
Conclusion

The Recipe Finder Using String Matching and Similarity Algorithms demonstrates the practical application of Data Structures and Algorithms to a recipe-search problem.

The project integrates multiple pattern matching algorithms, Trie-based autocomplete, fuzzy searching using Levenshtein Edit Distance, similarity calculation, ingredient matching, greedy coverage, and recommendation functionality.

The application provides a user-friendly search interface while internally applying the implemented algorithms to process and retrieve recipe information from the dataset.

The project demonstrates the practical implementation of different algorithmic techniques in a real-world search application.


### Important before you commit

There are **two things I would verify against your actual latest Java file before calling this 100% final**:

1. Whether **HashMap and HashSet** are actually used in your current `RecipeFinder.java`.
2. Whether your **CO4, CO5 and CO6 implementations** exactly match the descriptions above.

Your earlier uploaded Java/PPT files are no longer accessible to me, so I don't want t