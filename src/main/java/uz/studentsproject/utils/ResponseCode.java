package uz.studentsproject.utils;

public enum ResponseCode {

    REQUIRED_DATA_MISSING(4001),
    ;

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
