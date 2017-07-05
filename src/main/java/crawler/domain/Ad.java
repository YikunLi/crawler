package crawler.domain;

import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by liyikun on 2017/7/2.
 */
@Entity
@Data
@Table(name = "ad")
public class Ad {

    @Id
    private int adId;

    private int campaignId;

    private String keyWords;

    private double bidPrice;

    private double price;

    private String tumbnail;

    private String description;

    private String brand;

    private String detailUrl;

    private String category;

    private String title;
    
}
