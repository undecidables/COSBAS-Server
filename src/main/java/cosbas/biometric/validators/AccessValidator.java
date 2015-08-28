package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//TODO I suggest we rename this to a more descriptive name. Eventually.

/**
 * {@author Renette Ros}
 * Validates a specific biometric type.
 */
@Component
public abstract class AccessValidator {

    protected BiometricDataDAO repository;

    /**
     * Checks whether this validator can validate the specific request
     * @param type The biometric type that needs to be validate
     * @return A boolean indicating whether this validator can validate @param{type}
     */
    protected abstract Boolean checkValidationType(BiometricTypes type);

    @Autowired
    public void setRepository(BiometricDataDAO repository) {
        this.repository = repository;
    }


    /**
     * A method used by @method{validate} to compare two BiometricData objects.
     * @param request The data received from the Client App.
     * @param dbItem Item fetched from database to validate @param{request} against.
     * @param action  "in"/"Out"
     * @return A validation response with: if approved, success = true and data is the UserID identified"
     * If failure, success = false and the data is the failure message.
     */
    abstract protected ValidationResponse matches(BiometricData request, BiometricData dbItem, DoorActions action) throws ValidationException;

    /**
     * Validate whether the given data allows a user access, by fetching items from the databse and using
     * the @method{matches} to compare them.
     * Template method that can be overridden in special cases such as access codes.
     *
     * @param request The biometric data that needs to be validated.
     * @param action  Describes the role of the client that sends the request eg. The user is entering or exiting.
     * @return A validation response with: if approved, success = true and data is the UserID identified"
     * If failure, success = false and the data is the failure message.
     * @throws ValidationException When the @param{request}'s biometric type cannot be validted by this class
     */
    public ValidationResponse validate(BiometricData request, DoorActions action) throws ValidationException {
        if (!checkValidationType(request.getType())) throw new BiometricTypeException("Invalid Type validator");
        List<BiometricData> items = repository.findByType(request.getType());

        for (BiometricData item : items) {
            ValidationResponse response = matches(request, item, action);
            if (response.approved) return response;

        }
        return ValidationResponse.failedValidation("No Match found");
    }

}
