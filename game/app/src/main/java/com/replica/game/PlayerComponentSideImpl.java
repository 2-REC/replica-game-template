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

import com.replica.engine.CameraSystem;
import com.replica.engine.DebugLog;
import com.replica.engine.EventRecorder;
import com.replica.engine.GameObject;
import com.replica.engine.GameObjectFactory;
import com.replica.engine.GameObjectManager;
//////// CLIMBING 20141023 - MID
import com.replica.engine.HotSpotSystem;
//////// CLIMBING 20141023 - END
import com.replica.engine.HudSystem;
import com.replica.engine.InputButton;
import com.replica.engine.InputXY;
import com.replica.engine.InventoryComponent;
import com.replica.engine.PlayerComponentSide;
import com.replica.engine.Utils;
import com.replica.engine.Vector2;

public class PlayerComponentSideImpl extends PlayerComponentSide {

    private float mAttackShakeTime;
    private float mAttackShakeMagnitude;
//    private float mAttackVibrateTime;

    private float mStompAttackVelocity;
    private float mStompAttackHangTime; // time waiting between start of attack, and stomping (going down)

    private float mDashAttackVelocity;
    private float mDashAttackDuration;
    private float mDashAttackHangTime; // time waiting between start of attack, and dashing (going side)

    private boolean mStompAttack;


    @Override
    public void reset() {
        super.reset();

        mAttackShakeTime = 0.15f;
        setAttackDelayTime(mAttackShakeTime);
        mAttackShakeMagnitude = 50.0f;
//        mAttackVibrateTime = 0.05f;

        mStompAttack = false;
//        mStompAttackVelocity = -500.0f;
        mStompAttackVelocity = -50.0f;
        mStompAttackHangTime = 0.0f;

        mDashAttackVelocity = 500.0f;
        mDashAttackDuration = 0.25f;
        mDashAttackHangTime = 0.0f;
    }

/*
    @Override
    protected void preUpdateProcess(GameObject parent) {
        super.preUpdateProcess(parent);
    }

    @Override
    protected void postUpdateProcess(GameObject parent) {
    }
*/

    @Override
    protected void getMovement(float time, Vector2 impulse) {
        final InputGameInterfaceImpl input = (InputGameInterfaceImpl)sSystemRegistry.inputGameInterface;
        if (input != null) {
            final InputXY dpad = input.getMovementPad();
            if (dpad.getPressed()) {
                impulse.set(dpad.getX(), 0.0f);
            }

            final InputButton jumpButton = input.getJumpButton();
            if (jumpButton.getPressed()) {
                if (jumpButton.getTriggered(time) && mTouchingGround) {
                    impulse.y = 1.0f;
                }
            }
        }
    }

    @Override
    protected boolean isAttacking(float time) {
        final InputGameInterfaceImpl input = (InputGameInterfaceImpl)sSystemRegistry.inputGameInterface;
        final InputButton attackButton = input.getAttackButton();

//        if (attackButton.getTriggered(time) && !mTouchingGround) {
        if (attackButton.getTriggered(time)) {
            return true;
        }
        return false;
    }

    @Override
    protected void startAttack(GameObject parentObject) {
// !!!! TODO: should depend on whether the player is pressing a direction or not !!!!
//        mStompAttack = !mTouchingGround;
        final InputGameInterfaceImpl input = (InputGameInterfaceImpl)sSystemRegistry.inputGameInterface;
        final InputXY dpad = input.getMovementPad();
        mStompAttack = (!mTouchingGround && Math.abs(dpad.getX()) == 0.0f);

        setAttackHoldTime(mStompAttack ? mStompAttackHangTime : mDashAttackHangTime);

        parentObject.getImpulse().zero();
        parentObject.getVelocity().set(0.0f, 0.0f);
        parentObject.positionLocked = true;

//// SWING - BEGIN
/*
final GameObjectFactoryImpl factory = (GameObjectFactoryImpl)sSystemRegistry.gameObjectFactory;
final GameObjectManager manager = sSystemRegistry.gameObjectManager;
if (factory != null && manager != null) {
    final float x = parentObject.getPosition().x;
    final float y = parentObject.getPosition().y;

//pendulum with swinging chain elements (nice movement, but never stabilises)
//    GameObject previousSwing = parentObject;
//    GameObject swing = factory.spawnPendulum(x, y, 4.0f, true, 2.0f);
//    manager.add(swing);
//    previousSwing = swing;
//    for (int i=0; i<10; ++i) {
//        swing = factory.spawnSwingCoin(previousSwing, 4.0f);
//        manager.add(swing);
//        previousSwing = swing;
//    }

//pendulum with "in between" chain elements
    GameObject swing = factory.spawnPendulum(x, y, 100.0f, true, 100.0f);
//    GameObject swing = factory.spawnSwingCoin(parentObject, 100.0f);
    manager.add(swing);
    final int nb = 5;
    for (int i=0; i<nb; ++i) {
    	final float ratio = i / (float)nb;
        GameObject between = factory.spawnSwingBetweenFixed(x, y, swing, ratio);
//        GameObject between = factory.spawnSwingBetween(parentObject, swing, ratio);
        manager.add(between);
    }
}
*/
//// SWING - END
    }

