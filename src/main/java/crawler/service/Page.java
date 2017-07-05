package crawler.service;

import lombok.Data;

import java.util.List;

/**
 * Created by liyikun on 2017/7/5.
 */
@Data
public class Page<T> {

    private int pageNumber;

    private List<T> products;

    public Page(int pageNumber, List<T> products) {
        this.pageNumber = pageNumber;
        this.products = products;
    }
}