package org.example.ServiceLayer;

public class ResponseT<T> extends Response {
    public final T value;

    private ResponseT(T value, String msg) {
        super(msg);
        this.value = value;
    }

    public static <T> ResponseT fromValue(T value) {
        return new ResponseT<>(value, null);
    }

    public static <T> ResponseT fromError(String msg) {
        return new ResponseT<>(null, msg);
    }
}


