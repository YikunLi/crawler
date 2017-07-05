package crawler.converter;

import crawler.domain.AdPo;
import crawler.domain.AmazonProduct;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyikun on 2017/7/5.
 */
public class AmazonProductToAdPoConverter implements Converter<AmazonProduct, AdPo> {

    private static final String[] STOP_WORDs = {
            "like", "a", "an", "the"
    };

    @Override
    public AdPo convert(AmazonProduct amazonProduct) {
        AdPo adPo = new AdPo();
        adPo.setTitle(amazonProduct.getTitle());
        adPo.setPrice(amazonProduct.getPrice());
        adPo.setThumbnail(amazonProduct.getThumbnail());
        adPo.setDescription(amazonProduct.getDescription());
        adPo.setBrand(amazonProduct.getBrand());
        adPo.setDetailUrl(amazonProduct.getDetailUrl());
        adPo.setCategory(amazonProduct.getCategory());
        adPo.setQuery(amazonProduct.getQuery());
        adPo.setKeywords(this.getKeyWords(amazonProduct.getTitle()));
        return adPo;
    }

    private List<String> getKeyWords(String title) {
        List<String> keywords = new ArrayList<>();
        for (String word : title.split(" ")) {
            String formatWord = word.toLowerCase();
            if (this.isStopWord(formatWord)) {
                continue;
            }
            keywords.add(word);
        }
        return keywords;
    }

    private boolean isStopWord(String word) {
        for (String stopWord : STOP_WORDs) {
            if (stopWord.equals(word)) {
                return true;
            }
        }
        return false;
    }

}