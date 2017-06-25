package crawler.domain;

import org.springframework.stereotype.Component;

/**
 * Created by liyikun on 2017/6/25.
 */
@Component
public class RawQueryRepository {

    public void loadQuerys(Callback callback) {

    }

    public interface Callback {

        void onQueryLoaded(RawQuery query);
    }
}
