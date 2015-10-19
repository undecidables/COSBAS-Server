package cosbas.biometric.validators;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricDataDAO;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.biometric.validators.exceptions.NoUserException;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@author Renette Ros}
 * Abstract class to validate {@link BiometricData} of a specific type.
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
     * Template method to check validator type and validate BiometricData from an AccessRequest.
     * It uses the abstract {@link #identifyUser} and {@link #checkValidationType} functions.
     *
     * @param request The biometric data that needs to be validated.
     * @param action  Describes the role of the client that sends the request eg. The user is entering or exiting.
     * @return A validation response with: If approved, success = true and data is the UserID identified
     *  If failure, success = false and the data is the failure message.
     *
     * @throws BiometricTypeException When the @param{request}'s biometric type cannot be validated by this class.
     */
    public ValidationResponse validate(BiometricData request, DoorActions action) throws BiometricTypeException {
        if (!checkValidationType(request.getType()))
            throw new BiometricTypeException(this.getClass() + " cannot validate " + request.getType());
        try {
            return identifyUser(request, action);
        } catch (ValidationException e) {
           return ValidationResponse.failedValidation(e.getMessage());
        }
    }

    /**
     * Checks whether this validator can validate the specific request.
     * @param type The biometric type that needs to be validate
     * @return A boolean indicating whether this validator can validate @param{type}.
     *  (True => Can validate, False => Can not validate).
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
    public abstract ValidationResponse identifyUser(BiometricData request, DoorActions action) throws ValidationException;

    /**
     * A function to perform extra actions necessary when registering a user. It handles saving to the database and
     * error checking and can be overridden in base classes.
     * @param request A biometricData
     * @return The saved requests database ID
     * @throws BiometricTypeException when the bioemtric type cannot be validated by this validator.
     * @throws NoUserException When the request object does not contain a userID.
     */
    public String registerUser(BiometricData request) throws BiometricTypeException, NoUserException {
        if (!checkValidationType(request.getType())) throw new BiometricTypeException(this.getClass() + " is the wrong validator for " + request.getType());
        if (request.getUserID() == null) throw new NoUserException("Cannot register data without a userID");
        repository.save(request);
        return request.getId();
    }

    public String registerUser(BiometricData request, String userID) throws BiometricTypeException, NoUserException {
       request.setUserID(userID);
        return registerUser(request);
    }
}
