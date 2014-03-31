/*Класс, содержащий данные из считанного файла*/

public class Data {
    private String code;
    private double factor;
    private String fullName;

    public Data(String code, double factor, String fullName) {
        this.code = code;
        this.factor = factor;
        this.fullName = fullName;
    }
/*Возвращает код валюты (например, USD)*/

    public String getCode() {
        return code;
    }

/*Возвращает курс валюты относительно рубля*/

    public double getFactor() {
        return factor;
    }

/*Возвращает полное название валюты*/

    public String getFullName() {
        return fullName;
    }
}
