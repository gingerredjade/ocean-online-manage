package com.ocean.model.vo;

import java.io.Serializable;
import java.util.List;

public class PageVO<T> implements Serializable {

    private List<T> list;				// list result of this page
    private int pageNumber;				// page number
    private int pageSize;				// result amount of this page
    private int totalPage;				// total page
    private long totalRow;				// total row


    public PageVO(List<T> list, int pageNumber, int pageSize, int totalPage, int totalRow) {
        this.list = list;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalRow = totalRow;
    }

    public PageVO() {
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }
}
