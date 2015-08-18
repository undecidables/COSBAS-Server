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
    protected ValidationResponse matches(BiometricData request, BiometricData dbItem, String action) {
        if (!Arrays.equals(request.getData(), dbItem.getData()))
            return ValidationResponse.failedValidation("Code not found");

        AccessCode dbAC = (AccessCode) dbItem;
        dbAC.use(); //Set used variable for db cleanup.
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = dbAC.getValidFrom();
        LocalDateTime end = dbAC.getValidTo();

        if (start != null && now.isBefore(start)) {
            return ValidationResponse.failedValidation("Code not valid yet.");
        } else if (end != null && now.isAfter(end)) {
            repository.delete(dbItem);
            if (action.equals("in"))
                return ValidationResponse.failedValidation("Code expired.");
        }


        return ValidationResponse.successfulValidation(dbItem.getPersonID());
    }

    @Override
    public ValidationResponse validate(BiometricData request, String action) throws ValidationException {
        if (!checkValidationType(request.getType()))
            throw new BiometricTypeException("Invalid validator type for " + request.getType());

        BiometricData dbItem = repository.findByData(request.getData());

        if (dbItem == null) return ValidationResponse.failedValidation("Code not found");

        return matches(request, dbItem, action);
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
        if (now.isAfter(dbItem.getValidTo()) && !dbItem.getUsed()) {
            repository.delete(dbItem);
            return false;
        }
        return true;
    }

    @Scheduled
    public void cleanup() {
    }


}
