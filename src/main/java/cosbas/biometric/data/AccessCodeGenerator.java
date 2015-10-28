package cosbas.biometric.data;

import cosbas.appointment.Appointment;
import cosbas.biometric.validators.CodeValidator;
import cosbas.user.ContactDetail;
import cosbas.user.User;
import cosbas.user.UserDAO;
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

    @Autowired
    private UserDAO userRepository;

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

        String appointmentID = appointment.getId();

        List<String> visitors = appointment.getVisitorIDs();

        ArrayList<ContactDetail> contactDetailsStaff = null;
        User user = userRepository.findByUserID(appointment.getStaffID());
        if (user != null) {
            contactDetailsStaff = (ArrayList<ContactDetail>) user.getContact();
        }

        int size = visitors.size();
        if (contactDetailsStaff != null) {
            size -= contactDetailsStaff.size();
        }

        List<TemporaryAccessCode> codes = new ArrayList<>(size);

        LocalDateTime from = appointment.getDateTime().minusMinutes(15);
        LocalDateTime to = appointment.getDateTime().plusMinutes(appointment.getDurationMinutes() + 15);

        for (int i = 0; i < size; i++) {
            codes.add(new TemporaryAccessCode(appointmentID, visitors.get(i), getCode(), from, to));
        }

        return codes;
    }

    /**
     * The actual code generation is delegated to implementation classes.
     * @return A new Access code.
     */
   protected abstract byte[] getCode();
}
