package crawler.service.impl;

import crawler.domain.*;
import crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CrawlerPoolExecutor crawlerPoolExecutor;

    @Autowired
    private RawQueryRepository rawQueryRepository;

    @Override
    public List<Ad> findAll() {
        return this.adRepository.findAll();
    }

    @Override
    public boolean startCrawl() {
        this.rawQueryRepository.loadQueries(query ->
                CrawlerServiceImpl.this.crawlerPoolExecutor.getAd(ads ->
                        CrawlerServiceImpl.this.adRepository.save(ads)));
        return true;
    }
}
