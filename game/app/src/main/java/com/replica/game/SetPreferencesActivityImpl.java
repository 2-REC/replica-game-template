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

import com.replica.engine.PreferencesFragmentAudio;
import com.replica.engine.PreferencesFragmentGeneral;
import com.replica.engine.SetPreferencesActivitySpecific;

public class SetPreferencesActivityImpl extends SetPreferencesActivitySpecific {
 
    protected void removeSpecifics() {

//////// GAME_SPECIFIC - BEGIN

        removeSpecific(PreferenceConstantsSpecific.PREFERENCE_NB_LIVES);
        removeSpecific(PreferenceConstantsSpecific.PREFERENCE_COINS_TOTAL);
        removeSpecific(PreferenceConstantsSpecific.PREFERENCE_COINS_COLLECTED);
//        removeSpecific(PreferenceConstantsSpecific.PREFERENCE_ENEMIES_TOTAL);
        removeSpecific(PreferenceConstantsSpecific.PREFERENCE_ENEMIES_KILLED);

//////// GAME_SPECIFIC - END

    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
// !!!! ???? TODO: OK to do like this ? ???? !!!!
        return PreferencesFragmentGeneral.class.getName().equals(fragmentName) ||
                PreferencesFragmentAudio.class.getName().equals(fragmentName) ||
                PreferencesFragmentControls.class.getName().equals(fragmentName);
    }

}
