package cosbas.user;

import org.springframework.data.annotation.PersistenceConstructor;

/**
 * {@author Renette}
 */
public class ContactDetail {
    /**
     * The actual contact info (detail address, phone number etc)
     */
    protected final String details;
    private final ContactTypes type;

    @PersistenceConstructor
    public ContactDetail(ContactTypes type, String details) {
        this.type = type;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public ContactTypes getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final ContactDetail other = (ContactDetail) object;
        return details.equals(other.details) && type.equals(other.type);
    }
}

