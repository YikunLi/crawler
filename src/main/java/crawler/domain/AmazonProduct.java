package crawler.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
@Data
public class AmazonProduct {

    @Id
    private final String id;

    private String category;

    private String query;

    private String description;

    private String title;

    private double price;

    private double relevanceScore;

    private String brand;

    private int pClick;

    private String thumbnail;

    private int position;

    private List<String> keywords;

    private String detailUrl;

    private int rankCore;

    private int qualityScore;

    public AmazonProduct(String id) {
        this.id = id;
    }
}