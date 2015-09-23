package cosbas.biometric.serialize;

import com.google.gson.Gson;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.registration.RegisterResponse;
import org.springframework.stereotype.Component;


/**
 * {@author Renette Ros}
 * Concrete strategy to serialize response objects to JSON
 */

@Component
public class JSONSerializer extends BiometricSerializer {

    public JSONSerializer() {super();}

    @Override
    public String serializeResponse(AccessResponse response) {
        Gson gson = new Gson();
        //TODO: Fine Tune, everything does not need to be serialized.
         return gson.toJson(response);
    }

    @Override
    public String serializeResponse(RegisterResponse response) {
        Gson gson = new Gson();
        //TODO: Fine Tune, everything does not need to be serialized.
        return gson.toJson(response);
    }

}
