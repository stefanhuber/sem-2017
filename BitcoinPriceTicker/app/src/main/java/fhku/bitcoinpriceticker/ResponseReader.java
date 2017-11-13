package fhku.bitcoinpriceticker;

import android.util.JsonReader;
import android.util.Log;

public class ResponseReader {

    public double parseResponse(JsonReader jsonReader) {
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {

                if (jsonReader.nextName().equals("EUR")) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.nextName().equals("last")) {
                            final double rate = jsonReader.nextDouble();
                            return rate;
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                } else {
                    jsonReader.skipValue();
                }

            }
            jsonReader.endObject();
        } catch (Exception e) {
            Log.e("PARSING", e.getMessage());
        }

        return 0.0;
    }

}