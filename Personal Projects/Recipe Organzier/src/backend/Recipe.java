package backend;

import java.util.Collections;
import java.util.Set;

public class Recipe {
    private final Set<Ingredient> ingredients;
    private final Set<String> instructions;
    private final Set<MealPart> mealParts;
    private final Set<MealType> mealTypes;
    private final int minutestoPrepare;
    private final int minutesToCook;

    /**
     * Total minutes always equal to minutes to prepare + minutes to cook.
     */
    private final int totalMinutes;

    /**
     * One portion = food for one person for one meal.
     */
    private final int portions;

    /**
     * Ingredient constructor treating time to prepare and time to cook separately.
     * @param ingredients
     * @param instructions
     * @param mealParts
     * @param minutesToPrepare
     * @param minutesToCook
     * @param portions
     */
    public Recipe(Set<Ingredient> ingredients, Set<String> instructions,
            Set<MealPart> mealParts, Set<MealType> mealTypes, int minutesToPrepare,
            int minutesToCook, int portions) {
        this.ingredients = Collections.unmodifiableSet(ingredients);
        this.instructions = Collections.unmodifiableSet(instructions);
        this.mealParts = Collections.unmodifiableSet(mealParts);
        this.mealTypes = Collections.unmodifiableSet(mealTypes);
        this.minutestoPrepare = minutesToPrepare;
        this.minutesToCook = minutesToCook;
        totalMinutes = minutesToPrepare + minutesToCook;
        this.portions = portions;
    }

    /**
     * Ingredient constructor treating time to prepare as zero and time to cook/
     * total time as equivalent.
     *
     * TODO: consider removing this constructor
     *
     * @param ingredients
     * @param instructions
     * @param mealParts
     * @param minutesToPrepare
     * @param minutesToCook
     * @param portions
     */
    public Recipe(Set<Ingredient> ingredients, Set<String> instructions,
            Set<MealPart> mealParts, Set<MealType> mealTypes,
            int totalMinutes, int portions) {
        this.ingredients = Collections.unmodifiableSet(ingredients);
        this.instructions = Collections.unmodifiableSet(instructions);
        this.mealParts = Collections.unmodifiableSet(mealParts);
        this.mealTypes = Collections.unmodifiableSet(mealTypes);
        minutestoPrepare = 0;
        this.minutesToCook = totalMinutes;
        this.totalMinutes = totalMinutes;
        this.portions = portions;
    }

    // TODO: constructor with url to recipe site

    /**
     * Returns the immodifiable set of ingredients.
     * @return ingredients
     */
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Returns the immodifiable set of instructions.
     * @return instructions
     */
    public Set<String> getInstructions() {
        return instructions;
    }

    /**
     * Returns the immodifiable set of meal parts.
     * @return meal parts
     */
    public Set<MealPart> getMealParts() {
        return mealParts;
    }

    /**
     * Returns the immodifiable set of meal types.
     * @return meal types
     */
    public Set<MealType> getMealTypes() {
        return mealTypes;
    }

    public int getMinutesToCook() {
        return minutesToCook;
    }

    public int getMinutestoPrepare() {
        return minutestoPrepare;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public int getPortions() {
        return portions;
    }
}
