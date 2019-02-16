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
import com.replica.engine.DrawableBitmap;
import com.replica.engine.HudBar;
import com.replica.engine.HudDigits;
import com.replica.engine.HudElement;
import com.replica.engine.HudIconCounter;
import com.replica.engine.HudSystem;
import com.replica.engine.TextureLibrary;
import android.content.Context;

// !!!! TODO: ADAPT OUYA HUD !!!!

/*
TODO:
 - should get everything from a file (XML) (positions, sizes, drawables, etc.)
 - should have all the positions and sizes checked to avoid overlaps of hud elements
*/

public class HudSystemImpl extends HudSystem {

//////// GAME_SPECIFIC - BEGIN
    private static final HudElement.Size DIGIT_SIZE = HudElement.Size.ABSOLUTE;
    private static final float DIGIT_WIDTH = 0.0f;   // keep ratio with height
    private static final float DIGIT_HEIGHT = 0.1f;  // ~2.5mm

    private static final HudElement.Size      LIFE_BAR_SIZE = HudElement.Size.ABSOLUTE;
    private static final float                LIFE_BAR_X = 0.02f; // ~0.5mm from left
    private static final HudElement.Alignment LIFE_BAR_X_ORIGIN = HudElement.Alignment.LEFT;
    private static final HudElement.Alignment LIFE_BAR_X_ALIGNMENT = HudElement.Alignment.LEFT;
    private static final float                LIFE_BAR_Y = 0.02f; // ~0.5mm from top
    private static final HudElement.Alignment LIFE_BAR_Y_ORIGIN = HudElement.Alignment.TOP;
    private static final HudElement.Alignment LIFE_BAR_Y_ALIGNMENT = HudElement.Alignment.TOP;
    private static final float                LIFE_BAR_WIDTH = 1.4f; // ~5.0mm
    private static final float                LIFE_BAR_HEIGHT = 0.2f; // ~5.0mm
    private static final float                LIFE_BAR_PADDING = 0.0f; // 0.0mm
    private static final float                LIFE_BAR_INSIDE_PADDING = 0.02f; // ~0.5mm
    private static final float                LIFE_BAR_DECREASE_SPEED = 0.75f;
    private static final float                LIFE_BAR_INCREASE_SPEED = 2.0f;

    private static final HudElement.Size      LIFE_COUNTER_SIZE = HudElement.Size.ABSOLUTE;
    private static final float                LIFE_COUNTER_X = 0.8f; // 2.0cm from right
    private static final HudElement.Alignment LIFE_COUNTER_X_ORIGIN = HudElement.Alignment.RIGHT;
    private static final HudElement.Alignment LIFE_COUNTER_X_ALIGNMENT = HudElement.Alignment.RIGHT;
    private static final float                LIFE_COUNTER_Y = -0.02f; // ~0.5mm from top
    private static final HudElement.Alignment LIFE_COUNTER_Y_ORIGIN = HudElement.Alignment.TOP;
    private static final HudElement.Alignment LIFE_COUNTER_Y_ALIGNMENT = HudElement.Alignment.TOP;
    private static final float                LIFE_COUNTER_WIDTH = 0.0f; // keep ratio with height
//    private static final float                LIFE_COUNTER_HEIGHT = 0.1f; // ~2.5mm
    private static final float                LIFE_COUNTER_HEIGHT = 0.2f; // ~5.0mm
    private static final float                LIFE_COUNTER_PADDING = 0.0f; // 0.0mm
    private static final float                LIFE_COUNTER_INSIDE_PADDING = 0.04f; // ~1.0mm
    private static final int                  LIFE_COUNTER_NB_DIGITS = 2;
    private static final boolean              LIFE_COUNTER_SHOW_ZEROES = false;

    private static final HudElement.Size      COINS_COUNTER_SIZE = HudElement.Size.ABSOLUTE;
    private static final float                COINS_COUNTER_X = -0.02f; // ~0.5mm from right
    private static final HudElement.Alignment COINS_COUNTER_X_ORIGIN = HudElement.Alignment.RIGHT;
    private static final HudElement.Alignment COINS_COUNTER_X_ALIGNMENT = HudElement.Alignment.RIGHT;
    private static final float                COINS_COUNTER_Y = -0.02f; // ~0.5mm from top
    private static final HudElement.Alignment COINS_COUNTER_Y_ORIGIN = HudElement.Alignment.TOP;
    private static final HudElement.Alignment COINS_COUNTER_Y_ALIGNMENT = HudElement.Alignment.TOP;
    private static final float                COINS_COUNTER_WIDTH = 0.0f; // keep ratio with height
//    private static final float                COINS_COUNTER_HEIGHT = 0.1f; // ~2.5mm
    private static final float                COINS_COUNTER_HEIGHT = 0.2f; // ~5.0mm
    private static final float                COINS_COUNTER_PADDING = 0.0f; // 0.0mm
    private static final float                COINS_COUNTER_INSIDE_PADDING = 0.04f; // ~1.0mm
    private static final int                  COINS_COUNTER_NB_DIGITS = 2;
    private static final boolean              COINS_COUNTER_SHOW_ZEROES = true;

// !!!! TODO: add chilli bonus stuff !!!!
//...
// !!!! TODO: add tequila bonus stuff !!!!
//...
//////// GAME_SPECIFIC - END


//// DIGITS - MID
    private HudDigits mHudDigits;
//// DIGITS - END

//////// GAME_SPECIFIC - BEGIN

