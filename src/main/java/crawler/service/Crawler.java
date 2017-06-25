package crawler.service;

import crawler.domain.Ad;

import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
public interface Crawler {

    List<Ad> get(String query);
}
