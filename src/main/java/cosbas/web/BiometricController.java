/**
 * {@author Renette}
 * MVC COntroller for biometric system
 * Responds with plain text to requests
 */

package cosbas.web;

import cosbas.biometric.BiometricSystem;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterResponse;
import cosbas.biometric.serialize.BiometricSerializer;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
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
    public String access(HttpServletRequest request) throws IOException, ServletException, BiometricTypeException {

        AccessRequest aRequest = serializer.parseRequest(request);
        AccessResponse response = authSystem.requestAccess(aRequest);
        return serializer.serializeResponse(response);
    }

    @RequestMapping(/*method= RequestMethod.POST, */value="/biometrics/registration", method= RequestMethod.POST)
    public String registration(HttpServletRequest request) throws IOException, ServletException, BiometricTypeException {
        System.out.println("GOT HERE!");
        RegisterRequest aRequest = serializer.parseRegisterRequest(request);
        RegisterResponse response = authSystem.register(aRequest);
        return serializer.serializeResponse(response);
    }

}
