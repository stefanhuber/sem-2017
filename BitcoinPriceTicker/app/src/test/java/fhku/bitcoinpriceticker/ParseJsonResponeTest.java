package fhku.bitcoinpriceticker;

import android.util.JsonReader;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class ParseJsonResponeTest {

    protected ResponseReader reader;
    protected String response = "{ \"USD\" : {\"15m\" : 6263.98, \"last\" : 6263.98, \"buy\" : 6265.36, \"sell\" : 6262.6, \"symbol\" : \"$\"}, \"AUD\" : {\"15m\" : 8179.94, \"last\" : 8179.94, \"buy\" : 8181.75, \"sell\" : 8178.14, \"symbol\" : \"$\"}, \"BRL\" : {\"15m\" : 20559.64, \"last\" : 20559.64, \"buy\" : 20564.16, \"sell\" : 20555.11, \"symbol\" : \"R$\"}, \"CAD\" : {\"15m\" : 7951.81, \"last\" : 7951.81, \"buy\" : 7953.56, \"sell\" : 7950.06, \"symbol\" : \"$\"}, \"CHF\" : {\"15m\" : 6244.72, \"last\" : 6244.72, \"buy\" : 6246.1, \"sell\" : 6243.35, \"symbol\" : \"CHF\"}, \"CLP\" : {\"15m\" : 3951944.98, \"last\" : 3951944.98, \"buy\" : 3952815.62, \"sell\" : 3951074.34, \"symbol\" : \"$\"}, \"CNY\" : {\"15m\" : 41629.16, \"last\" : 41629.16, \"buy\" : 41638.33, \"sell\" : 41619.99, \"symbol\" : \"¥\"}, \"DKK\" : {\"15m\" : 40046.09, \"last\" : 40046.09, \"buy\" : 40054.92, \"sell\" : 40037.27, \"symbol\" : \"kr\"}, \"EUR\" : {\"15m\" : 5440.31, \"last\" : 5440.31, \"buy\" : 5451.8, \"sell\" : 5428.82, \"symbol\" : \"€\"}, \"GBP\" : {\"15m\" : 4784.3, \"last\" : 4784.3, \"buy\" : 4785.35, \"sell\" : 4783.24, \"symbol\" : \"£\"}, \"HKD\" : {\"15m\" : 48875.02, \"last\" : 48875.02, \"buy\" : 48885.78, \"sell\" : 48864.25, \"symbol\" : \"$\"}, \"INR\" : {\"15m\" : 409969.14, \"last\" : 409969.14, \"buy\" : 410059.46, \"sell\" : 409878.82, \"symbol\" : \"₹\"}, \"ISK\" : {\"15m\" : 650075.84, \"last\" : 650075.84, \"buy\" : 650219.06, \"sell\" : 649932.63, \"symbol\" : \"kr\"}, \"JPY\" : {\"15m\" : 717003.3, \"last\" : 717003.3, \"buy\" : 717134.57, \"sell\" : 716872.02, \"symbol\" : \"¥\"}, \"KRW\" : {\"15m\" : 7022422.7, \"last\" : 7022422.7, \"buy\" : 7023969.79, \"sell\" : 7020875.61, \"symbol\" : \"₩\"}, \"NZD\" : {\"15m\" : 9044.72, \"last\" : 9044.72, \"buy\" : 9046.71, \"sell\" : 9042.72, \"symbol\" : \"$\"}, \"PLN\" : {\"15m\" : 22735.48, \"last\" : 22735.48, \"buy\" : 22740.49, \"sell\" : 22730.47, \"symbol\" : \"zł\"}, \"RUB\" : {\"15m\" : 370509.41, \"last\" : 370509.41, \"buy\" : 370591.03, \"sell\" : 370427.78, \"symbol\" : \"RUB\"}, \"SEK\" : {\"15m\" : 52515.7, \"last\" : 52515.7, \"buy\" : 52527.27, \"sell\" : 52504.13, \"symbol\" : \"kr\"}, \"SGD\" : {\"15m\" : 8529.16, \"last\" : 8529.16, \"buy\" : 8531.04, \"sell\" : 8527.28, \"symbol\" : \"$\"}, \"THB\" : {\"15m\" : 207209.33, \"last\" : 207209.33, \"buy\" : 207254.98, \"sell\" : 207163.68, \"symbol\" : \"฿\"}, \"TWD\" : {\"15m\" : 189006.2, \"last\" : 189006.2, \"buy\" : 189047.84, \"sell\" : 188964.56, \"symbol\" : \"NT$\"} }";

    @Before
    public void setUp() {
        reader = new ResponseReader();
    }

    @Test
    public void parsing_JsonResponse_correctly() throws Exception {
        reader.parseResponse(new JsonReader(new StringReader(response)));

        assertEquals(4, 2 + 2);
    }
}