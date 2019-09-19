package com.decoders.school.resource;



public class PageResource {

    private Long count;
    private Integer numberOfPages;
    private Object pageList;

    public PageResource() {
    }

    public PageResource(Long count, Integer numberOfPages, Object pageList) {
        this.count = count;
        this.pageList = pageList;
        this.numberOfPages = numberOfPages;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getPageList() {
        return pageList;
    }

    public void setPageList(Object pageList) {
        this.pageList = pageList;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
