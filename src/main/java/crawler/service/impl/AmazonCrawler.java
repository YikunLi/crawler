package crawler.service.impl;

import crawler.domain.Ad;
import crawler.domain.AmazonProduct;
import crawler.service.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
public class AmazonCrawler implements Crawler {

    private static final String REQUEST_URL = "https://www.amazon.com/s/ref=nb_sb_noss";

    public AmazonCrawler() {

    }

    @Override
    public List<Ad> get(String query) {
        try {
            Document document = this.getDocument(query);
            List<AmazonProduct> amazonProducts = this.getAmazonProducts(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Document getDocument(String query) throws IOException {
        return Jsoup.connect(REQUEST_URL)
                .data("field-keywords", query)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, sdch, br")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
                .header("Cache-Control", "max-age=0")
                .header("Connection", "keep-alive")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .timeout(1000)
                .get();
    }

    private List<AmazonProduct> getAmazonProducts(Document document) {
        List<AmazonProduct> amazonProducts = new ArrayList<>();
        Element elementResultList = document
                .getElementById("search-main-wrapper")
                .getElementById("main")
                .getElementById("searchTemplate")
                .getElementById("rightContainerATF")
                .getElementById("rightResultsATF")
                .getElementById("resultsCol")
                .getElementById("centerMinus")
                .getElementById("atfResults")
                .getElementById("s-results-list-atf");
        System.out.println(elementResultList);
        Elements elements = elementResultList.children();
        for (Element element : elements) {
            try {
                AmazonProduct amazonProduct = this.getAmazonProduct(element);
                amazonProducts.add(amazonProduct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return amazonProducts;
    }

    private AmazonProduct getAmazonProduct(Element element) throws Exception {
        AmazonProduct product = new AmazonProduct(element.attr("data-asin"));
        product.setTitle(element.getElementsByAttributeValue("class", "a-size-medium s-inline  s-access-title  a-text-normal").get(0).text());
        product.setPrice(Double.parseDouble(element.getElementsByAttributeValue("class", "a-color-base sx-zero-spacing").get(0).attr("aria-label").substring(1)));
        product.setRelevanceScore(this.parseScore(element.getElementsByAttributeValue("class", "a-icon a-icon-star a-star-4").get(0)
                .getElementsByAttributeValue("class", "a-icon-alt").get(0).text()));
        product.setBrand(element.getElementsByAttributeValue("class", "a-size-small a-color-secondary").get(1).text());
        product.setThumbnail(element.getElementsByAttributeValue("alt", "Product Details").attr("src"));
        product.setKeywords(Arrays.asList(product.getTitle().split(" ")));
        return product;
    }

    // 3.8 out of 5 stars
    private double parseScore(String s) {
        String[] arg = s.split(" ");
        double score = Double.parseDouble(arg[0]);
        // double total = Double.parseDouble(arg[3]);
        return score;
    }

    public static void main(String[] args) {
        Crawler amazonCrawler = new AmazonCrawler();
        amazonCrawler.get("Apple");
    }
}