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

import com.replica.engine.InventoryComponent;
import com.replica.engine.InventoryComponent.UpdateRecord;

/*
TODO :
 - Make this more generic ( arrays of counters & hud counters, and constants indicating index in arrays )
    => imply changes in InventoryComponent, UpdateRecordImpl, EventRecorder(Impl), HudSystemImpl, GameObjectFactoryImpl, ...

 - should have a list of "HudCounter"/"HudIconCounter" variables
    => if not null, linked to a hud counter element
       private ArrayList<HudIconCounter> mHudCounters = new ArrayList<HudIconCounter>();
 - same with list of variables

 - should handle "icons array"
*/

public class UpdateRecordImpl extends InventoryComponent.UpdateRecord {
    public int mLifeCount;

//////// GAME_SPECIFIC - BEGIN
    public int mCoinsCount;
//    public int mEnnemiesCount;

//////// SPEEDUP - BEGIN
    public int mSpeedUpCount;
//////// SPEEDUP - END

//////// VEHICLE 20140902 - BEGIN
    public int mControllerId;
    public int mControllerLife;
    public float mControllerTime;
//////// VEHICLE 20140902 - END
//////// GAME_SPECIFIC - END


    public UpdateRecordImpl() {
        super();
    }

    @Override
    public void reset() {
        mLifeCount = 0;

//////// GAME_SPECIFIC - BEGIN
        mCoinsCount = 0;
//        mEnnemiesCount = 0;

//////// SPEEDUP - BEGIN
        mSpeedUpCount = 0;
//////// SPEEDUP - END

//////// VEHICLE 20140902 - BEGIN
        mControllerId = 0;
        mControllerLife = 0;
        mControllerTime = 0.0f;
//////// VEHICLE 20140902 - END
//////// GAME_SPECIFIC - END
    }

    @Override
    public void add(UpdateRecord other) {
        if (other instanceof UpdateRecordImpl) {
            final UpdateRecordImpl otherImpl = (UpdateRecordImpl)other;

            mLifeCount += otherImpl.mLifeCount;

//////// GAME_SPECIFIC - BEGIN
            mCoinsCount += otherImpl.mCoinsCount;
//            mEnnemiesCount += otherImpl.mEnnemiesCount;

//////// SPEEDUP - BEGIN
            mSpeedUpCount += otherImpl.mSpeedUpCount;
//////// SPEEDUP - END

//////// VEHICLE 20140902 - BEGIN
// !!!! ???? TODO: ok to have that here ? ???? !!!!
            mControllerId += otherImpl.mControllerId;
            mControllerLife += otherImpl.mControllerLife;
            mControllerTime += otherImpl.mControllerTime;
//////// VEHICLE 20140902 - END
//////// GAME_SPECIFIC - END
        }

    }


    @Override
    public void updateChange() {
//////// GAME_SPECIFIC - BEGIN
//////// GAME_SPECIFIC - END
    }

    public void copy(UpdateRecord otherRecord) {
        final UpdateRecordImpl other = (UpdateRecordImpl)otherRecord;

        this.mLifeCount = other.mLifeCount;

//////// GAME_SPECIFIC - BEGIN

        this.mCoinsCount = other.mCoinsCount;

//        this.mEnnemiesCount = other.mEnnemiesCount;

//////// SPEEDUP - BEGIN
        this.mSpeedUpCount = other.mSpeedUpCount;
//////// SPEEDUP - END

//////// VEHICLE 20140902 - BEGIN
        this.mControllerId = other.mControllerId;
        this.mControllerLife = other.mControllerLife;
        this.mControllerTime = other.mControllerTime;
//////// VEHICLE 20140902 - END

//////// GAME_SPECIFIC - END
    }


//////// GAME_SPECIFIC - BEGIN

//////// VEHICLE 20140902 - BEGIN
    public void resetVehicle() {
        mControllerId = 0;
        mControllerLife = 0;
        mControllerTime = 0;
    }
//////// VEHICLE 20140902 - END

//////// GAME_SPECIFIC - END

}
