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

    /**
     *
     * @param request
     * @param dbItem
     * @param action In/Out
     * @return
     */
    abstract protected String matches(BiometricData request, BiometricData dbItem, String action) throws UserNotFoundException;

    /**
     * Validate whether the given data allows a user access,
     * Template method that can be overridden in special cases such as access codes
     * @param requestData The biometric data that needs to be validated.
     * @param action 'in' or 'out'
     * @return True for valid - access allowed.
     */
    public String validate(BiometricData requestData, String action) throws BiometricTypeException, UserNotFoundException {

        List<BiometricData> items = repository.findByType(requestData.getType());
        for (BiometricData item : items) {
            try {
                return matches(requestData, item, action);

            } catch (UserNotFoundException e) {
                /** Silently continue with validating next item */
                continue;
            }
        }
        throw new UserNotFoundException();
    }

}
