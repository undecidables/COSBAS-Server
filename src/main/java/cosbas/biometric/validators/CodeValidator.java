package cosbas.biometric.validators;

import cosbas.biometric.data.AccessCode;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author Renette
 *         Validates permanent and temporary access codes
 */
@Component
public class CodeValidator extends AccessValidator {

    @Override
    protected ValidationResponse matches(BiometricData request, BiometricData dbItem, DoorActions action) {
        if (!Arrays.equals(request.getData(), dbItem.getData()))
            return ValidationResponse.failedValidation("Invalid Code.");

        AccessCode dbAC = (AccessCode) dbItem;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = dbAC.getValidFrom();
        LocalDateTime end = dbAC.getValidTo();

        if (start != null && now.isBefore(start)) {
            return ValidationResponse.failedValidation("Future code not yet valid.");
        } else if (end != null && now.isAfter(end)) {
            repository.delete(dbItem);
            if (action == DoorActions.IN) return ValidationResponse.failedValidation("Code expired.");
        }


        return ValidationResponse.successfulValidation(dbItem.getPersonID());
    }

    /**
     * Validates a request and changes the last action associated with the AccessCode Object.
     * @param request The biometric data that needs to be validated.
     * @param action  'in' or 'out'
     * @return A ValidationResponse object containing
     * @throws ValidationException
     */
    @Override
    public ValidationResponse validate(BiometricData request, DoorActions action) throws ValidationException {
        if (!checkValidationType(request.getType()))
            throw new BiometricTypeException("Invalid validator type for " + request.getType());

        BiometricData dbItem = repository.findByData(request.getData());

        if (dbItem == null) return ValidationResponse.failedValidation("Code not found");

        ValidationResponse resp = matches(request, dbItem, action);

        if (resp.approved) {
            AccessCode code = (AccessCode) dbItem;
            code.use(action);
            repository.save(code);
        }

        return resp;
    }

    @Override
    protected Boolean checkValidationType(BiometricTypes type) {
        return type == BiometricTypes.CODE;
    }

    /**
     * Check if an access code already exists in the database
     *
     * @param newCode The new access code
     * @return true if the code already exists in the database, false otherwise
     * This function will delete expired access codes that have never been used from the database
     */
    public boolean isDuplicate(byte[] newCode) {
        AccessCode dbItem = (AccessCode) repository.findByData(newCode);
        if (dbItem == null) return false;

        LocalDateTime now = LocalDateTime.now();
        DoorActions lastAction = dbItem.getLastAction();

        /**
         * If duplicate code has expired and last action was either exit or it was unused, it should be deleted from the databse.
         */
        if (now.isAfter(dbItem.getValidTo()) && (lastAction == DoorActions.OUT || lastAction == null) ) {
            repository.delete(dbItem);
            return false;
        }
        return true;
    }

    @Scheduled
    public void cleanup() {
        //TODO Implement and schedule....
    }


}
