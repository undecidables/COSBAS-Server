/**
 * @author Renette
 * MVC COntroller for biometric system
 * Responds with plain text to requests
 */

package web;

import biometric.AccessRequest;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
public class BiometricController {

    @RequestMapping(/*method= RequestMethod.POST, */value="/biometrics/access", method= RequestMethod.POST)
    public String access(HttpServletRequest request) throws IOException, ServletException {
        //TODO Call access function and serialize response to JSON


        return "{TODO: Serialize response object to JSON}";
    }

}
