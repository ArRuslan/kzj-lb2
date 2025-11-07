package ua.nure.kz.utils;

public class Pagination {
    private boolean hasPrev;
    private boolean hasNext;
    private PaginationPage[] pages;
    private String currentPath;
    private String currentQuery;
    private long prevPage;
    private long nextPage;
    private int pageSize;

    public Pagination(boolean hasPrev, boolean hasNext, PaginationPage[] pages, String currentPath, long prevPage, long nextPage, int pageSize, String currentQuery) {
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.pages = pages;
        this.currentPath = currentPath;
        this.prevPage = prevPage;
        this.nextPage = nextPage;
        this.pageSize = pageSize;
        this.currentQuery = currentQuery;
    }

    public boolean getHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public PaginationPage[] getPages() {
        return pages;
    }

    public void setPages(PaginationPage[] pages) {
        this.pages = pages;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public long getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(long prevPage) {
        this.prevPage = prevPage;
    }

    public long getNextPage() {
        return nextPage;
    }

    public void setNextPage(long nextPage) {
        this.nextPage = nextPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurrentQuery() {
        return currentQuery;
    }

    public void setCurrentQuery(String currentQuery) {
        this.currentQuery = currentQuery;
    }
}
