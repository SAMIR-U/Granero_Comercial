package co.elgranero.persistence;

import java.util.List;
import java.util.Map;

public class SqlQueryInstructions {
    public List<String> create_tables_order;

    public Map<String, String> creates;
    public Map<String, String> inserts;
    public Map<String, String> selects;
    public Map<String, String> updates;
}
