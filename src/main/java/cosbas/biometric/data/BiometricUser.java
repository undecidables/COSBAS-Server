package cosbas.biometric.data;
import org.springframework.data.annotation.Id;
/**
 * {@author Renette Ros}
 * A user with a user id and biometric data to be registered on ths system.
 */
public abstract class BiometricUser {

    @Id
   protected String userID;

    /**
     * Array of BiometricData Objects for user.
     * TODO this might change since it is hard to query embedded docs
     */
    protected BiometricData[] data;
}
