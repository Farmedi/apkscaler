package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public interface Dns {
    public static final Dns SYSTEM = Dns$$Lambda$0.$instance;

    List<InetAddress> lookup(String str) throws UnknownHostException;
}
