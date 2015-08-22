package cosbas.biometric.data;

import cosbas.appointment.Appointment;
import cosbas.appointment.AppointmentDBAdapter;
import cosbas.biometric.validators.CodeValidator;
import cosbas.db.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Renette
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
    public List<AccessCode> getTRemporaryAccessCode(Appointment appointment) {

        String id = appointment.getId();


        List<String> visitors = appointment.getVisitorIDs();
        List<AccessCode> codes = new ArrayList<>(visitors.size());

        LocalDateTime from = appointment.getDateTime().minusMinutes(15);
        LocalDateTime to = appointment.getDateTime().plusMinutes(appointment.getDurationMinutes() + 15);

        for (String visitor : visitors) {
            codes.add(new TemporaryAccessCode(visitor, getCode(), from, to, id));
        }

        return codes;
    }

    /**
     * The actual code generation is delegated to implementation classes.
     * @return A new Access code.
     */
   protected abstract byte[] getCode();
}
