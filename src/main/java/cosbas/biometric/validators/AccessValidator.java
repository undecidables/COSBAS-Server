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


    @Autowired
    protected BiometricDataDAO repository;

    /**
     * Setter to inject DAO.
     * @param repository The value to be set
     */
    public void setRepository(BiometricDataDAO repository) {
        this.repository = repository;
    }

    /**
     * Template method to validate BiometricData from an AccessRequest. It uses the abstract
     * identifyUser and checkValidationType functions.
     *
     * @param request The biometric data that needs to be validated.
     * @param action  Describes the role of the client that sends the request eg. The user is entering or exiting.
     * @return A validation response with: if approved, success = true and data is the UserID identified"
     * If failure, success = false and the data is the failure message.
     * @throws BiometricTypeException When the @param{request}'s biometric type cannot be validated by this class as
     *  determined  by the checkValidationType function.
     */
    public final ValidationResponse validate(BiometricData request, DoorActions action) throws BiometricTypeException {
        if (!checkValidationType(request.getType()))
            throw new BiometricTypeException(this.getClass() + " cannot validate " + request.getType());
        return identifyUser(request, action);
    }


    /**
     * Checks whether this validator can validate the specific request.
     * @param type The biometric type that needs to be validate
     * @return A boolean indicating whether this validator can validate @param{type}
     */
    protected abstract Boolean checkValidationType(BiometricTypes type);

    /**
     * The function that validates the biometric data and identifies a user.
     *
     * @param request The biometric data that needs to be validated.
     * @param action  Describes the role of the client that sends the request eg. The user is entering or exiting.
     * @return A validation response with: if approved, success = true and data is the UserID identified"
     * If failure, success = false and the data is the failure message.
     */
    public abstract ValidationResponse identifyUser(BiometricData request, DoorActions action);

}
