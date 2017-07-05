package crawler.service.impl;

import crawler.domain.AmazonProduct;
import crawler.service.Crawler;
import crawler.service.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
public class AmazonCrawler implements Crawler<AmazonProduct> {

    private static final String REQUEST_URL = "https://www.amazon.com/s/ref=nb_sb_noss";

    public AmazonCrawler() {

    }

    @Override
    public Page<AmazonProduct> getPage(String query) {
        return this.getPage(query, 1);
    }

    @Override
    public Page<AmazonProduct> getPage(String query, int pageIndex) {
        Document document = this.getDocumentSecurity(query, pageIndex);
        System.out.println("——————————————————————START——————————————————————");
        System.out.println("Query : " + query + " pageIdex : " + pageIndex);
        System.out.println(document);
        if (document == null) {
            return null;
        }
        Page<AmazonProduct> page = new Page<>(pageIndex, this.getAmazonProducts(document, query));
        System.out.println("——————————————————————END——————————————————————");
        return page;
    }

    @Override
    public List<Page<AmazonProduct>> getAllPage(String query, int pageCount) {
        List<Page<AmazonProduct>> pages = new ArrayList<>();
        for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
            Page<AmazonProduct> page = this.getPage(query, pageIndex);
            if (page == null) {
                break;
            }
            pages.add(page);
        }
        return pages;
    }

    private Document getDocumentSecurity(String query, int pageIndex) {
        try {
            Document document = this.getDocument(query, pageIndex);
            if (document.getElementById("noResultsTitle") != null) {
                return null;
            }
            return document;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Just for develop
    private Document getMockDocument(String query) {
        try {
            File file = new File("mock/result.html");
            if (file.exists()) {
                System.out.println("Read document from local");
                // read
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder builder = new StringBuilder();
                String tempString;
                while ((tempString = reader.readLine()) != null) {
                    builder.append(tempString);
                }
                reader.close();
                return Jsoup.parse(file, "UTF-8", REQUEST_URL);
            } else {
                System.out.println("Read document from remote server");
                Document document = this.getDocument(query, 1);
                // save
                FileOutputStream out = new FileOutputStream(file);
                BufferedOutputStream writer = new BufferedOutputStream(out);
                writer.write(document.html().getBytes());
                return document;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Document getDocument(String query, int pageIndex) throws IOException {
        return Jsoup.connect(REQUEST_URL)
                .data("field-keywords", query)
                .data("page", String.valueOf(pageIndex))
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, sdch")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
                .header("Cache-Control", "max-age=0")
                .header("Connection", "keep-alive")
                .header("Cookie", "x-wl-uid=1RgapfiubYGU3GtT3tpVDz6FDtdxO61XT6KOl5S4GcCvtLMs0ibOG6o35GfjMUhQN8EM1YFX8ucw=; session-token=iCM6VCdSd4UKmmfJBymQUUNeb/td66T5XVg+q1pkV/AUNojj4fUcdCCqCgXPHfMP8qIIF8zWkxaGA+pTWnVUQIt1ybvMgoYGgZz3C6LCywX7rmamkJjFXrCBWnL62ABDbvME01CU8i06q0FrEJsrSLeOwkpHmHqUVLEQKX8dPyGp5e1Mhf/EUHbvIpJdf71J; skin=noskin; JSESSIONID=4F9A9FEDF30E2FE41FE1D68DF8545740; csm-hit=BK82CK8HZER1TXJH0HW9+s-TD7Z30RD39CD8FKNB8CH|1499226857590; ubid-main=135-3545536-7422912; session-id-time=2082787201l; session-id=132-4879705-4425663")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .timeout(1000)
                .get();
    }

    private List<AmazonProduct> getAmazonProducts(Document document, String query) {
        List<AmazonProduct> amazonProducts = new ArrayList<>();
        String category = document.getElementsByClass("a-size-small a-spacing-top-mini a-color-base a-text-bold").get(0).text();
        Element resultsCol = document
                .getElementById("search-main-wrapper")
                .getElementById("main")
                .getElementById("searchTemplate")
                .getElementById("rightContainerATF")
                .getElementById("rightResultsATF")
                .getElementById("resultsCol");
        this.addAmazonProducts(resultsCol
                .getElementById("centerMinus"), query, category, amazonProducts);
        this.addAmazonProducts(resultsCol
                .getElementById("btfResults"), query, category, amazonProducts);
        return amazonProducts;
    }

    private void addAmazonProducts(Element parentElement, String query, String category, List<AmazonProduct> amazonProducts) {
        if (parentElement == null) {
            return;
        }
        Elements resultElements = parentElement
                .getElementsByTag("ul");
        if (resultElements == null || resultElements.size() == 0) {
            return;
        }
//        System.out.println(resultElementss);
        Elements elements = resultElements.get(0).children();
        for (Element element : elements) {
            try {
                AmazonProduct amazonProduct = this.getAmazonProduct(element);
                amazonProduct.setQuery(query);
                amazonProduct.setCategory(category);
                amazonProducts.add(amazonProduct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private AmazonProduct getAmazonProduct(Element element) throws Exception {
//        System.out.println("id : " + element.id() + "________start________");
        AmazonProduct product = new AmazonProduct(element.attr("data-asin"));
        product.setTitle(element.getElementsByAttributeValue("class", "a-size-medium s-inline  s-access-title  a-text-normal").get(0).text());
        product.setPrice(this.parsePrise(element.getElementsByAttributeValue("class", "a-color-base sx-zero-spacing").get(0).attr("aria-label")));
        product.setThumbnail(element.getElementsByAttributeValue("alt", "Product Details").attr("src"));
        // description
        product.setBrand(element.getElementsByAttributeValue("class", "a-size-small a-color-secondary").get(1).text());
        product.setDetailUrl(element.getElementsByClass("a-link-normal s-access-detail-page  s-color-twister-title-link a-text-normal").get(0).attr("href"));

//        System.out.println("id : " + element.id() + "_________end_________");
        return product;
    }

    // $11.90 - $27.90
    private double parsePrise(String s) {
        double price = 0;
        String[] arg = s.split(" - ");
        String priceString = arg[0].substring(1).replace(",", "");
        return Double.parseDouble(priceString);
    }

    // 3.8 out of 5 stars
    private double parseScore(String s) {
        String[] arg = s.split(" ");
        double score = Double.parseDouble(arg[0]);
        // double total = Double.parseDouble(arg[3]);
        return score;
    }
}