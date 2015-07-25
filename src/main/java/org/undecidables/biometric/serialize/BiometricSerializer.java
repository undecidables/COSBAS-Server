package org.undecidables.biometric.serialize;

import org.undecidables.biometric.request.AccessRequest;
import org.undecidables.biometric.request.AccessResponse;
import org.undecidables.biometric.request.BiometricData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Renette
 * Abstract class for serializing AccessResponses to text
 * and parsing AccessRequests from HttpServletRequests.
 */
public abstract class BiometricSerializer {
    /**
     * Creates a string from an accessResponse Object
     * @param response Response Object to be serialized
     * @return Serialized response object.
     */
    public abstract String serializeResponse(AccessResponse response);

    /**
     * Parses a string? into a AccessRequest object.
     * @param request Data to be parsed into request object
     * @return Parsed request object
     * TODO: I dont think String is correct input type... What data does http request use?
     */
    public AccessRequest parseRequest(HttpServletRequest request) throws IOException, ServletException {

        List<Part> parts = (List<Part>) request.getParts();
        List<BiometricData> biometricDatas = new ArrayList<BiometricData>();

        String id = request.getParameter("ID");
        String action = request.getParameter("Action");

        if(parts != null)
        {
            for(Part part : parts)
            {
                String name = part.getName().toLowerCase();

                if(name.contains("id") || name.contains("action"))
                {
                }
                else
                {
                    byte[] file = null;
                    InputStream partInputStream=part.getInputStream();
                    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                    byte[] chunk=new byte[4096];
                    int amountRead;
                    while ((amountRead=partInputStream.read(chunk)) != -1) {
                        outputStream.write(chunk,0,amountRead);
                    }
                    if (outputStream.size() == 0) {
                    }
                    else
                    {
                        file = outputStream.toByteArray();
                    }
                    BiometricData data = new BiometricData(part.getName(),file);
                    biometricDatas.add(data);
                }
            }

            return new AccessRequest(id, action, biometricDatas);
        }
        throw new NullPointerException("Parts null, unable to parse http request ");
    }
}
