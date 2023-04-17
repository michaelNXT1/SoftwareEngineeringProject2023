package org.example.ServiceLayer;

public class Response {
    public String error_message = "";
    public boolean error_occurred;

    protected Response() {
    }

    protected Response(String msg) {
        error_message = msg;
    }

    public boolean getError_occurred() {
        if (error_message != null)
            return !error_message.equals("");
        return false;
    }
}


