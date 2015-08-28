package cosbas.biometric.serialize;

import cosbas.biometric.request.AccessRequest;
import cosbas.biometric.request.AccessResponse;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.BiometricTypes;
import cosbas.biometric.request.DoorActions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

        List<Part> parts = (List<Part>) request.getParts();
        List<BiometricData> biometricDatas = new ArrayList<>();

        String id = request.getParameter("ID");
        DoorActions action = DoorActions.fromString(request.getParameter("Action"));

        if(parts != null)
        {
            for(Part part : parts)
            {
                String name = part.getName().toLowerCase();

                if (!name.contains("id") && !name.contains("action")) {
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

                    BiometricData data = new BiometricData(BiometricTypes.fromString(part.getName()),file);
                    biometricDatas.add(data);
                }
            }

            return new AccessRequest(id, action, biometricDatas);
        }
        throw new NullPointerException("Parts null, unable to parse http request ");
    }

}
