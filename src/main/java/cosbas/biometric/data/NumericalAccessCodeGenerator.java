package cosbas.biometric.data;

import cosbas.biometric.validators.CodeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Renette
 *
 * This class is a concrete strategy generates a unique Numerical AccessCode.
 * Uniquness is verified using a @Class{CodeValidator}.
 */
@Service
public class NumericalAccessCodeGenerator implements AccessCodeGenerator {

    /**
     * The length of a new code. It has a default value if 'codes.newlength' is not specified in the application properties.
     */
    @Value("${codes.newlength}")
    private int CODE_LENGTH = 5;

    @Autowired
    private CodeValidator validator;

    public void setValidator(CodeValidator validator) {
        this.validator = validator;
    }

    @Override
    public byte[] newAccessCode() {
        byte[] code = generateCode();
        while(validator.isDuplicate(code)) {
            code = generateCode();
        }
        return code;
    }

    protected byte[] generateCode() {
        byte[] code = new byte[CODE_LENGTH];
        for (int i=0; i<CODE_LENGTH; ++i) {
            code[i] = (byte) (Math.random() * 10);
        }
        return code;
    }

}
