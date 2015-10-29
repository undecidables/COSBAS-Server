package cosbas.biometric.data;

import cosbas.biometric.BiometricTypes;
import cosbas.biometric.request.DoorActions;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * {@author Renette Ros}
 */
public class AccessCode extends BiometricData {

    protected boolean temporary = false;
    private DoorActions lastAction;

    @PersistenceConstructor
    protected AccessCode(String userID, BiometricTypes type, byte[] data, boolean temporary, DoorActions lastAction) {
        super(userID, type, data);
        this.temporary = temporary;
        this.lastAction = lastAction;
    }

    public AccessCode (String user, byte[] code) {
        super(user, BiometricTypes.CODE, code);
    }

    public AccessCode(byte[] code) {
        super(BiometricTypes.CODE, code);
    }

    public void use(DoorActions action) {
        lastAction = action;
    }

    public DoorActions getLastAction() {
        return lastAction;
    }

    public boolean isTemporary() {
        return temporary;
    }

    @Override
    public String toString() {
        byte[] code = this.getData();
        StringBuilder codeString = new StringBuilder(code.length);
        for (int i = 0; i < code.length; i++) {
            codeString.append(code[i]);
        }
        return String.valueOf(codeString);
    }
}
