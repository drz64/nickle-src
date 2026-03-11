package nickle;

public record DataDesc(Kind kind, long iVal, int cVal, String sVal) {
    public enum Kind { INT, CHAR, STRING }

    public static DataDesc ofInt(long v)   { return new DataDesc(Kind.INT, v, 0, null); }
    public static DataDesc ofChar(int v)   { return new DataDesc(Kind.CHAR, 0, v, null); }
    public static DataDesc ofString(String s) { return new DataDesc(Kind.STRING, 0, 0, s); }
}
