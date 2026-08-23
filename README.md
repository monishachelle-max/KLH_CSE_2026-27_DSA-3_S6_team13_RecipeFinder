# Recipe Finder Using String Matching

## Team Members

| S.No | Team Member Name | ID Number |
|------|------------------|-----------|
| 1 | Monisha Raghini Chelle | 2520030537 |
| 2 | Shaik Sabiya Tabassum | 2520030502 |
| 3 | K. Siri Chandana | 2520030453 |

## Supervisor

**Dr. V. Sireesha**  
Professor, Department of CSE

## Abstract

The Recipe Finder is a Java-based application developed to search and retrieve recipes from a text-based recipe corpus. The project demonstrates the implementation of a String/Pattern Matching Algorithm on project data files.

The application stores multiple recipes in the `data/recipes.txt` corpus. The user enters a recipe name, and the application searches the corpus using the Naive Pattern Matching algorithm. When a matching recipe is found, the complete recipe information is displayed.

## Setup Instructions

### Requirements

- Java Development Kit (JDK)
- Java-compatible IDE such as IntelliJ IDEA, Eclipse, or VS Code
- Git/GitHub

The project should contain:

```text
src/RecipeFinder.java
data/recipes.txt
```

Make sure the `recipes.txt` file is inside the `data` folder.

## Execution Instructions

Compile the program:

```bash
javac src/RecipeFinder.java
```

Run the program:

```bash
java -cp src RecipeFinder
```

Enter a recipe name when prompted.

Example:

```text
Enter recipe name: Biryani
```

The program searches the recipe corpus using the Naive Pattern Matching algorithm and displays the matching recipe.

## Current Phase Status

**Review-2 – String/Pattern Matching Algorithm Implementation**

### Completed

- Recipe corpus created in `data/recipes.txt`
- Java implementation completed
- Naive Pattern Matching algorithm implemented
- Recipe search implemented
- Case-insensitive matching implemented
- Recipe output tested successfully
- Output screenshots stored in `results/`

## Project Structure

```text
Repository
│
├── src
│   └── RecipeFinder.java
│
├── data
│   └── recipes.txt
│
├── docs
│
├── results
│
├── reports
│
└── README.md
```

## Algorithm Used

The project implements the **Naive Pattern Matching Algorithm** for searching recipe names in the recipe corpus.

The algorithm compares the search pattern with the recipe name character by character. The matching is case-insensitive.

For example, the following searches can find the recipe `Biryani`:

```text
Biryani
biryani
BIRYANI
BiRyAnI
```

## Corpus

The recipe corpus is stored in:

```text
data/recipes.txt
```

The corpus contains multiple recipes with:

- Recipe name
- Ingredients
- Instructions
- Preparation time
- Cooking time

## Results

The application successfully searches the recipe corpus and displays the complete recipe when a matching recipe is found.

Example:

```text
Enter recipe name: Biryani

========== RECIPE FOUND ==========

Recipe: Biryani
Ingredients:
Basmati Rice - 2 cups
Chicken - 500 grams
...

==================================
```

If the recipe is not found, the program displays:

```text
Recipe not found.
Please enter a valid recipe name.
```
Make sure the recipes.txt file is inside the data folder.

Execution Instructions

Compile the program:

javac src/RecipeFinder.java

Run the program:

java -cp src RecipeFinder

The application displays the following menu:

1. Pattern Matching
2. Fuzzy Search
3. Exit

Select an option and enter the recipe name when prompted.

Example for Pattern Matching:

Enter your choice: 1
Enter recipe name: Biryani

Example for Fuzzy Search:

Enter your choice: 2
Enter recipe name: Biriyani

The program searches the recipe corpus and displays the matching or closest recipe.

Current Phase Status

Review-2 – Partial Implementation of String Matching, Fuzzy Search and Similarity Algorithms

Completed
GitHub repository created
Required project folder structure created
Recipe corpus created in data/recipes.txt
Java implementation completed
Naive Pattern Matching algorithm implemented
Pattern-based recipe search implemented
Case-insensitive matching implemented
Levenshtein Edit Distance implemented
Fuzzy Search implemented
Similarity percentage calculation implemented
70% similarity threshold used for fuzzy matching
Complete recipe information displayed for matching results
Pattern Matching and Fuzzy Search tested using the recipe corpus
Output screenshots stored in results/
Project Structure
Repository

│
├── src
│   └── RecipeFinder.java
│
├── data
│   └── recipes.txt
│
├── docs
│   ├── RecipeFinder.pptx
│   └── Team No_13_DSA-3_Project Abstract.docx
│
├── results
│
├── reports
│
└── README.md
Algorithm Used

The project implements the Naive Pattern Matching Algorithm for searching recipe names in the recipe corpus.

The algorithm compares the search pattern with the recipe name character by character. The implementation is case-insensitive.

The project also implements Fuzzy Search using Levenshtein Edit Distance. This allows the application to find the closest recipe when the entered recipe name contains small spelling differences.

The similarity percentage is calculated using the edit distance between the entered name and each recipe name.

For example, the following searches can find the recipe Biryani using case-insensitive Pattern Matching:

Biryani
biryani
BIRYANI
BiRyAnI

Fuzzy Search can also handle small differences in the entered recipe name.

Corpus

The recipe corpus is stored in:

data/recipes.txt

The corpus contains multiple recipes with:

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

Preparation Time: 20 minutes
Cooking Time: 40 minutes

Instructions:
1. Wash and soak the rice.
2. Prepare the chicken and spices.
3. Cook the rice and chicken together.
4. Serve hot.
Results

The application successfully searches the recipe corpus using both Pattern Matching and Fuzzy Search.

Pattern Matching Result

Example:

======================================
             RECIPE FINDER
======================================

1. Pattern Matching
2. Fuzzy Search
3. Exit

Enter your choice: 1
Enter recipe name: Biryani

======================================
             RECIPE FOUND
======================================

Recipe: Biryani
Ingredients:
Basmati Rice - 2 cups
Chicken - 500 grams
Onion - 2
Spices - As required

Preparation Time: 20 minutes
Cooking Time: 40 minutes

Instructions:
1. Wash and soak the rice.
2. Prepare the chicken and spices.
3. Cook the rice and chicken together.
4. Serve hot.

======================================
Fuzzy Search Result

Example:

======================================
             RECIPE FINDER
======================================

1. Pattern Matching
2. Fuzzy Search
3. Exit

Enter your choice: 2
Enter recipe name: Biriyani

======================================
          FUZZY MATCH FOUND
======================================

Closest Recipe: Biryani
Similarity: 88.89%

======================================
             RECIPE FOUND
======================================

Recipe: Biryani
Ingredients:
Basmati Rice - 2 cups
Chicken - 500 grams
Onion - 2
Spices - As required

Preparation Time: 20 minutes
Cooking Time: 40 minutes

Instructions:
1. Wash and soak the rice.
2. Prepare the chicken and spices.
3. Cook the rice and chicken together.
4. Serve hot.

======================================

If no recipe reaches the required similarity threshold, the program displays:

Recipe not found.

The output screenshots obtained during testing are stored in the results/ folder.



**One important thing:** in the Fuzzy Search example, `88.89%` is only an example. Your screenshot should show the **actual percentage produced by your program**, not necessarily 88.89%.