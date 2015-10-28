package cosbas.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
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
        return new EqualsBuilder()
                .append(details, other.details)
                .append(type, other.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(79, 113)
                .append(details)
                .append(type)
                .toHashCode();
    }
}

