package biometric;

/**
 * Created by Renette on 2015-06-26.
 * A user with a user id and biometric data to be registered on ths system.
 */
public class BiometricUser {
    /**
     * User's login ID.
     */
    String userID;

    /**
     * Array of BiometricData Objects for user.
     */
    BiometricData[] data;
}
