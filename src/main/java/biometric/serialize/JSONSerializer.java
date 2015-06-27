package biometric.serialize;

import biometric.AccessRequest;
import biometric.AccessResponse;
import biometric.BiometricData;
import com.google.gson.Gson;


/**
 * Created by Renette on 2015-06-27.
 * Handles JSON conversion for Biometric System.
 * Could be replaced by another serializer if something other than JSON is preferred.
 */
public class JSONSerializer extends BiometricSerializer {
    @Override
    public String serializeResponse(AccessResponse response) {
        Gson gson = new Gson();
        //TODO: Fine Tune, everything does not need to be serialized.
         return gson.toJson(response);
    }

}
