package crawler.domain;

import lombok.Data;

/**
 * Created by liyikun on 2017/6/25.
 */
@Data
public class RawQuery {

    private String query;

    private double bidPrice;

    private int campaignId;

    private int queryGroupId;

    @Override
    public String toString() {
        return "RawQuery{" +
                "query='" + query + '\'' +
                ", bidPrice=" + bidPrice +
                ", campaignId=" + campaignId +
                ", queryGroupId=" + queryGroupId +
                '}';
    }
}
