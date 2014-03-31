public class Data {
    private String code;
    private double factor;
    private String fullName;

    public Data(String code, double factor, String fullName) {
        this.code = code;
        this.factor = factor;
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public double getFactor() {
        return factor;
    }

    public String getFullName() {
        return fullName;
    }
}
