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

import com.replica.engine.GameObject;
import com.replica.engine.InventoryComponent;

/**
 * Class defining a restricted SidePlayer, not handling controllers pick-ups.
 * Must be an object that has been given control by the Player.
 * (object can't give control to another one)
 */
// !!!! TODO: rename to more generic name ... !!!!
public class PlayerComponentSideVehicle extends PlayerComponentSideImpl {
/*
    @Override
    public void reset() {
        super.reset();
        ...
    }
*/
/*
    @Override
    protected void preUpdateProcess(GameObject parent) {
        super.preUpdateProcess(parent);
        ...
    }

    @Override
    protected void postUpdateProcess(GameObject parent) {
        super.postUpdateProcess(parent);
        ...
    }
*/

    @Override
    protected void processInventory(InventoryComponent.UpdateRecord inventory, GameObject parent, float time) {
//NOT!        super.processInventory(inventory, parent, time);
// (don't want the pickup process to take place)
// => though should still be able to pick up collectibles ... ?
    }

/*
    @Override
    protected void additionalProcess(GameObject parent, float time) {
        super.additionalProcess(parent, time);
        ...
    }
*/

}
