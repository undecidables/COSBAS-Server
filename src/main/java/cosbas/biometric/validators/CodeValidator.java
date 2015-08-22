package cosbas.biometric.validators;

import cosbas.biometric.data.AccessCode;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.data.BiometricTypes;
import cosbas.biometric.data.TemporaryAccessCode;
import cosbas.biometric.request.DoorActions;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
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

        if (dbAC.isTemporary()) {
            TemporaryAccessCode tAC = (TemporaryAccessCode) dbAC;
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = tAC.getValidFrom();
            LocalDateTime end = tAC.getValidTo();

            if (now.isBefore(start)) {
                return ValidationResponse.failedValidation("Future code not yet valid.");
            } else if (now.isAfter(end)) {
                repository.delete(dbItem);
                if (action == DoorActions.IN) return ValidationResponse.failedValidation("Code expired.");
            }
        }


        return ValidationResponse.successfulValidation(dbItem.getPersonID());
    }

    /**
     * Validates a request and changes the last action associated with the AccessCode Object.
     * @param request The biometric data that needs to be validated.
     * @param action  Whether the user is entering or leaving.
     * @return A ValidationResponse object containing
     * @throws BiometricTypeException When @param{request}'s type is not BiometricTypes.CODE
     */
    @Override
    public ValidationResponse validate(BiometricData request, DoorActions action) throws BiometricTypeException {
        if (!checkValidationType(request.getType()))
            throw new BiometricTypeException("Invalid validator type for " + request.getType());

        BiometricData dbCode = repository.findByData(request.getData());

        if (dbCode == null) return ValidationResponse.failedValidation("Code not found");

        ValidationResponse resp = matches(request, dbCode, action);

        if (resp.approved) {
            AccessCode code = (AccessCode) dbCode;
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

        if (dbItem.isTemporary()) {
            TemporaryAccessCode tAC = (TemporaryAccessCode) dbItem;
            LocalDateTime now = LocalDateTime.now();
            DoorActions lastAction = dbItem.getLastAction();
            /**
             * If duplicate code has expired and last action was either exit or it was unused, it should be deleted from the databse.
             */

            if (now.isAfter(tAC.getValidTo()) && (lastAction == DoorActions.OUT || lastAction == null)) {
                repository.delete(dbItem);
                return false;
            }
        }
        return true;
    }

    @Scheduled
    public void cleanup() {
        //TODO Implement and schedule....
    }


}
