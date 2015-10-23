package cosbas.biometric.data;

import cosbas.appointment.Appointment;
import cosbas.biometric.validators.CodeValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * {@author Renette Ros}
 * The abstract class implementing template methods for genrating temporary and permanent access codes.
 */
public abstract class AccessCodeGenerator {

       @Autowired
    private CodeValidator validator;

    public AccessCode getPermanentAccessCode(String user) {
        byte[] code = getCode();
        return new AccessCode(user, code);
    }

    /**
     * Creates Temporary Access codes for all visitors in the appointment.
     * @param appointment The appointment with which the access codes should be are associated.
     *                    It should already have an id field.
     * @return A list containing TemporaryAccessCode
     */
    public List<TemporaryAccessCode> getTemporaryAccessCode(Appointment appointment) {

        String id = appointment.getId();


        List<String> visitors = appointment.getVisitorIDs();
        List<TemporaryAccessCode> codes = new ArrayList<>(visitors.size());

        LocalDateTime from = appointment.getDateTime().minusMinutes(15);
        LocalDateTime to = appointment.getDateTime().plusMinutes(appointment.getDurationMinutes() + 15);

        for (int i = 0; i < visitors.size() - 1; i++) {
            codes.add(new TemporaryAccessCode(id, visitors.get(i), getCode(), from, to));
        }

        return codes;
    }

    /**
     * The actual code generation is delegated to implementation classes.
     * @return A new Access code.
     */
   protected abstract byte[] getCode();
}
