package io.github.joaofxs.fake_requisitions.model;

import java.util.HashMap;
import java.util.Map;

public class TemplateJson {
    private Map<String, Object> fields = new HashMap<>();

    public void addField(String key, String value){
        fields.put(key, value);
    }

    public void removeField(String key){
        fields.remove(key);
    }
    public Map<String, Object> getFields(){
        return new HashMap<>(fields);
    }
}
