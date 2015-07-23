package biometric.serialize;

import biometric.AccessResponse;
import com.google.gson.Gson;

/**
 * @autor Renette
 * Handles JSON conversion for Biometric System.
 * Could be replaced by another serializer if something other than JSON is preferred.
 */


public class JSONSerializer extends BiometricSerializer {

    public JSONSerializer() {super();}

    @Override
    public String serializeResponse(AccessResponse response) {
        Gson gson = new Gson();
        //TODO: Fine Tune, everything does not need to be serialized.
         return gson.toJson(response);
    }

}
