package crawler.service;

import crawler.domain.Ad;

import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 * https://www.amazon.com/s/ref=nb_sb_noss_2?field-keywords=das
 */
public interface CrawlerService {

    List<Ad> findAll();

    boolean startCrawl();
}
