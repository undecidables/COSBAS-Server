package cosbas.biometric.serialize;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.request.access.AccessRequest;
import cosbas.biometric.request.access.AccessResponse;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.request.registration.RegisterRequest;
import cosbas.biometric.request.registration.RegisterResponse;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.user.ContactDetail;
import cosbas.user.ContactTypes;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Value("${request.pattern.biometric.text:biometric-(.*)(-\\d+)?}")
    private String biometricPatternString;
    @Value("${request.pattern.biometric.group:1}")
    private int biometricPatternGroup;

    @Value("${request.pattern.contact.text:contact-(.*)(-\\d+)?}")
    private String contactPatternString;
    @Value("${request.pattern.contact.group:1}")
    private int contactPatternGroup;


    private Pattern biometricPattern = null;
    private Pattern contactPattern = null;

    @PostConstruct
    private void init() {
        biometricPattern = Pattern.compile(biometricPatternString, Pattern.CASE_INSENSITIVE);
        contactPattern = Pattern.compile(contactPatternString, Pattern.CASE_INSENSITIVE);
    }


    /**
     * Parses an {@link HttpServletRequest} into an {@link AccessRequest} object.
     * @param request Data to be parsed into request object
     * @return Parsed request object
     * @throws IOException When request.getParts fails.
     * @throws ServletException When request.getParts fails.
     * @throws IllegalArgumentException When the request action or a biometric type Invalid.
     * @throws NullPointerException When the request parts is null.
     * @throws BiometricTypeException When the BioemtricType is invalid
     * @see BiometricTypes
     * @see DoorActions
     */
    public AccessRequest parseRequest(HttpServletRequest request)
            throws IOException, ServletException, IllegalArgumentException, NullPointerException, BiometricTypeException {

        Collection<Part> parts = request.getParts();
        List<BiometricData> biometricDatas = new ArrayList<>();

        String id = request.getParameter("ID");
        DoorActions action = DoorActions.fromString(request.getParameter("Action"));

        if(parts != null)
        {
            for(Part part : parts)
            {
                String name = part.getName();
                checkBiometricData(part, name, biometricDatas);
            }

            return new AccessRequest(id, action, biometricDatas);
        }
        throw new NullPointerException("Parts null, unable to parse http request ");
    }

    private BiometricData getBiometricData(Part part, BiometricTypes dataType) throws IOException {
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

        return new BiometricData(dataType,file);
    }

    /**
     * Parses the POST request data into a Java Object
     *
     * @param request The user's e-mail address
     * @return A RegisterRequest Java Object with the POST request data
     */
    public RegisterRequest parseRegisterRequest(HttpServletRequest request) throws IOException, ServletException, IllegalArgumentException, NullPointerException, BiometricTypeException {

        Collection<Part> parts = request.getParts();
        List<BiometricData> biometricData = new LinkedList<>();
        List<ContactDetail> contactDetails = new LinkedList<>();

        String id = request.getParameter("personID");

        if(parts != null)
        {
            for(Part part : parts)
            {
                String name = part.getName().toLowerCase();
                if (!checkBiometricData(part, name, biometricData))
                    checkContactDetails(request, name, contactDetails);
            }

            return new RegisterRequest(contactDetails, id, biometricData);
        }
        throw new NullPointerException("Parts null, unable to parse http request.");
    }

    private boolean checkContactDetails(HttpServletRequest request, String name, List<ContactDetail> contactDetails) {
        Matcher contactMatcher = contactPattern.matcher(name);
        if (contactMatcher.matches()) {
            ContactTypes type = ContactTypes.fromString(contactMatcher.group(contactPatternGroup));
            String details = request.getParameter(name);
            contactDetails.add(new ContactDetail(type, details));
            return true;
        }
        return false;
    }

    private boolean checkBiometricData(Part part, String name, List<BiometricData> biometricDataList) throws IOException, BiometricTypeException {
        Matcher bioMatcher = biometricPattern.matcher(name);
        if (bioMatcher.matches()) {
            BiometricTypes dataType = BiometricTypes.fromString(bioMatcher.group(biometricPatternGroup));
            BiometricData data = getBiometricData(part, dataType);
            biometricDataList.add(data);
            return true;
        }
        return  false;
    }

}