    @Override
    protected void attackBegin(GameObject parentObject) {
        if (mStompAttack) {
            parentObject.getVelocity().set(0.0f, mStompAttackVelocity);
            parentObject.positionLocked = false;
        } else {
        	final float attackVelocity = mDashAttackVelocity * Utils.sign(parentObject.facingDirection.x);
            parentObject.getVelocity().set(attackVelocity, 0.0f);
            parentObject.positionLocked = false;
        }
    }

    @Override
    protected boolean attackEndCondition(float time) {
        if (mStompAttack) {
            return mTouchingGround;
        } else {
            return (time - mTimer > mDashAttackDuration);
        }
    }

    @Override
    protected void attackEnd(GameObject parentObject) {
        if (mStompAttack) {
            final CameraSystem camera = sSystemRegistry.cameraSystem;
            if (camera != null) {
                camera.shake(mAttackShakeTime, mAttackShakeMagnitude);
            }
/*
            final VibrationSystem vibrator = sSystemRegistry.vibrationSystem;
            if (vibrator != null) {
                vibrator.vibrate(ATTACK_VIBRATE_TIME);
            }
*/

            final float x = parentObject.getPosition().x;
            final float y = parentObject.getPosition().y;
            spawnObject(GameObjectFactoryImpl.GameObjectType.DUST.ordinal(), x, y - 16, true);
            spawnObject(GameObjectFactoryImpl.GameObjectType.DUST.ordinal(), x + 32, y - 16, false);

//////// FOLLOW CAMERA - BEGIN
spawnObject(GameObjectFactoryImpl.GameObjectType.LAVA.ordinal(), 0.0f, 0.0f, false);
spawnObject(GameObjectFactoryImpl.GameObjectType.LAVA_WALL.ordinal(), 0.0f, 0.0f, false);
//////// FOLLOW CAMERA - END

        } else {
            if (parentObject.touchingLeftWall() || parentObject.touchingRightWall()) {
                final CameraSystem camera = sSystemRegistry.cameraSystem;
                if (camera != null) {
                    camera.shake(mAttackShakeTime, mAttackShakeMagnitude);
                }
/*
                final VibrationSystem vibrator = sSystemRegistry.vibrationSystem;
                if (vibrator != null) {
                    vibrator.vibrate(ATTACK_VIBRATE_TIME);
                }
*/
            }
        }
    }

    @Override
    protected void finishAttack(GameObject parentObject) {
        parentObject.positionLocked = false;
    }

/*
    @Override
    protected boolean dieCondition() {
        return mTouchingGround;
    }
*/

    @Override
    protected boolean shouldDieAtPosition(GameObject parentObject) {
        return (parentObject.getPosition().y < -parentObject.height);
    }

    @Override
    protected void die(GameObject parentObject) {
        parentObject.getVelocity().zero();
        parentObject.getTargetVelocity().zero();
    }

//////// WIN - BEGIN
    @Override
    protected void win(GameObject parentObject) {
        parentObject.getVelocity().zero();
        parentObject.getTargetVelocity().zero();

// !!!! TODO: could play a "win" animation ... !!!!
    }
//////// WIN - END

    @Override
    protected void updateHudLife(float percentage) {
        final HudSystemImpl hud = (HudSystemImpl)sSystemRegistry.hudSystem;
        if (hud != null) {
            hud.setLifePercent(percentage);
        }
    }

    @Override
    protected void updateLifeCounter(EventRecorder recorder, HudSystem hud) {
        final EventRecorderImpl rec = (EventRecorderImpl)recorder;
        rec.decrementEventCounter(EventRecorderImpl.COUNTER_LIVES, 1);
        ((HudSystemImpl) hud).setLifeCounter(rec.getNbLives());
    }

