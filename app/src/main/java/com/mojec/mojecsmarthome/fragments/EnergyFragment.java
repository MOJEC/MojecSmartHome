package com.mojec.mojecsmarthome.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mojec.mojecsmarthome.R;
import com.mojec.mojecsmarthome.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class EnergyFragment extends Fragment {

    private static final String TAG = EnergyFragment.class.getSimpleName();

    private static final String URL = "https://api.androidhive.info/volley/person_object.json";
    private static final String BASE_URL = "http://205.134.191.233:9090/TMRDataService";
    private static final String ITEM_INFO = BASE_URL + "/itemInfo/item";
    private static final String LOAD_PROFILE = BASE_URL + "/loadProfileData?deviceNo=0101150138606&itemNo=49&startTime=2018-01-01%2000:00:00&endTime=2018-07-01%2000:00:00&skip=0&take=1";
    private static final String LOAD_PROFILE_2 = BASE_URL + "/loadProfileData?deviceNo=0101150138606&itemNo=313&startTime=2018-01-01%2000:00:00&endTime=2018-07-01%2000:00:00&skip=0&take=10";
    private static final String TOKEN_RECHARGE = BASE_URL + "/chargeCreditToken?deviceNo=0101150138606&token=41699982783217220077";
    private static final String RELAY_STATUS = BASE_URL + "/relay_state?deviceNo=0101150138606";
    private static final String RELAY_STATUS_CHECK = BASE_URL + "/relay_state/op_result?uuid=";


    private Button btnBalanceQuery, btnImportQuery, btnConsumptionQuery;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txtResponse, consumptionResponse, importResponse;

    // temporary string to show the parsed response
    private String jsonResponse;


    public EnergyFragment() {
        // Required empty public constructor
    }


    public static EnergyFragment newInstance(String param1, String param2) {
        EnergyFragment fragment = new EnergyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_energy, container, false);

        btnBalanceQuery = view.findViewById(R.id.btnBalanceQuery);
        btnImportQuery = view.findViewById(R.id.btnImportQuery);
        btnConsumptionQuery = view.findViewById(R.id.btnConsumptionQuery);
        txtResponse = view.findViewById(R.id.txtResponse);
        consumptionResponse = view.findViewById(R.id.consumption);
        importResponse = view.findViewById(R.id.import_energy);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnBalanceQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json object request
                makeBalanceRequest();
            }
        });

        btnImportQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeConsumptionRequest();
            }
        });

        btnConsumptionQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeConsumptionRequest();
            }
        });

        btnImportQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeImportRequest();
            }
        });

        return view;
    }


    /**
     * Method to make json object request where json response starts wtih {
     */
    private void makeBalanceRequest() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                ITEM_INFO, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response == null) {
                    Toast.makeText(getContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d(TAG, response.toString());

                try {

                    // Parsing json object response
                    // response will be a json object
                    JSONObject pageInfo = response.getJSONObject("pageInfo");

                    String endRowNum = pageInfo.getString("endRowNum");
                    String startRowNum = pageInfo.getString("startRowNum");
                    String totalRowNum = pageInfo.getString("totalRowNum");

                    jsonResponse = "";
                    jsonResponse += "End Row: " + endRowNum + "\n";
                    jsonResponse += "Start Row: " + startRowNum + "\n";
                    jsonResponse += "Total Row: " + totalRowNum + "\n";

                    JSONArray dataArray = response.getJSONArray("data");

                    if (dataArray == null || dataArray.length() == 0) {
                        return;
                    }

                    jsonResponse = "";
                    for (int i = 0; i < dataArray.length(); i++) {

                        JSONObject dataJsonObject = (JSONObject) dataArray
                                .get(i);

                        if (dataJsonObject == null) {
                            continue;
                        }

                        String itemName = dataJsonObject.getString("itemName");
                        String itemNo = dataJsonObject.getString("itemNo");
                        String obis = dataJsonObject.getString("obis");
//                        int attributeId = dataJsonObject.getInt("attributeId");
//                        int decimalNumber = dataJsonObject.getInt("decimalNumber");
//                        String dataType = dataJsonObject.getString("dataType");

                        jsonResponse += "Item Name: " + itemName + "\n";
                        jsonResponse += "Item Number: " + itemNo + "\n";
                        jsonResponse += "OBIS Code: " + obis + "\n";
//                        jsonResponse += "Attribute ID: " + attributeId + "\n";
//                        jsonResponse += "Decimal Number: " + decimalNumber + "\n";
//                        jsonResponse += "Data Type: " + dataType + "\n";
                    }

                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * Method to make json array request where response starts with [
     */
    private void makeConsumptionRequest() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                LOAD_PROFILE, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject pageInfo = response.getJSONObject("pageInfo");

                    String endRowNum = pageInfo.getString("endRowNum");
                    String startRowNum = pageInfo.getString("startRowNum");
                    String totalRowNum = pageInfo.getString("totalRowNum");

                    jsonResponse = "";
                    jsonResponse += "End Row: " + endRowNum + "\n";
                    jsonResponse += "Start Row: " + startRowNum + "\n";
                    jsonResponse += "Total Row: " + totalRowNum + "\n\n\n";

                    JSONArray dataArray = response.getJSONArray("data");

                    jsonResponse = "";
                    if (dataArray == null || dataArray.length() == 0) {
                        return;
                    }

//                    jsonResponse = "";
                    for (int i = 0; i < dataArray.length(); i++) {

                        JSONObject dataJsonObject = (JSONObject) dataArray
                                .get(i);

                        if (dataJsonObject == null) {
                            continue;
                        }

                        String meterNo = dataJsonObject.getString("meterNo");
                        String meterName = dataJsonObject.getString("meterName");
                        String meterType = dataJsonObject.getString("meterType");
                        String saveTime = dataJsonObject.getString("saveTime");
                        String captureInterval = dataJsonObject.getString("captureInterval");
                        String captureTime = dataJsonObject.getString("captureTime");
                        String itemNo = dataJsonObject.getString("49");
//                        int attributeId = dataJsonObject.getInt("attributeId");
//                        int decimalNumber = dataJsonObject.getInt("decimalNumber");
//                        String dataType = dataJsonObject.getString("dataType");

                        importResponse.setText(itemNo);
                        jsonResponse += "Item Number: " + meterNo + "\n";
                        jsonResponse += "OBIS Code: " + meterName + "\n";
                        jsonResponse += "Meter Type: " + meterType + "\n";
                        jsonResponse += "Save Time: " + saveTime + "\n";
                        jsonResponse += "Attribute ID: " + captureInterval + "\n";
                        jsonResponse += "Decimal Number: " + captureTime + "\n";
                        jsonResponse += "Total Import Active: " + itemNo + "\n";
//                        jsonResponse += "Data Type: " + dataType + "\n";
                    }

                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * Method to make json array request where response starts with [
     */
    private void makeImportRequest() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                LOAD_PROFILE, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("name");
                    String email = response.getString("email");
                    JSONObject phone = response.getJSONObject("phone");
                    String home = phone.getString("home");
                    String mobile = phone.getString("mobile");

                    jsonResponse = "";
                    jsonResponse += "Name: " + name + "\n\n";
                    jsonResponse += "Email: " + email + "\n\n";
                    jsonResponse += "Home: " + home + "\n\n";
                    jsonResponse += "Mobile: " + mobile + "\n\n";

                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
