package decorate.angel.admission;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DefaultLottieNetworkFetcher implements LottieNetworkFetcher {
    /* renamed from: ˊ  reason: contains not printable characters */
    public LottieFetchResult m29(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        return new DefaultLottieFetchResult(httpURLConnection);
    }
}
