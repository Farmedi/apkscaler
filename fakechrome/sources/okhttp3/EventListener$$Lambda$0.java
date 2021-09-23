package okhttp3;

import okhttp3.EventListener;

final /* synthetic */ class EventListener$$Lambda$0 implements EventListener.Factory {
    private final EventListener arg$1;

    EventListener$$Lambda$0(EventListener eventListener) {
        this.arg$1 = eventListener;
    }

    @Override // okhttp3.EventListener.Factory
    public EventListener create(Call call) {
        return EventListener.lambda$factory$0$EventListener(this.arg$1, call);
    }
}
