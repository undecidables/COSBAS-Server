/**
 * @author Renette
 * MVC COntroller for biometric system
 * Responds with plain text to requests
 */

package web;

import biometric.AccessRequest;
import biometric.BiometricData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
public class BiometricController {

    @RequestMapping(/*method= RequestMethod.POST, */value="/biometrics/access", method= RequestMethod.POST)
    public String access(HttpServletRequest request) throws IOException, ServletException {
        //TODO Call access function and serialize response to JSON


        List<Part> parts = (List) request.getParts();
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

            AccessRequest accessRequest = new AccessRequest(id, action, biometricDatas);
        }


        return "{TODO: Serialize response object to JSON}";
    }

}
