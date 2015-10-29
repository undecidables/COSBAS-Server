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
    @Value("${codes.newlength:5}")
    protected int CODE_LENGTH;
    @Value("${codes.duplicateTries:10}")
    protected int CODE_TRIES;

    @Autowired
    private CodeValidator validator;

    public void setValidator(CodeValidator validator) {
        this.validator = validator;
    }

    protected byte[] getCode() {
        int length = CODE_LENGTH;
        byte[] code = generate(length);
        int tries = 0;
        while(validator.isDuplicate(code)) {
            tries++;
            if (tries > CODE_TRIES) {
                tries = 0;
                length++;
            }
            code = generate(length);
        }
        return code;
    }

    private byte[] generate(int codeLength) {
        byte[] code = new byte[codeLength];
        for (int i=0; i< codeLength; ++i) {
            code[i] = (byte) (Math.random() * 10);
        }
        return code;
    }

}
