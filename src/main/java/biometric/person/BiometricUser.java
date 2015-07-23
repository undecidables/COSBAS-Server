package biometric.person;
import biometric.request.BiometricData;
import org.springframework.data.annotation.Id;
/**
 * @author Renette
 * A user with a user id and biometric data to be registered on ths system.
 */
public abstract class BiometricUser {

    @Id
   protected String userID;

    /**
     * Array of BiometricData Objects for user.
     */
    protected BiometricData[] data;
}
