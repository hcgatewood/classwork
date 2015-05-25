package backend;

public class Ingredient {
    private final String name;
    private final double quantity;
    private final Unit unit;

    public Ingredient(String name, double quantity, Unit unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public Ingredient changedQuantity (double quantity) {
        return new Ingredient(getName(), quantity, getUnit());
    }

    /**
     * Returns a new ingredient with amount multiplied by the passed factor.
     * @param factor to multiply the current quantity by
     * @return a new ingredient with scaled quantity
     */
    public Ingredient scaledQuantity(double factor) {
        return new Ingredient(getName(), factor*getQuantity(), getUnit());
    }
}
