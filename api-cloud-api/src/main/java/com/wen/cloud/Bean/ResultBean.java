package com.wen.cloud.Bean;

public class ResultBean {
    private int status;
    private String msg;
    private String result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
