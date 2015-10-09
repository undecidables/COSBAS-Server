package cosbas.user;

/**
 * {@author Renette}
 */
public enum ContactTypes {
    EMAIL;

    /**
     * Wrapper for valueOf that first makes the value uppercase.
     * @param value The value to be converted to a Contact Type
     * @return ContactType for the string
     */
    public static ContactTypes fromString(String value) {
        return valueOf(value.toUpperCase());
    }
}
