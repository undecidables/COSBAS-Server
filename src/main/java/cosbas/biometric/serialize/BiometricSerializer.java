package cosbas.biometric.serialize;

import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.registration.RegisterResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * {@author Renette Ros}
 * Abstract strategy for serializing AccessResponses to text and parsing AccessRequests from an {@link HttpServletRequest}.
 */
public abstract class BiometricSerializer {

    /**
     * Creates a string from an accessResponse Object
     * @param response Response Object to be serialized
     * @return Serialized response object.
     */
    public abstract String serializeResponse(AccessResponse response);
    public abstract String serializeResponse(RegisterResponse response);

}
