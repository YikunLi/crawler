package crawler.domain;

import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by liyikun on 2017/6/25.
 */
@Component
public class RawQueryRepository {

    private static final String FILE_NAME = "data/rawQuery.txt";

    public void loadQueries(Callback callback) {
        try {
            File file = new File(FILE_NAME);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String temp;
            while ((temp = reader.readLine()) != null) {
                if (temp.equals("")) {
                    continue;
                }
                String[] args = temp.split(",");
                RawQuery rawQuery = new RawQuery();
                rawQuery.setQuery(args[0].trim());
                rawQuery.setBidPrice(Double.parseDouble(args[1].trim()));
                rawQuery.setCampaignId(Integer.parseInt(args[2].trim()));
                rawQuery.setQueryGroupId(Integer.parseInt(args[3].trim()));
                if (callback != null) {
                    callback.onQueryLoaded(rawQuery);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public interface Callback {

        void onQueryLoaded(RawQuery query);
    }

    public static void main(String[] args) {
        RawQueryRepository repository = new RawQueryRepository();
        repository.loadQueries(query -> System.out.println(query));
    }
}
