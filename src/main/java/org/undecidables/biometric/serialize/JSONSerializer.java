package org.undecidables.biometric.serialize;

import org.undecidables.biometric.request.AccessResponse;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 * @autor Renette
 * Handles JSON conversion for Biometric System.
 * Could be replaced by another serializer if something other than JSON is preferred.
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
