package crawler.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;

/**
 * Created by liyikun on 2017/7/5.
 */
public class AdPoRepository {

    private static final String FILE_NAME = "data/result.json";

    public void saveStart() {
        this.saveString("[", false);
    }

    public void saveEnd() {
        this.saveString("]", true);
    }

    public void saveComma() {
        this.saveString(",", true);
    }

    public void save(List<AdPo> adPos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(adPos);
            this.saveString(jsonString.substring(1, jsonString.length() - 1), true);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void saveString(String string, boolean append) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, append);
            writer.write(string);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}