package crawler.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
@Data
public class AdPo {

    private int adId;

    private int campaignId;

    private String category;

    private String query;

    private String description;

    private String title;

    private double price;

    private int relevanceScore;

    private String brand;

    private int pClick;

    private String thumbnail;

    private double costPerClick;

    private double bidPrice;

    private int queryGroupId;

    private int position;

    private List<String> keywords;

    private String detailUrl;

    private int rankCore;

    private int qualityScore;
}
