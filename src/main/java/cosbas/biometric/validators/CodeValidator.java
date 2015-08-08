package cosbas.biometric.validators;

import cosbas.biometric.data.AccessCode;
import cosbas.biometric.data.BiometricData;
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
    protected boolean matches(BiometricData request, BiometricData dbItem) {
        if(!Arrays.equals(request.getData(), dbItem.getData()))
            return false;

        AccessCode dbAC = (AccessCode) dbItem;
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime start = dbAC.getValidFrom();
        LocalDateTime end = dbAC.getValidTo();

        if (start != null && !start.isBefore(now))
            return false;

        if(end != null && !end.isAfter(now)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean validate(BiometricData request) throws BiometricTypeException {
        if (request.getType() != BiometricTypes.CODE) throw new BiometricTypeException("invalid validator for " + request.getType());
        byte[] code = request.getData();
        //Lookup in db
        //Validate
        return true;
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
