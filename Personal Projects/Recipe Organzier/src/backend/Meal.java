package backend;

import java.util.Collections;
import java.util.Set;

public class Meal {
    private final Set<Recipe> recipes;

    /**
     * All of the ingredients needed for the meal, "condensed"
     * from their representations within each recipe.
     */
    private final Set<Ingredient> ingredients;
    private final int numberOfRecipes;

    /**
     * One portion = one person served at this meal.
     */
    private final int portions;
    private final MealType mealType;
    private final Set<MealPart> mealParts;

    public Meal(Set<Recipe> recipes, Set<Ingredient> ingredients,
            int portions, MealType mealType, Set<MealPart> mealParts) {
        this.recipes = Collections.unmodifiableSet(recipes);
        this.ingredients = Collections.unmodifiableSet(ingredients);
        numberOfRecipes = recipes.size();
        this.portions = portions;
        this.mealType = mealType;
        this.mealParts = Collections.unmodifiableSet(mealParts);
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getNumberOfRecipes() {
        return numberOfRecipes;
    }

    public int getPortions() {
        return portions;
    }

    public MealType getMealType() {
        return mealType;
    }

    public Set<MealPart> getMealParts() {
        return mealParts;
    }
}
