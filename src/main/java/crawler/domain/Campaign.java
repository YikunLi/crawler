package crawler.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by liyikun on 2017/7/2.
 */
@Data
@Entity
@Table(name = "campaign")
public class Campaign {

    @Id
    private int campaignId;

    private double budget;

}
