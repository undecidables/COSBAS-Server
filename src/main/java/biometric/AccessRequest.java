package biometric;

/**
 * Created by Renette on 2015-06-26.
 * Access Request created from http request.
 */
public class AccessRequest {


    /**
     * Identifies type of biometric data.
     * TODO Maybe an enum is better - or is string & class stored somewhere in config file?
     */
    final String type;

    public byte[] getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    /**
     * Actual biometric data data.
     * TODO Make sure this is best format.
     */
    final byte[] data;

    public AccessRequest(String type, byte[] data) {
        this.type = type;
        this.data = data;
    }
}
