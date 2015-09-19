package cosbas.user;

/**
 * {@author Renette}
 */
abstract class ContactDetails {
    protected final String details;
    private final ContactTypes type;

    protected ContactDetails(ContactTypes type, String email) {
        this.type = type;
        this.details = email;
    }

    public String getDetails() {
        return details;
    }

    public ContactTypes getType() {
        return type;
    }
}

