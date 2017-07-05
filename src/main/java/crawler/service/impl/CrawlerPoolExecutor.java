package crawler.service.impl;

import crawler.domain.Ad;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
@Component
public class CrawlerPoolExecutor {

    public void getAd(Callback callback) {
    }

    public interface Callback {

        void onDataLoaded(List<Ad> ads);

    }
}
