package cosbas.biometric.validators.facial;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;

/**
 * {@author Renette}
 */
public abstract class RecognizerDataComponent {
    @Id
    private String id;
    private final String field;

    public LocalDateTime getSaved() {
        return saved;
    }

    private final LocalDateTime saved;

    @PersistenceConstructor
    protected RecognizerDataComponent(String id, String field, LocalDateTime saved) {
        this.id = id;
        this.field = field;
        this.saved = saved;
    }

    protected RecognizerDataComponent(String field, LocalDateTime saved) {
        this.field = field;
        this.saved = saved;
    }

    public String getField() {
        return field;
    }

    public String getId() {
        return id;
    }
}
