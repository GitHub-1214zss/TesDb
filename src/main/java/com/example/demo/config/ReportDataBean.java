package com.example.demo.config;

import java.util.List;

public class ReportDataBean {
    private List<String> categories;
    private List<Float> datatype;
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public List<Float> getData() {
        return datatype;
    }
    public void setData(List<Float> datatype) {
        this.datatype = datatype;
    }
}
