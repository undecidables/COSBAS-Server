/**
 * @author Renette
 * MVC COntroller for biometric system
 * Responds with plain text to requests
 */

package web;

import biometric.request.AccessRequest;
import biometric.request.AccessResponse;
import biometric.BiometricSystem;
import biometric.serialize.BiometricSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;


@RestController
public class BiometricController {

    final BiometricSerializer serializer;
    final BiometricSystem authSystem;

    @Autowired
    public BiometricController(BiometricSerializer serializer, BiometricSystem authSystem) {
        this.serializer = serializer;
        this.authSystem = authSystem;
    }

    @RequestMapping(/*method= RequestMethod.POST, */value="/biometrics/access", method= RequestMethod.POST)
    public String access(HttpServletRequest request) throws IOException, ServletException {

        AccessRequest aRequest = serializer.parseRequest(request);
        AccessResponse response = authSystem.requestAccess(aRequest);
        return serializer.serializeResponse(response);
    }

}
