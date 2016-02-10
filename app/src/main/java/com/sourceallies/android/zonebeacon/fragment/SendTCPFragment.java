package com.sourceallies.android.zonebeacon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sourceallies.android.zonebeacon.R;
import com.sourceallies.android.zonebeacon.data.Command;
import com.sourceallies.android.zonebeacon.util.CommandExecutor;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * Fragment that allows you to send plain text commands to the centralite briefcase.
 * It will display the responses in the top TextView section.
 *
 * It sends the commands to 192.168.1.150:11000 by default
 */
public class SendTCPFragment extends RoboFragment {

    private static final String IP = "192.168.1.150";
    private static final int PORT = 11000;

    // injecting this singleton allows you to execute the commands serially (with a queue).
    @Inject CommandExecutor commandExecuter;

    // inject the views so that we don't have to manually set them
    @InjectView(R.id.response)      TextView responseText;
    @InjectView(R.id.command_text)  EditText commandText;
    @InjectView(R.id.send_command)  Button sendButton;

    // creates the layout for the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_tcp, container, false);
    }

    // called after the layout is created and the views are injected.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Command command = new Command(IP, PORT)
                        .setCommand(commandText.getText().toString())
                        .setResponseCallback(new Command.CommandCallback() {
                            @Override
                            public void onResponse(String text) {
                                responseText.setText(text);
                            }
                        });

                commandExecuter.sendCommand(command);
            }
        });
    }
}
