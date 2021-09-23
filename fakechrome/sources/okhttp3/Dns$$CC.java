package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public abstract /* synthetic */ class Dns$$CC {
    public static void $$triggerInterfaceInit() {
        Dns dns = Dns.SYSTEM;
    }

    static /* synthetic */ List lambda$static$0$Dns$$CC(String str) throws UnknownHostException {
        if (str == null) {
            throw new UnknownHostException("hostname == null");
        }
        try {
            return Arrays.asList(InetAddress.getAllByName(str));
        } catch (NullPointerException e) {
            UnknownHostException unknownHostException = new UnknownHostException("Broken system behaviour for dns lookup of " + str);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}
