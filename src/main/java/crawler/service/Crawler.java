package crawler.service;

import crawler.domain.AmazonProduct;

import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
public interface Crawler<T> {

    Page<AmazonProduct> getPage(String query);

    Page<AmazonProduct> getPage(String query, int pageIndex);

    List<Page<AmazonProduct>> getAllPage(String query, int pageCount);
}
