package crawler.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by liyikun on 2017/6/25.
 */
public interface AmazonProductRepository extends MongoRepository<AmazonProduct, String> {
}
