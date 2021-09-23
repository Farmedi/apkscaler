package decorate.angel.admission;

public enum FileExtension {
    JSON(".json"),
    ZIP(".zip");
    

    /* renamed from: ʻ  reason: contains not printable characters */
    public final String f15;

    private FileExtension(String str) {
        this.f15 = str;
    }

    public String toString() {
        return this.f15;
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    public String m30() {
        return ".temp" + this.f15;
    }
}
