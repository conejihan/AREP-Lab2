package edu.escuelaing.arep.nanoSpark.componentes;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final HashMap<String, String> valuesHashMap;

    public Request(String path) {
        valuesHashMap = new HashMap<>();
        int indexOfValue = path.indexOf("?");
        if (indexOfValue >= 0) {
            String values = path.substring(indexOfValue + 1);
            setValues(values);
        }
    }

    private void setValues(String values) {
        String[] valuesList = values.split("%26");
        for (String value : valuesList) {
            String[] valuePair = value.split("=");
            valuesHashMap.put(valuePair[0], valuePair[1]);
        }
    }

    public Map<String, String> queryParams() {
        return valuesHashMap;
    }

    public String queryParams(String key){
        return valuesHashMap.get(key);
    }
}
