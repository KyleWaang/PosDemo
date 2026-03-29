package common;

public enum Category {
    COSMETIC("01", "化妝品"),
    PERFUME("02", "香水"),
    LEATHER("03", "皮件"),
    APPAREL("04", "服飾"),
    WINE("05", "酒");

    private final String code;
    private final String desc;

    Category(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Category fromCode(String code) {
        for (Category c : values()) {
            if (c.code.equals(code)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown category code: " + code);
    }
}