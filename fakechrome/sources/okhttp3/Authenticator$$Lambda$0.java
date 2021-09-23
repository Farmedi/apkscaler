package okhttp3;

final /* synthetic */ class Authenticator$$Lambda$0 implements Authenticator {
    static final Authenticator $instance = new Authenticator$$Lambda$0();

    private Authenticator$$Lambda$0() {
    }

    @Override // okhttp3.Authenticator
    public Request authenticate(Route route, Response response) {
        return Authenticator$$CC.lambda$static$0$Authenticator$$CC(route, response);
    }
}
