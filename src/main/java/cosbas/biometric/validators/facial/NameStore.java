package cosbas.biometric.validators.facial;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@author Renette
 */
@Component
public class NameStore extends RecognizerDataComponent {

    public List<String> getNames() {
        return names;
    }

    public boolean isNeedsTraining() {
        return needsTraining;
    }

    private final List<String> names;
    private boolean needsTraining;

    @PersistenceConstructor
    protected NameStore(String id, String field, LocalDateTime saved, List<String> names, boolean needsTraining) {
        super(id, field, saved);
        this.names = names;
        this.needsTraining = needsTraining;
    }

    protected NameStore(String field, LocalDateTime saved, List<String> names, boolean needsTraining) {
        super(field, saved);
        this.names = names;
        this.needsTraining = needsTraining;
    }

    public void setNeedsTraining(boolean needsTraining) {
        this.needsTraining = needsTraining;
    }
}
