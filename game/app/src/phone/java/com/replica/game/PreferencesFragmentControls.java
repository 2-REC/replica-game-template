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

import com.replica.engine.PreferenceConstants;
import com.replica.engine.UtilsResources;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

// !!!! TODO: see if can be same as ouya !!!!

public class PreferencesFragmentControls extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = getActivity().getApplicationContext();
        final String appName = getString(UtilsResources.getResourceIdByName(context, "string", "app_name"));

        getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
        getPreferenceManager().setSharedPreferencesName(appName + PreferenceConstants.PREFERENCE_NAME);

        addPreferencesFromResource(UtilsResources.getResourceIdByName(context, "xml", "preferences_controls"));
    }
}
