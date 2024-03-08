package persistence;

import org.json.JSONObject;

// sourced from JsonSerializationDemo project provided for reference.

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
