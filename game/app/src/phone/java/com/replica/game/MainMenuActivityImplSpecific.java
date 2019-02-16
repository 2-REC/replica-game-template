/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.replica.game;

import com.replica.engine.MainMenuActivityImpl;
//import com.replica.engine.TouchFilter;
import com.replica.engine.PreferenceConstants;
import android.content.SharedPreferences;

// !!!! TODO: check if can move differet stuff to engine !!!!
// (& make class commmon)

public class MainMenuActivityImplSpecific extends MainMenuActivityImpl {

    protected void setInitialSettings(SharedPreferences prefs) {
        // default values for this phone game => will only be set the first time, when they don't exist yet

        SharedPreferences.Editor prefsEditor = prefs.edit();

//////// SYSTEM_SPECIFIC - BEGIN

// !!!! TODO: should add/uncomment this !!!!
/*
        TouchFilter touch = new TouchFilter();
        if (!touch.supportsMultitouch(this)) {
            prefsEditor.putBoolean(PreferenceConstants.PREFERENCE_SCREEN_CONTROLS, false);

            // Turn on tilt controls if there's nothing else.
            prefsEditor.putBoolean(PreferenceConstants.PREFERENCE_TILT_CONTROLS, true);
//            mSelectedControlsString = getString(R.string.control_setup_dialog_tilt);
        }
*/

        // buttons controls by default, no orientation sensing
        final boolean tiltControls = prefs.getBoolean(PreferenceConstants.PREFERENCE_TILT_CONTROLS, false);
        prefsEditor.putBoolean(PreferenceConstants.PREFERENCE_TILT_CONTROLS, tiltControls);

        final boolean onScreenControls = prefs.getBoolean(PreferenceConstants.PREFERENCE_SCREEN_CONTROLS, true);
        prefsEditor.putBoolean(PreferenceConstants.PREFERENCE_SCREEN_CONTROLS, onScreenControls);

// not necessary ...
//        final int movementSensitivity = prefs.getInt(PreferenceConstants.PREFERENCE_MOVEMENT_SENSITIVITY, 100);
//        prefsEditor.putInt(PreferenceConstants.PREFERENCE_MOVEMENT_SENSITIVITY, movementSensitivity);

//        final int tiltSensitivity = prefs.getInt(PreferenceConstants.PREFERENCE_TILT_SENSITIVITY, 50);
//        prefsEditor.putInt(PreferenceConstants.PREFERENCE_TILT_SENSITIVITY, tiltSensitivity);

//        final boolean flipControls = prefs.getBoolean(PreferenceConstants.PREFERENCE_FLIP, false);

//////// SYSTEM_SPECIFIC - END

        prefsEditor.commit();
    }

}
