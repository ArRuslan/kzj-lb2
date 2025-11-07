package ua.nure.kz.utils;

public class PaginationPage {
    private boolean isCurrent;
    private long num;

    public PaginationPage(boolean isCurrent, long num) {
        this.isCurrent = isCurrent;
        this.num = num;
    }

    public boolean getIsCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }
}
