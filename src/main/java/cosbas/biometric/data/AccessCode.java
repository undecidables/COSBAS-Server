package cosbas.biometric.data;

import cosbas.biometric.request.DoorActions;

/**
 * {@author Renette Ros}
 */
public class AccessCode extends BiometricData {

    public AccessCode (String person, byte[] code) {
        super(person, BiometricTypes.CODE, code);
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

    protected boolean temporary = false;
    private DoorActions lastAction;
}
