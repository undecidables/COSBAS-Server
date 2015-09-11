package cosbas.calendar_services.authorization;

/**
 * {@author  Renette}
 */
public interface Authorizer {

    void startFlow();

    String buildLoginUrl();
    CredentialWrapper getCredential(String userID, String authCode);
}
