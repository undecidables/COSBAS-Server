package cosbas.biometric.serialize;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterResponse;
import cosbas.user.ContactDetail;
import cosbas.user.ContactTypes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    /**
     * Parses an {@link HttpServletRequest} into an {@link AccessRequest} object.
     * @param request Data to be parsed into request object
     * @return Parsed request object
     * @throws IOException When request.getParts fails.
     * @throws ServletException When request.getParts fails.
     * @throws IllegalArgumentException When the request action or a biometric type Invalid.
     * @throws NullPointerException When the request parts is null.
     * @see BiometricTypes
     * @see DoorActions
     */
    public AccessRequest parseRequest(HttpServletRequest request) throws IOException, ServletException, IllegalArgumentException, NullPointerException {

        Collection<Part> parts = request.getParts();
        List<BiometricData> biometricDatas = new ArrayList<>();

        String id = request.getParameter("ID");
        DoorActions action = DoorActions.fromString(request.getParameter("Action"));

        if(parts != null)
        {
            for(Part part : parts)
            {
                String name = part.getName().toLowerCase();

                if (!name.contains("id") && !name.contains("action")) {
                    BiometricData data = getBiometricData(part);
                    biometricDatas.add(data);

                }
            }

            return new AccessRequest(id, action, biometricDatas);
        }
        throw new NullPointerException("Parts null, unable to parse http request ");
    }

    private BiometricData getBiometricData(Part part) throws IOException {
        byte[] file = null;
        InputStream partInputStream=part.getInputStream();
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        byte[] chunk=new byte[4096];
        int amountRead;
        while ((amountRead=partInputStream.read(chunk)) != -1) {
            outputStream.write(chunk,0,amountRead);
        }
        if (outputStream.size() != 0) {
            file = outputStream.toByteArray();
        }

        return new BiometricData(BiometricTypes.fromString(part.getName()),file);
    }

    /**
     * Parses the POST request data into a Java Object
     *
     * @param request The user's e-mail address
     * @return A RegisterRequest Java Object with the POST request data
     */
    public RegisterRequest parseRegisterRequest(HttpServletRequest request) throws IOException, ServletException, IllegalArgumentException, NullPointerException {

        Collection<Part> parts = request.getParts();
        List<BiometricData> biometricDatas = new ArrayList<>();

        String id = request.getParameter("personID");
        String email = request.getParameter("email");

        if(parts != null)
        {
            for(Part part : parts)
            {
                String name = part.getName().toLowerCase();
                if (!name.contains("email") && !name.contains("personid")) {
                    BiometricData data = getBiometricData(part);
                    biometricDatas.add(data);
                }
            }
            ArrayList<ContactDetail> cList = new ArrayList<>(1);
                   cList.add(new ContactDetail(ContactTypes.EMAIL, email));
            return new RegisterRequest(cList, id, biometricDatas);
        }
        throw new NullPointerException("Parts null, unable to parse http request ");
    }

}