    private HudBar mLifeBar;
    private HudIconCounter mLifeCounter;

    private HudIconCounter mCoinsCounter;
//    private HudCounter mCoinsCounter;

//////// GAME_SPECIFIC - END


    public HudSystemImpl() {
        super();

//// DIGITS - MID
        mHudDigits = new HudDigits();
        mHudDigits.setSizeFactors(DIGIT_SIZE, DIGIT_WIDTH, DIGIT_HEIGHT);
//// DIGITS - END

//////// GAME_SPECIFIC - BEGIN

// !!!! ???? TODO : should use a pool of hud elements ? ???? !!!!
        mLifeBar = new HudBar(LIFE_BAR_SIZE,
                              LIFE_BAR_X,
                              LIFE_BAR_X_ORIGIN,
                              LIFE_BAR_X_ALIGNMENT,
                              LIFE_BAR_Y,
                              LIFE_BAR_Y_ORIGIN,
                              LIFE_BAR_Y_ALIGNMENT,
                              LIFE_BAR_WIDTH,
                              LIFE_BAR_HEIGHT,
                              LIFE_BAR_PADDING,
                              LIFE_BAR_INSIDE_PADDING,
                              LIFE_BAR_DECREASE_SPEED,
                              LIFE_BAR_INCREASE_SPEED);
        addElement(mLifeBar);


        mLifeCounter = new HudIconCounter(LIFE_COUNTER_SIZE,
                                          LIFE_COUNTER_X,
                                          LIFE_COUNTER_X_ORIGIN,
                                          LIFE_COUNTER_X_ALIGNMENT,
                                          LIFE_COUNTER_Y,
                                          LIFE_COUNTER_Y_ORIGIN,
                                          LIFE_COUNTER_Y_ALIGNMENT,
                                          LIFE_COUNTER_WIDTH,
                                          LIFE_COUNTER_HEIGHT,
                                          LIFE_COUNTER_PADDING,
                                          LIFE_COUNTER_INSIDE_PADDING,
                                          mHudDigits,
                                          LIFE_COUNTER_NB_DIGITS,
                                          LIFE_COUNTER_SHOW_ZEROES);
        addElement(mLifeCounter);

        mCoinsCounter = new HudIconCounter(COINS_COUNTER_SIZE,
                                           COINS_COUNTER_X,
                                           COINS_COUNTER_X_ORIGIN,
                                           COINS_COUNTER_X_ALIGNMENT,
                                           COINS_COUNTER_Y,
                                           COINS_COUNTER_Y_ORIGIN,
                                           COINS_COUNTER_Y_ALIGNMENT,
                                           COINS_COUNTER_WIDTH,
                                           COINS_COUNTER_HEIGHT,
                                           COINS_COUNTER_PADDING,
                                           COINS_COUNTER_INSIDE_PADDING,
                                           mHudDigits,
                                           COINS_COUNTER_NB_DIGITS,
                                           COINS_COUNTER_SHOW_ZEROES);
/*
        mCoinsCounter = new HudCounter(COINS_COUNTER_SIZE,
                                       COINS_COUNTER_X,
                                       COINS_COUNTER_X_ORIGIN,
                                       COINS_COUNTER_X_ALIGNMENT,
                                       COINS_COUNTER_Y,
                                       COINS_COUNTER_Y_ORIGIN,
                                       COINS_COUNTER_Y_ALIGNMENT,
                                       COINS_COUNTER_PADDING,
                                       mHudDigits,
                                       COINS_COUNTER_NB_DIGITS,
                                       COINS_COUNTER_SHOW_ZEROES);
*/
        addElement(mCoinsCounter);

//////// GAME_SPECIFIC - END

        reset();
    }

/*
    @Override
    public void reset() {
        super.reset();
    }
*/

/*
    @Override
    public void update(float timeDelta, BaseObject parent) {
        super.update(timeDelta, parent);
    }
*/

    @Override
    public void setDrawables(Context context) {
        super.setDrawables(context);

        TextureLibrary longTermTextureLibrary = BaseObject.sSystemRegistry.longTermTextureLibrary;

//// DIGITS - MID
        DrawableBitmap[] digits = {
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_0), 0, 0),
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_1), 0, 0),
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_2), 0, 0),
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_3), 0, 0),
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_4), 0, 0),
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_5), 0, 0),
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_6), 0, 0),
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_7), 0, 0),
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_8), 0, 0),
                new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_9), 0, 0),
        };
        mHudDigits.setDrawables(digits, false);
//// DIGITS - END

//////// GAME_SPECIFIC - BEGIN

// !!!! TODO: change drawables names !!!!
// (and associated resources files)
        mLifeBar.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_lifebar), 0, 0),
                              new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_lifebar_background), 0, 0));


        mLifeCounter.setDrawable(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_lives), 0, 0));

        mCoinsCounter.setDrawable(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_points), 0, 0));

//////// GAME_SPECIFIC - END
    }


//////// GAME_SPECIFIC - BEGIN

    public void setLifePercent(float percent) {
        mLifeBar.setPercent(percent);
    }

/*
    public HudBar getLife() {
        return mLifeBar;
    }
*/
    public void setLifeCounter(int value) {
        mLifeCounter.updateCounter(value);
    }

    public HudIconCounter getLifeCounter() {
        return mLifeCounter;
    }

    public HudIconCounter getCoinsCounter() {
//    public HudCounter getCoinsCounter() {
        return mCoinsCounter;
    }

//////// GAME_SPECIFIC - END

}
