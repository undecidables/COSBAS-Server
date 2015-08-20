package cosbas.biometric.validators;

/**
 * @author Renette
 */
public enum DoorActions  {
    IN, OUT;

    /**
     * Wrapper for valueOf that first makes the value uppercase.
     * @param value The value to be converted to a Biometric Type
     * @return Biometric Type for the string
     */
    public static DoorActions fromString(String value) {
        return DoorActions.valueOf(value.toUpperCase());
    }
}
