package cosbas.biometric.validators;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Renette
 * Validates a specific biometric type.
 */
@Component
public abstract class AccessValidator {

    protected BiometricDataDAO repository;

    @Autowired
    public void setRepository(BiometricDataDAO repository) {
        this.repository = repository;
    }

    abstract protected boolean matches(BiometricData request, BiometricData dbItem);



    /**
     * Validate whether the given data allows a user access,
     * Template method that can be overridden in special cases such as access codes
     * @param requestData The biometric data that needs to be validated.
     * @return True for valid - access allowed.
     */
    public boolean validate(BiometricData requestData) throws BiometricTypeException {
        List<BiometricData> items = repository.findByType(requestData.getType());
        for (BiometricData item : items) {
            if (matches(requestData, item)) {
                return true;
            }
        }
        return false;
    }

}
