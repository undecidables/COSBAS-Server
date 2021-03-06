/**
 * {@author Renette}
 * MVC Controller for biometric system
 * Responds with plain text to requests
 */

package cosbas.web;

import cosbas.biometric.BiometricSystem;
import cosbas.biometric.parser.BiometricParser;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterResponse;
import cosbas.biometric.serialize.BiometricSerializer;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.biometric.validators.exceptions.ValidationException;
import cosbas.permissions.PermissionManager;
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
    final BiometricParser parser;
    final BiometricSystem authSystem;

    @Autowired
    PermissionManager permissionManager;

    @Autowired
    public BiometricController(BiometricSerializer serializer, BiometricParser parser ,BiometricSystem authSystem) {
        this.serializer = serializer;
        this.parser = parser;
        this.authSystem = authSystem;

    }

    @RequestMapping(/*method= RequestMethod.POST, */value="/biometrics/access", method= RequestMethod.POST)
    public String access(HttpServletRequest request) throws IOException, ServletException, ValidationException {
        AccessRequest aRequest = parser.parseRequest(request);
        AccessResponse response = authSystem.requestAccess(aRequest);
        return serializer.serializeResponse(response);
    }

    @RequestMapping(/*method= RequestMethod.POST, */value="/biometrics/registration", method= RequestMethod.POST)
    public String registration(HttpServletRequest request) throws IOException, ServletException, BiometricTypeException {
        RegisterRequest aRequest = null;
        try {
            aRequest = parser.parseRegisterRequest(request);
            RegisterResponse response = authSystem.register(aRequest);
            return serializer.serializeResponse(response);
        } catch (Exception e) {
            return serializer.serializeResponse(RegisterResponse.getFailureResponse(aRequest, e.getMessage()));
        }
    }

}
