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

import com.replica.engine.ResultsActivitySpecific;
import com.replica.engine.MusicManager;
import com.replica.engine.SoundManager;

public class ResultsGameOverActivityImpl extends ResultsActivitySpecific {
    @Override
    protected void addContent() {

//////// GAME_SPECIFIC - BEGIN

        addPercentageResult("results_coins_collected_total", "coins_collected", "coins_total", SoundManager.SOUND_OK);

//////// GAME_SPECIFIC - END

    }

    @Override
    protected int getMusic() {
        return MusicManager.MUSIC_GAME_WIN;
    }

}
