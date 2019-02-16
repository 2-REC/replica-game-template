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

import com.replica.engine.BaseObject;
import com.replica.engine.InventoryComponent.UpdateRecord;
import com.replica.engine.HudIconCounter;

/**
 * UpdateRecord implementation for data linked to the Hud.
 * (& where data must be persistent between consecutive "reset" calls).
 */
public class UpdateRecordHudImpl extends UpdateRecordImpl {

//////// GAME_SPECIFIC - BEGIN
    private HudIconCounter mHudCounterLife;
    private HudIconCounter mHudCounterCoins;
//    private HudCounter mHudCounterCoins;
//    private HudIconCounter mHudCounterEnnemies;
//////// GAME_SPECIFIC - END

/*
    public UpdateRecordHudImpl() {
        super();
    }
*/

    @Override
    public void reset() {
        super.reset();

        final HudSystemImpl hud = (HudSystemImpl)sSystemRegistry.hudSystem;

//////// GAME_SPECIFIC - BEGIN

        mHudCounterLife = hud.getLifeCounter();
        mLifeCount = mHudCounterLife.getCounterValue();

        mHudCounterCoins = hud.getCoinsCounter();
        mCoinsCount = mHudCounterCoins.getCounterValue();

//        mHudCounterEnnemies = hud.getEnnemiesCounter();
//        mEnnemiesCount = mHudCounterEnnemies.getCounterValue();

//////// GAME_SPECIFIC - END

//        resetRecord();
    }

    public void resetRecord() {
        reset();

//////// GAME_SPECIFIC - BEGIN

        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        mLifeCount = evtRec.getNbLives();
        mHudCounterLife.updateCounter(mLifeCount);

        mCoinsCount = 0;
        mHudCounterCoins.updateCounter(mCoinsCount);

//        mEnnemiesCount = 0;
//        mHudCounterEnnemies.updateCounter(mEnnemiesCount);

//////// GAME_SPECIFIC - END
    }

    @Override
    public void updateChange() {
        super.updateChange();

//////// GAME_SPECIFIC - BEGIN

        mHudCounterLife.updateCounter(mLifeCount);
        mHudCounterCoins.updateCounter(mCoinsCount);
//        mHudCounterEnnemies.updateCounter(mEnnemiesCount);

//////// GAME_SPECIFIC - END
    }

    @Override
    public void copy(UpdateRecord otherRecord) {
        final UpdateRecordHudImpl other = (UpdateRecordHudImpl)otherRecord;
        if (other != null) {
//////// GAME_SPECIFIC - BEGIN

            this.mHudCounterLife = other.mHudCounterLife;
            this.mHudCounterCoins = other.mHudCounterCoins;
//            this.mHudCounterEnnemies = other.mHudCounterEnnemies;

//////// GAME_SPECIFIC - END
        }
        super.copy(otherRecord);
    }


//////// GAME_SPECIFIC - BEGIN

    public void resetCoinsCounter() {
        mCoinsCount = 0;
        mHudCounterCoins.updateCounter(0);
    }

/*
    public void resetEnnemiesCounter() {
        mEnnemiesCount = 0;
        mHudCounterEnnemies.updateCounter(0);
    }
*/

//////// GAME_SPECIFIC - END

}
