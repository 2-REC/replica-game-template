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
import com.replica.engine.HotSpotSystem;
import com.replica.engine.InputButton;
import com.replica.engine.InputXY;

import com.replica.engine.DebugLog;

/**
 * Component allowing the player to climb.
 * Swapped from other PlaterComponent when interacting with a "CLIMB" HotSpot.
 */
public class PlayerComponentTopClimber extends PlayerComponentTopImpl {

    @Override
    public void reset() {
        super.reset();

        mGroundMaxSpeed = 250.0f;
        mGroundImpulseSpeed = 250.0f;
    }

/*
    @Override
    protected void preUpdateProcess(GameObject parent) {
    }

    @Override
    protected void postUpdateProcess(GameObject parent) {
    }
*/

    @Override
    protected boolean isAttacking(float time) {
        return false; // no attacking
    }

/*
    @Override
    protected void startAttack(GameObject parentObject) {
    }
    @Override
    protected void attackBegin(GameObject parentObject) {
    }
    @Override
    protected boolean attackEndCondition(float time) {
        return true;
    }
    @Override
    protected void attackEnd(GameObject parentObject) {
    }
    @Override
    protected void finishAttack(GameObject parentObject) {
    }
*/

/*
    @Override
    protected void processInventory(InventoryComponent.UpdateRecord inventory,
            GameObject parent, float time) {
//        final UpdateRecordImpl record = (UpdateRecordImpl)inventory;
    }
*/
/*
    @Override
    protected void additionalProcess(GameObject parent, float time) {
    }
*/

    @Override
    protected GameObject.ActionType releaseSwapper(float gameTime, GameObject parentObject) {
        // ATTACK => nothing (can have a different attack, can't use the "normal" attack)
        // DEAD? => handled by TakeControlComponent, will kill object & set controlled object's life to 0 (=> DEATH)
        // WIN => kills controlled object in "stateWin"
        // WAIT_ANIM => nothing (can stay with current object)
        // HIT_REACT => handled in HitReactionComponent
        // FROZEN => handle when HIT_REACT

        GameObject.ActionType actionType = GameObject.ActionType.INVALID;
        switch (mState) {
            case MOVE: // swap if jumping or if not on a CLIMB HotSpot
DebugLog.e("TOPPLAYER", "releaseSwapper - MOVE");
                // not on a CLIMB HotSpot
                final HotSpotSystem hotSpotSystem = sSystemRegistry.hotSpotSystem;
                if (hotSpotSystem != null) {
                    // check if not on a CLIMB HotSpot (centre & bottom of player)
                    final float centeredX = parentObject.getCenteredPositionX();
                    final int hotSpotCentre = hotSpotSystem.getHotSpot(centeredX, parentObject.getCenteredPositionY());
// !!!! TODO: check that ok !!!!
//                    if (!isSwapperHotSpot(hotSpotCentre)) {
                    if (hotSpotCentre != HotSpotSystem.HotSpotType.SWAP_CLIMB) {
                        final int hotSpotBottom = hotSpotSystem.getHotSpot(centeredX, parentObject.getPosition().y);
//                        if (!isSwapperHotSpot(hotSpotBottom)) {
                        if (hotSpotBottom != HotSpotSystem.HotSpotType.SWAP_CLIMB) {
                            actionType = GameObject.ActionType.MOVE;
                        }
                    }
                }
    
                if (actionType == GameObject.ActionType.INVALID) {
                    final InputGameInterfaceImpl input = (InputGameInterfaceImpl)sSystemRegistry.inputGameInterface;
                    if (input != null) {
                        // jumping
                        final InputButton jumpButton = input.getJumpButton();
                        if (jumpButton.getPressed() && jumpButton.getTriggered(gameTime)) {
DebugLog.e("TOPPLAYER", "releaseSwapper - JUMP");
                            actionType = GameObject.ActionType.MOVE;
                        } else {
DebugLog.e("TOPPLAYER", "releaseSwapper - ELSE");
                            // pushing "down" & touching bottom
                            final InputXY dpad = input.getMovementPad();
                            if (dpad.getPressed() && dpad.getY() < 0.0f && parentObject.touchingGround()) {
DebugLog.e("TOPPLAYER", "releaseSwapper - SWAP");
                                actionType = GameObject.ActionType.MOVE;
                            }
                        }
                    }
                }
                break;

            default:
                break;
        }

        return actionType;
    }

}
