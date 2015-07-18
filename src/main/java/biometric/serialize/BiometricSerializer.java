package biometric.serialize;

import biometric.AccessRequest;
import biometric.AccessResponse;
import biometric.BiometricData;

import java.util.ArrayList;

/**
 * Created by Renette on 2015-06-27.
 */
public abstract class BiometricSerializer {
    /**
     * Creates a string from an accessResponse Object
     * @param response Response Object to be serialized
     * @return Serialized response object.
     */
    public abstract String serializeResponse(AccessResponse response);

    /**
     * Parses a string? into a AccessRequest object.
     * @param request Data to be parsed into request object
     * @return Parsed request object
     * TODO: I dont think String is correct input type... What data does http request use?
     */
    public AccessRequest parseRequest(String request) {
        return new AccessRequest("0", "entrance", new ArrayList<BiometricData>());
    }
}
