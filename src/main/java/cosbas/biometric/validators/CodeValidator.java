package cosbas.biometric.validators;

import cosbas.biometric.data.AccessCode;
import cosbas.biometric.data.BiometricData;
import cosbas.biometric.validators.exceptions.AccessCodeException;
import cosbas.biometric.validators.exceptions.BiometricTypeException;
import cosbas.biometric.validators.exceptions.UserNotFoundException;
import cosbas.biometric.validators.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author Renette
 * Validates temporary access code
 */
@Component
public class CodeValidator extends AccessValidator {

    @Override
    protected String matches(BiometricData request, BiometricData dbItem, String action) throws ValidationException {
        if(!Arrays.equals(request.getData(), dbItem.getData()))
            throw new UserNotFoundException();

        AccessCode dbAC = (AccessCode) dbItem;
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime start = dbAC.getValidFrom();
        LocalDateTime end = dbAC.getValidTo();

        if (start != null && now.isBefore(start)) {
            throw new AccessCodeException();
        }

        if(end != null && now.isAfter(end)) {
            repository.delete(dbItem);
            if (action.equals("in")) throw new AccessCodeException();
        }

        return dbItem.getPersonID();
    }

    @Override
    public String validate(BiometricData request, String action) throws ValidationException {
        if (request.getType() != BiometricTypes.CODE) throw new BiometricTypeException("invalid validator for " + request.getType());
        byte[] code = request.getData();
        BiometricData dbItem = repository.findByData(code);

        if (dbItem == null) throw new AccessCodeException();

        return matches(request, dbItem, action);
    }

    /**
     * Check if an access code already exists in the database
     * @param newCode The new access code
     * @return true if the code already exists in the database, false otherwise
     */
    public boolean isDuplicate(byte[] newCode) {
        return  false;
    }

}
