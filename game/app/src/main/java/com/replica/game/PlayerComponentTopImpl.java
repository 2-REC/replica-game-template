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

import com.replica.engine.EventRecorder;
import com.replica.engine.GameObject;
import com.replica.engine.HudSystem;
import com.replica.engine.InputButton;
import com.replica.engine.InputXY;
import com.replica.engine.InventoryComponent;
import com.replica.engine.PlayerComponentTop;
import com.replica.engine.Utils;
import com.replica.engine.Vector2;

public class PlayerComponentTopImpl extends PlayerComponentTop {
/*
    private float mAttackShakeTime;
    private float mAttackShakeMagnitude;
//    private float mAttackVibrateTime;
*/
    private float mAttackVelocity;
    private float mAttackDuration;
    private float mAttackHangTime;


    @Override
    public void reset() {
        super.reset();

        mAttackVelocity = 100.0f;
        mAttackDuration = 0.25f;
        mAttackHangTime = 0.0f;
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
    protected void getMovement(float time, Vector2 impulse) {
        final InputGameInterfaceImpl input = (InputGameInterfaceImpl)sSystemRegistry.inputGameInterface;
        if (input != null) {
            final InputXY dpad = input.getMovementPad();
            if (dpad.getPressed()) {
                impulse.x = dpad.getX();
                impulse.y = dpad.getY();
            }

//            final InputButton jumpButton = input.getJumpButton();
//            ...
        }
    }

    @Override
    protected boolean isAttacking(float time) {
        final InputGameInterfaceImpl input = (InputGameInterfaceImpl)sSystemRegistry.inputGameInterface;

        final InputButton attackButton = input.getAttackButton();

        if (attackButton.getTriggered(time)) {
            return true;
        }

        return false;
    }

    @Override
    protected void startAttack(GameObject parentObject) {
// !!!! TODO: adapt ... !!!!
// (take out sword? start playing animation?)
//        final InputGameInterfaceImpl input = (InputGameInterfaceImpl)sSystemRegistry.inputGameInterface;
//        final InputXY dpad = input.getMovementPad();

        setAttackHoldTime(mAttackHangTime);

        parentObject.getImpulse().zero();
        parentObject.getVelocity().set(0.0f, 0.0f);
        parentObject.positionLocked = true;
    }

    @Override
    protected void attackBegin(GameObject parentObject) {
// !!!! TODO: adapt ... !!!!
        final float attackVelocityX = mAttackVelocity * Utils.sign(parentObject.facingDirection.x);
        final float attackVelocityY = mAttackVelocity * Utils.sign(parentObject.facingDirection.y);
        parentObject.getVelocity().set(attackVelocityX, attackVelocityY);
        parentObject.positionLocked = false;
    }

    @Override
    protected boolean attackEndCondition(float time) {
// !!!! TODO: adapt ... !!!!
// => should stop when hit a wall
        return (time - mTimer > mAttackDuration);
    }


    @Override
    protected void attackEnd(GameObject parentObject) {
// !!!! TODO: adapt ... !!!!
// (shake camera when hit wall, etc.)
/*
        if (parentObject.touchingLeftWall() || parentObject.touchingRightWall() ||
                parentObject.touchingCeiling() || parentObject.touchingGround()) {
            final CameraSystem camera = sSystemRegistry.cameraSystem;
            if (camera != null) {
                camera.shake(mAttackShakeTime, mAttackShakeMagnitude);
            }

//            final VibrationSystem vibrator = sSystemRegistry.vibrationSystem;
//            if (vibrator != null) {
//                vibrator.vibrate(ATTACK_VIBRATE_TIME);
//            }
        }
*/
    }

    @Override
    protected void finishAttack(GameObject parentObject) {
// !!!! TODO: adapt ... !!!!
        parentObject.positionLocked = false;
    }

/*
    @Override
    protected boolean dieCondition() {
        ...
        return ...;
    }
*/
/*
    @Override
    protected boolean shouldDieAtPosition(GameObject parentObject) {
        ...
        return ...;
    }
*/

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
    protected void processInventory(InventoryComponent.UpdateRecord inventory,
            GameObject parent, float time) {
//        final UpdateRecordImpl record = (UpdateRecordImpl)inventory;
//        ...
    }

    @Override
    protected void additionalProcess(GameObject parent, float time) {
    }

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


/*
    public void setAttackShakeTime(float shakeTime) {
    	mAttackShakeTime = shakeTime;
    }

    public void setAttackShakeMagnitude(float shakeMagnitude) {
    	mAttackShakeMagnitude = shakeMagnitude;
    }
*/
    public void setAttackVelocity(float attackVelocity) {
    	mAttackVelocity = attackVelocity;
    }

    public void setAttackHangTime(float hangTime) {
    	mAttackHangTime = hangTime;
    }

    public void setAttackDuration(float durationTime) {
    	mAttackDuration = durationTime;
    }

}
