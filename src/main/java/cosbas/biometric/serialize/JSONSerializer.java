package cosbas.biometric.serialize;

import cosbas.biometric.request.AccessResponse;
import com.google.gson.Gson;
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

}
