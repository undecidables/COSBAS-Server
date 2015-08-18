package cosbas.biometric.validators;

import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author Renette
 *         Validates a specific biometric type.
 */
@Component
public abstract class AccessValidator {

    protected BiometricDataDAO repository;

    /**
     * Checks whether this validator can validate the specific request
     * @param type The biometric type to check against validator type
     * @return
     */
    protected abstract Boolean checkValidationType(BiometricTypes type);

    @Autowired
    public void setRepository(BiometricDataDAO repository) {
        this.repository = repository;
    }


    /**
     *
     * @param request
     * @param dbItem
     * @param action  "in"/"Out"
     * @return A validation response with: if recognized, success = true and data is the UserID identified"
     * If failure, success = false and the data is the failure message.
     */
    abstract protected ValidationResponse matches(BiometricData request, BiometricData dbItem, String action) throws ValidationException;

    /**
     * Validate whether the given data allows a user access,
     * Template method that can be overridden in special cases such as access codes
     *
     * @param request The biometric data that needs to be validated.
     * @param action  'in' or 'out'
     * @return True for valid - access allowed.
     */
    public ValidationResponse validate(BiometricData request, String action) throws ValidationException {
        if (!checkValidationType(request.getType())) throw new BiometricTypeException("Invalid Type validator");
        List<BiometricData> items = repository.findByType(request.getType());

        for (BiometricData item : items) {
            ValidationResponse response = matches(request, item, action);
            if (response.recognized) return response;

        }
        return ValidationResponse.failedValidation("No Match found");
    }

    protected static class ValidationResponse {
        public final boolean recognized;

        /**
         * For example, a userid or message.
         */
        public final String data;

        public ValidationResponse(boolean recognized, String data) {

            this.data = data;
            this.recognized = recognized;
        }

        public static ValidationResponse successfulValidation(String user) {
            return new ValidationResponse(true, user);
        }

        public static ValidationResponse failedValidation(String message) {
            return new ValidationResponse(false, message);
        }

        @Override
        public boolean equals(Object other) {
            try {
                ValidationResponse otherResponse = (ValidationResponse) other;
                return recognized == otherResponse.recognized && Objects.equals(data, otherResponse.data);
            } catch (Exception e) {
                return false;
            }
        }
    }

}
