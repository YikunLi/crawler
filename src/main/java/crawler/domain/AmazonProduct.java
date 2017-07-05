package crawler.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
@Data
public class AmazonProduct {

    private final String id;

    private String title;

    private double price;

    private String thumbnail;

    private String description;

    private String brand;

    private String detailUrl;

    private String category;

    private String query;

    public AmazonProduct(String id) {
        this.id = id;
    }
}