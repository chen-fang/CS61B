package db;

import java.util.HashMap;
import java.util.Map;

public class Database {
    Map<String,Table> tables;
    public Database() {
        tables = new HashMap<>();
    }

    public String transact(String query) {
        Parse.eval(query);
        return "YOUR CODE HERE";
    }
}
