/*
 * Copyright (C) 2016 Source Allies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sourceallies.android.zonebeacon.api.interpreter;

import com.sourceallies.android.zonebeacon.api.executor.Executor;
import com.sourceallies.android.zonebeacon.data.model.Command;

/**
 * Interpreter for handling creating commands for a CentraLite control unit and receiving and
 * processing the results.
 */
public class CentraLiteInterpreter implements Interpreter {

    @Override
    public String getExecutable(Command command, Executor.LoadStatus loadStatus) {
        String zeros = "";

        if (command.getNumber() < 100) {
            zeros += "0";
        }

        if (command.getNumber() < 10) {
            zeros += "0";
        }

        String base;
        if (loadStatus == Executor.LoadStatus.OFF) { // we want to turn the light on
            base = command.getCommandType().getBaseSerialOnCode();
        } else { // we want to turn the lights off
            base = command.getCommandType().getBaseSerialOffCode();
        }

        String commandString;
        if (command.getControllerNumber() != null) {
            commandString = base + command.getControllerNumber() + zeros + command.getNumber();
        } else {
            commandString =  base + zeros + command.getNumber();
        }

        return commandString;
    }

    @Override
    public String processResponse(String response) {
        // TODO
        return response;
    }

}
