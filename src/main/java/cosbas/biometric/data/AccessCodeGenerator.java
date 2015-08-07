package cosbas.biometric.data;

/**
 * @author Renette
 * TODO This is non ideal as it might return duplicte access codes.
 */
public class AccessCodeGenerator {
    private static final int CODE_LENGTH = 5;
    public byte[] getNewAccessCode() {
        byte[] code = new byte[CODE_LENGTH];
        for (int i=0; i<CODE_LENGTH; ++i) {
            code[i] = (byte) (Math.random() * 10);
        }
        return code;
    }
}
