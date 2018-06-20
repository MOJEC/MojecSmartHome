package com.mojec.mojecsmarthome.app;

/**
 * Created by Lincoln on 06/01/16.
 */
public class EndPoints {

    // localhost url
    // public static final String BASE_URL = "http://192.168.0.101/gcm_chat/v1";

    public static final String BASE_URL = "http://205.134.191.233:9090/TMRDataService";
    public static final String ITEM_INFO = BASE_URL + "/itemInfo/item";
    public static final String LOAD_PROFILE = BASE_URL + "/loadProfileData?deviceNo=0101150138606&itemNo=49&startTime=2015-01-01%2000:00:00&endTime=2015-07-01%2000:00:00&skip=0&take=5000";
    public static final String TOKEN_RECHARGE = BASE_URL + "/chargeCreditToken?deviceNo=0101150138606&token=41699982783217220077";
    public static final String RELAY_STATUS = BASE_URL + "/relay_state?deviceNo=0101150138606";
    public static final String RELAY_STATUS_CHECK = BASE_URL + "/relay_state/op_result?uuid=";
}
