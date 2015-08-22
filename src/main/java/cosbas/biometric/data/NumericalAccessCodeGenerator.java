package cosbas.biometric.data;

import cosbas.biometric.validators.CodeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * {@author Renette Ros}
 *
 * This class is a concrete strategy generates a unique Numerical AccessCode.
 * Uniquness is verified using a {@link CodeValidator}.
 */
@Service
public class NumericalAccessCodeGenerator extends AccessCodeGenerator {

    /**
     * The length of a new code. It has a default value of {@value} if 'codes.newlength' is not specified in the application properties.
     */
    @Value("${codes.newlength}")
    protected int CODE_LENGTH = 5;

    @Autowired
    private CodeValidator validator;

    public void setValidator(CodeValidator validator) {
        this.validator = validator;
    }

    protected byte[] getCode() {
        byte[] code = generate();
        while(validator.isDuplicate(code)) {
            code = generate();
        }
        return code;
    }

    private byte[] generate() {
        byte[] code = new byte[CODE_LENGTH];
        for (int i=0; i<CODE_LENGTH; ++i) {
            code[i] = (byte) (Math.random() * 10);
        }
        return code;
    }

}
