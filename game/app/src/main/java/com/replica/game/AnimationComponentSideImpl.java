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

import com.replica.engine.AnimationComponentSide;
import com.replica.engine.GameObject;
import com.replica.engine.SoundSystem;
import com.replica.engine.SoundSystem.Sound;
import com.replica.engine.GameObject.ActionType;

/**
 * Player Animation game object component.  Responsible for selecting an animation to describe the 
 * player's current state.  Requires the object to contain a SpriteComponent to play animations.
 */
class AnimationComponentSideImpl extends AnimationComponentSide {

    private static final float LAND_THUMP_DELAY = 0.5f;

    protected PlayerComponentSideImpl mPlayer;

    private float mLandThumpDelay;
    private Sound mLandThump;


    @Override
    public void reset() {
        super.reset();

        mPlayer = null;

        mLandThump = null;
        mLandThumpDelay = 0.0f;
    }

    @Override
    protected boolean mainAttack(float gameTime, GameObject parentObject) {
        return mPlayer.isStompAttack();
    }

    @Override
    protected void handleExtra(GameObject parentObject, float gameTime) {
        final GameObject.ActionType currentAction = parentObject.getCurrentAction();

        if (currentAction == ActionType.MOVE) {
/*
            if (parentObject.touchingGround()) {
                final InputButton attackButton = input.getAttackButton();
                if (attackButton.getPressed()) {
                    // charge
                    final float pressedTime = gameTime - attackButton.getLastPressedTime();
                    final float wave = (float)Math.cos(pressedTime * (float)Math.PI * 2.0f);
                    mOpacity = (wave * 0.25f) + 0.75f;
                }
            }
*/
        } else if (currentAction == ActionType.ATTACK) {
            if (mPlayer.isStompAttack()) {
                if (parentObject.touchingGround() && gameTime > mLandThumpDelay) {
                    final SoundSystem sound = sSystemRegistry.soundSystem;
                    if (mLandThump != null && sound != null) {
                        // modulate the sound slightly to avoid sounding too similar
                        sound.play(mLandThump, false, SoundSystem.PRIORITY_HIGH, 1.0f, (float)(Math.random() * 0.5f) + 0.75f);
                        mLandThumpDelay = gameTime + LAND_THUMP_DELAY;
                    }
                }
            }
        }
    }

    @Override
    protected void deathByHit(GameObject parentObject) {
    	deathByHotSpot(parentObject);
    }

    @Override
    protected void deathByHotSpot(GameObject parentObject) {
        mHideDeath = true;

//!!!! TODO: should be able to specify the type of object to spawn !!!!
//=> to make this class more generic ("setObjectToSpawnOnDeath")
//////// GAME_TEST - MID
        spawnObject(GameObjectFactoryImpl.GameObjectType.EXPLOSION_GIANT.ordinal(),
                parentObject.getPosition().x, parentObject.getPosition().y);
//////// GAME_TEST - END
    }


    public void setPlayer(PlayerComponentSideImpl player) {
        mPlayer = player;
    }

    public void setLandThump(Sound land) {
        mLandThump = land;
    }

}
