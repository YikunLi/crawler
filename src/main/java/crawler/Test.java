package crawler;

import crawler.converter.AmazonProductToAdPoConverter;
import crawler.domain.*;
import crawler.service.Page;
import crawler.service.impl.AmazonCrawler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyikun on 2017/7/5.
 */
public class Test {

    private static boolean isFirst = true;

    public static void main(String[] args) {
        final AmazonCrawler crawler = new AmazonCrawler();
        final AdPoRepository repository = new AdPoRepository();
        repository.saveStart();
        RawQueryRepository rawQueryRepository = new RawQueryRepository();
        rawQueryRepository.loadQueries(query -> {
            crawAndSave(crawler, repository, query, isFirst);
            isFirst = false;
        });
        repository.saveEnd();
    }

    private static void crawAndSave(AmazonCrawler crawler, AdPoRepository repository, RawQuery rawQuery, boolean isFirst) {
        for (int i = 0; i < 3; i++) {
            Page<AmazonProduct> page = crawler.getPage(rawQuery.getQuery(), i);
            if (page == null) {
                return;
            }
            if (!(i == 0 && isFirst)) {
                repository.saveComma();
            }
            List<AdPo> adPos = new ArrayList<>();
            for (AmazonProduct product : page.getProducts()) {
                AdPo adPo = new AmazonProductToAdPoConverter().convert(product);
                adPo.setBidPrice(rawQuery.getBidPrice());
                adPo.setCampaignId(rawQuery.getCampaignId());
                adPo.setQueryGroupId(rawQuery.getQueryGroupId());
                adPos.add(adPo);
            }
            repository.save(adPos);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