    @Override
    protected void processInventory(InventoryComponent.UpdateRecord inventory, GameObject parent, float time) {
        final UpdateRecordImpl record = (UpdateRecordImpl)inventory;
        if (record.mControllerId > 0) {
//DebugLog.e("PlayerComponentSideImpl", "controller id: " + controllerId);
//DebugLog.e("PlayerComponentSideImpl", "controller life: " + record.mControllerLife);
//DebugLog.e("PlayerComponentSideImpl", "controller time: " + record.mControllerTime);
            final int controllerId = record.mControllerId;
            final int controllerLife = record.mControllerLife;
            final float controllerTime = record.mControllerTime;

            record.resetVehicle();
            mInventory.setChanged();

            spawnController(controllerId, parent, controllerLife, controllerTime);
        }


        if (record.mSpeedUpCount >= 1) {
            final int controllerLife = record.mControllerLife;
            final float controllerTime = record.mControllerTime;

            record.mSpeedUpCount = 0;
// !!!! TODO: could have a problem if pick up a vehicle just before !!!!
            record.resetVehicle();
            mInventory.setChanged();

            spawnController(GameObjectFactoryImpl.GameObjectType.PLAYER2.ordinal(), parent, controllerLife, controllerTime);
        }

    }

    @Override
    protected void additionalProcess(GameObject parent, float time) {
DebugLog.e("---- SIDEPLAYER", "additionalProcess");
final GameObject parentObject = (GameObject)parent;
DebugLog.e("---------------", "update - x: " + parentObject.getPosition().x + ", y: " + parentObject.getPosition().y);
    }

//////// CLIMBING 20141023 - MID
    @Override
    protected int activateSwapper(float gameTime, GameObject parentObject) {
        int swapIndex = -1;

        if (mState == State.MOVE) {
DebugLog.e("SIDEPLAYER", "activateSwapper - MOVE");
// !!!! TODO: make a specific handling for each handled HotSpotType !!!!
            // swap if not jumping, pushing "up" & on a CLIMB HotSpot
            // or if not jumping, pushing "down" & above a CLIMB HotSpot
            final InputGameInterfaceImpl input = (InputGameInterfaceImpl)sSystemRegistry.inputGameInterface;
            if (input != null) {
DebugLog.e("SIDEPLAYER", "activateSwapper - input");
                // not jumping ?
                final InputButton jumpButton = input.getJumpButton();
                if (!jumpButton.getPressed() || !jumpButton.getTriggered(gameTime) || !mTouchingGround) {
DebugLog.e("SIDEPLAYER", "activateSwapper - input - not jumping");
                    // going up ?
                    final InputXY dpad = input.getMovementPad();
                    if (dpad.getPressed()) {
                        final float y = dpad.getY();
DebugLog.e("SIDEPLAYER", "activateSwapper - input - dpad");
                        if (y > 0.0f) {
DebugLog.e("SIDEPLAYER", "activateSwapper - input - up");
                            final int hotSpot = getHotSpot(parentObject);
                            swapIndex = getSwapIndex(hotSpot);
                        } else if (y < 0.0f && parentObject.touchingGround()) {
DebugLog.e("SIDEPLAYER", "activateSwapper - input - down");
                            final HotSpotSystem hotSpotSystem = sSystemRegistry.hotSpotSystem;
                            if (hotSpotSystem != null) {
                                final int xTile = hotSpotSystem.getHitTileX(parentObject.getCenteredPositionX());
                                final int yTile = hotSpotSystem.getHitTileY(parentObject.getPosition().y + 10.0f); // feet height + small offset! (not centre)
DebugLog.e("SIDEPLAYER", "activateSwapper - hotspot");
                                final int hotSpot = hotSpotSystem.getHotSpotByTile(xTile, yTile + 1);
                                swapIndex = getSwapIndex(hotSpot);
                            }
                        }
                    }
                }
            }
        }
        return swapIndex;
    }
//////// CLIMBING 20141023 - END


    public void setAttackShakeTime(float shakeTime) {
    	mAttackShakeTime = shakeTime;
    }

    public void setAttackShakeMagnitude(float shakeMagnitude) {
    	mAttackShakeMagnitude = shakeMagnitude;
    }

    public void setStompAttackVelocity(float attackVelocity) {
    	mStompAttackVelocity = attackVelocity;
    }

    public void setStompAttackHangTime(float hangTime) {
    	mStompAttackHangTime = hangTime;
    }

    public void setDashAttackVelocity(float attackVelocity) {
    	mDashAttackVelocity = attackVelocity;
    }

    public void setDashAttackHangTime(float hangTime) {
    	mDashAttackHangTime = hangTime;
    }

    public void setDashAttackDuration(float durationTime) {
    	mDashAttackDuration = durationTime;
    }

    public boolean isStompAttack() {
        return mStompAttack;
    }

}
