package com.mojec.mojecsmarthome.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mojec.mojecsmarthome.R;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class AutomationFragment extends Fragment {

    private ToggleButton firstToggle, secondToggle, thirdToggle;
    private String mFirstToggleOn, mFirstToggleOff;
    private String mSecondToggleOn, mSecondToggleOff;
    private String mThirdToggleOn, mThirdToggleOff;
    private TextView mTextViewReplyFromServer, txtClose;
    private TextView mSwitch1, mSwitch2, mSwitch3;
    private Dialog popupDialog;
    Button btnToggle, btnNotToggle;
    boolean toggledProgrammatically;

    public AutomationFragment() {
        // Required empty public constructor
    }

    public static AutomationFragment newInstance(String param1, String param2) {
        AutomationFragment fragment = new AutomationFragment();
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
        final View view = inflater.inflate(R.layout.fragment_automation, container, false);

        popupDialog = new Dialog(getActivity());
        popupDialog.setContentView(R.layout.custom_popup);
        txtClose =(TextView) popupDialog.findViewById(R.id.txtclose);
        btnToggle = (Button) popupDialog.findViewById(R.id.btnToggle);
        btnNotToggle = (Button) popupDialog.findViewById(R.id.btnNotToggle);
        firstToggle = view.findViewById(R.id.first_toggle);
        secondToggle = view.findViewById(R.id.second_toggle);
        thirdToggle = view.findViewById(R.id.third_toggle);
        mTextViewReplyFromServer = view.findViewById(R.id.server_reply);

        mSwitch1 = view.findViewById(R.id.switch1);
        mSwitch2 = view.findViewById(R.id.switch2);
        mSwitch3 = view.findViewById(R.id.switch3);
        final String switch1 = mSwitch1.getText().toString();
        final String switch2 = mSwitch2.getText().toString();
        final String switch3 = mSwitch3.getText().toString();

        mFirstToggleOn = "1_ON";
        mFirstToggleOff = "1_OFF";
        mSecondToggleOn = "2_ON";
        mSecondToggleOff = "2_OFF";
        mThirdToggleOn = "3_ON";
        mThirdToggleOff = "3_OFF";

        firstToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (toggledProgrammatically) {
                    toggledProgrammatically = false;
                } else {
                    if (isChecked) {
                        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupDialog.show();
                        txtClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                firstToggle.toggle();
                            }
                        });
                        btnToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // The toggle is enabled
                                Toast.makeText(getActivity(), switch1 + " Toggled ON", Toast.LENGTH_SHORT).show();
                                switch (buttonView.getId()) {

                                    case R.id.first_toggle:
                                        sendMessage(mFirstToggleOn);
                                        break;
                                }
                                popupDialog.dismiss();
                            }
                        });
                        btnNotToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                firstToggle.toggle();
                            }
                        });
                        // The toggle is enabled
                    /*Toast.makeText(getActivity(), switch2 + " Toggled ON", Toast.LENGTH_SHORT).show();
                    switch (buttonView.getId()) {

                        case R.id.second_toggle:
                            sendMessage(mSecondToggleOn);
                            break;
                    }*/
                    } else {
                        // The toggle is disabled
                    /*Toast.makeText(getActivity(), switch2 + " Toggled OFF", Toast.LENGTH_SHORT).show();
                    switch (buttonView.getId()) {

                        case R.id.second_toggle:
                            sendMessage(mSecondToggleOff);
                            break;
                    }*/
                        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupDialog.show();
                        txtClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                firstToggle.toggle();
                            }
                        });
                        btnToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // The toggle is disabled
                                Toast.makeText(getActivity(), switch1 + " Toggled OFF", Toast.LENGTH_SHORT).show();
                                switch (buttonView.getId()) {

                                    case R.id.first_toggle:
                                        sendMessage(mFirstToggleOff);
                                        break;
                                }
                                popupDialog.dismiss();
                            }
                        });
                        btnNotToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                firstToggle.toggle();
                            }
                        });
                    }
                }
            }
        });

        secondToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                if (toggledProgrammatically) {
                    toggledProgrammatically = false;
                } else {
                    if (isChecked) {
                        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupDialog.show();
                        txtClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                secondToggle.toggle();
                            }
                        });
                        btnToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // The toggle is enabled
                                Toast.makeText(getActivity(), switch2 + " Toggled ON", Toast.LENGTH_SHORT).show();
                                switch (buttonView.getId()) {

                                    case R.id.second_toggle:
                                        sendMessage(mSecondToggleOn);
                                        break;
                                }
                                popupDialog.dismiss();
                            }
                        });
                        btnNotToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                secondToggle.toggle();
                            }
                        });
                        // The toggle is enabled
                    /*Toast.makeText(getActivity(), switch2 + " Toggled ON", Toast.LENGTH_SHORT).show();
                    switch (buttonView.getId()) {

                        case R.id.second_toggle:
                            sendMessage(mSecondToggleOn);
                            break;
                    }*/
                    } else {
                        // The toggle is disabled
                    /*Toast.makeText(getActivity(), switch2 + " Toggled OFF", Toast.LENGTH_SHORT).show();
                    switch (buttonView.getId()) {

                        case R.id.second_toggle:
                            sendMessage(mSecondToggleOff);
                            break;
                    }*/
                        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupDialog.show();
                        txtClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                secondToggle.toggle();
                            }
                        });
                        btnToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // The toggle is disabled
                                Toast.makeText(getActivity(), switch2 + " Toggled OFF", Toast.LENGTH_SHORT).show();
                                switch (buttonView.getId()) {

                                    case R.id.second_toggle:
                                        sendMessage(mSecondToggleOff);
                                        break;
                                }
                                popupDialog.dismiss();
                            }
                        });
                        btnNotToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                secondToggle.toggle();
                            }
                        });
                    }
                }
            }
        });

        /*secondToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(getActivity(), switch3 + " Toggled ON", Toast.LENGTH_SHORT).show();
                    switch (buttonView.getId()) {

                        case R.id.second_toggle:
                            sendMessage(mThirdToggleOn);
                            break;
                    }
                } else {
                    // The toggle is disabled
                    Toast.makeText(getActivity(), switch3 + " Toggled OFF", Toast.LENGTH_SHORT).show();
                    switch (buttonView.getId()) {

                        case R.id.second_toggle:
                            sendMessage(mThirdToggleOff);
                            break;
                    }
                }
            }
        });*/

        thirdToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                if (toggledProgrammatically) {
                    toggledProgrammatically = false;
                } else {
                    if (isChecked) {
                        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupDialog.show();
                        txtClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                thirdToggle.toggle();
                            }
                        });
                        btnToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // The toggle is enabled
                                Toast.makeText(getActivity(), switch3 + " Toggled ON", Toast.LENGTH_SHORT).show();
                                switch (buttonView.getId()) {

                                    case R.id.third_toggle:
                                        sendMessage(mThirdToggleOn);
                                        break;
                                }
                                popupDialog.dismiss();
                            }
                        });
                        btnNotToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                thirdToggle.toggle();
                            }
                        });
                        // The toggle is enabled
                    /*Toast.makeText(getActivity(), switch2 + " Toggled ON", Toast.LENGTH_SHORT).show();
                    switch (buttonView.getId()) {

                        case R.id.second_toggle:
                            sendMessage(mSecondToggleOn);
                            break;
                    }*/
                    } else {
                        // The toggle is disabled
                    /*Toast.makeText(getActivity(), switch2 + " Toggled OFF", Toast.LENGTH_SHORT).show();
                    switch (buttonView.getId()) {

                        case R.id.second_toggle:
                            sendMessage(mSecondToggleOff);
                            break;
                    }*/
                        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupDialog.show();
                        txtClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                thirdToggle.toggle();
                            }
                        });
                        btnToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // The toggle is disabled
                                Toast.makeText(getActivity(), switch3 + " Toggled OFF", Toast.LENGTH_SHORT).show();
                                switch (buttonView.getId()) {

                                    case R.id.third_toggle:
                                        sendMessage(mThirdToggleOff);
                                        break;
                                }
                                popupDialog.dismiss();
                            }
                        });
                        btnNotToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupDialog.dismiss();
                                toggledProgrammatically = true;
                                thirdToggle.toggle();
                            }
                        });
                    }
                }
            }
        });

        return view;
    }

    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnToggle;
        popupDialog.setContentView(R.layout.custom_popup);
        txtclose =(TextView) popupDialog.findViewById(R.id.txtclose);
        txtclose.setText("M");
        btnToggle = (Button) popupDialog.findViewById(R.id.btnToggle);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();
            }
        });
        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupDialog.show();
    }

    private void sendMessage(final String message) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {

            String stringData;

            @Override
            public void run() {

                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket();
                    // IP Address below is the IP address of that Device where server socket is opened.
                    InetAddress serverAddr = InetAddress.getByName("192.168.1.179");
                    DatagramPacket dp;
                    dp = new DatagramPacket(message.getBytes(), message.length(), serverAddr, 4210);
                    ds.send(dp);

                    byte[] lMsg = new byte[1000];
                    dp = new DatagramPacket(lMsg, lMsg.length);
                    ds.receive(dp);
                    stringData = new String(lMsg, 0, dp.getLength());

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String s = mTextViewReplyFromServer.getText().toString();
                        if (stringData.trim().length() != 0)
                            mTextViewReplyFromServer.setText(s + "\nFrom Server : " + stringData);

                    }
                });
            }
        });

        thread.start();
        Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_SHORT).show();
    }
}
