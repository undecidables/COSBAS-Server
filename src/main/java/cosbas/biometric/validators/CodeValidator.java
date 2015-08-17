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
    protected String matches(BiometricData request, BiometricData dbItem, String action) throws UserNotFoundException {
        if(!Arrays.equals(request.getData(), dbItem.getData()))
            return "";

        AccessCode dbAC = (AccessCode) dbItem;
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime start = dbAC.getValidFrom();
        LocalDateTime end = dbAC.getValidTo();

        if (start != null && !start.isBefore(now))
            return "";

        if(end != null && !end.isAfter(now)) {
            return "";
        }

        return "";
    }

    @Override
    public String validate(BiometricData request, String action) throws BiometricTypeException, UserNotFoundException {
        if (request.getType() != BiometricTypes.CODE) throw new BiometricTypeException("invalid validator for " + request.getType());
        byte[] code = request.getData();
        //Lookup in db
        //Validate
        return "u00000000";
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
