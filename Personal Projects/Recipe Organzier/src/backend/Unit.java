package backend;

public enum Unit {
    BASE_VOLUME(UnitCategory.VOLUME, 1, 1),
    BASE_MASS(UnitCategory.MASS, 1, 1);

    private final UnitCategory unitCategory;
    private final double basePerUnitFactor;
    private final double unitPerBaseFactor;

    Unit(UnitCategory unitCategory, double basePerUnitFactor, double unitPerBaseFactor) {
        this.unitCategory = unitCategory;
        this.basePerUnitFactor = basePerUnitFactor;
        this.unitPerBaseFactor = unitPerBaseFactor;
    }

    public UnitCategory getUnitCategory() {
        return unitCategory;
    }

    private double getBasePerUnitFactor() {
        return basePerUnitFactor;
    }
    private double getUnitPerBaseFactor() {
        return unitPerBaseFactor;
    }

    public double getConversionFactor(Unit newUnit) throws IllegalArgumentException {
        boolean unitDimensionsMatch = getUnitCategory().equals(newUnit.getUnitCategory());
        boolean unitDimensionsAreApplicable = getUnitCategory().equals(UnitCategory.MASS) ||
                getUnitCategory().equals(UnitCategory.VOLUME);
        if (! (unitDimensionsMatch && unitDimensionsAreApplicable)) {
            throw new IllegalArgumentException();
        }
        return getBasePerUnitFactor() * newUnit.getUnitPerBaseFactor();
    }
}
