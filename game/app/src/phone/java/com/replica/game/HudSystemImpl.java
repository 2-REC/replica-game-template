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
import com.replica.engine.HudButton;
import com.replica.engine.HudButtonSlider;
import com.replica.engine.HudIconCounter;
import com.replica.engine.HudSystem;
import com.replica.engine.TextureLibrary;
import android.content.Context;

/*
TODO:
 - should get everything from a file (XML) (positions, sizes, drawables, etc.)
*/

public class HudSystemImpl extends HudSystem {

//////// SYSTEM_SPECIFIC - BEGIN
    public final static int LAYOUT_TILT = 0;
    public final static int LAYOUT_BUTTONS = 1;
    public final static int LAYOUT_DEFAULT = LAYOUT_TILT;
//////// SYSTEM_SPECIFIC - END

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

//////// SYSTEM_SPECIFIC - BEGIN

// !!!! TODO: slider used for sensor movement, could be removed !!!!
// !!!! TODO: doesn't matter as control is always hidden !!!!
// ( only used for orientation sensor )
    private static final float MOVEMENT_SLIDER_BASE_X = 0.02f;       // ~0.5mm from left
    private static final float MOVEMENT_SLIDER_BASE_Y = 0.02f;       // ~0.5mm from bottom
    private static final float MOVEMENT_SLIDER_BASE_WIDTH = 1.0f;    // ~2.5cm
//    private static final float MOVEMENT_SLIDER_BASE_HEIGHT = 0.0f;   // keep ratio with width
    private static final float MOVEMENT_SLIDER_BASE_HEIGHT = 0.2f;   // ~5mm
    private static final float MOVEMENT_SLIDER_BUTTON_WIDTH = 0.2f;  // ~5mm
    private static final float MOVEMENT_SLIDER_BUTTON_HEIGHT = 0.0f; // keep ratio with width
//    private static final float MOVEMENT_SLIDER_BUTTON_HEIGHT = 0.2f; // ~0.5cm


    private static final HudElement.Size      LEFT_BUTTON_SIZE = HudElement.Size.RELATIVE;
    private static final HudElement.Alignment LEFT_BUTTON_X_ORIGIN = HudElement.Alignment.LEFT;
    private static final HudElement.Alignment LEFT_BUTTON_X_ALIGNMENT = HudElement.Alignment.LEFT;
    private static final HudElement.Alignment LEFT_BUTTON_Y_ORIGIN = HudElement.Alignment.BOTTOM;
    private static final HudElement.Alignment LEFT_BUTTON_Y_ALIGNMENT = HudElement.Alignment.BOTTOM;
    private static final float                LEFT_BUTTON_PADDING = 0.0f;

    private static final float LEFT_BUTTON_X_1 = 0.01f;     // aligned to left
    private static final float LEFT_BUTTON_Y_1 = 0.01f;     // aligned to bottom
    private static final float LEFT_BUTTON_WIDTH_1 = 0.1f;
    private static final float LEFT_BUTTON_HEIGHT_1 = 0.0f; // keep ratio with width


    private static final HudElement.Size      RIGHT_BUTTON_SIZE = HudElement.Size.RELATIVE;
    private static final HudElement.Alignment RIGHT_BUTTON_X_ORIGIN = HudElement.Alignment.LEFT;
    private static final HudElement.Alignment RIGHT_BUTTON_X_ALIGNMENT = HudElement.Alignment.LEFT;
    private static final HudElement.Alignment RIGHT_BUTTON_Y_ORIGIN = HudElement.Alignment.BOTTOM;
    private static final HudElement.Alignment RIGHT_BUTTON_Y_ALIGNMENT = HudElement.Alignment.BOTTOM;
    private static final float                RIGHT_BUTTON_PADDING = 0.0f;

    private static final float RIGHT_BUTTON_X_1 = 0.2f;
    private static final float RIGHT_BUTTON_Y_1 = 0.01f;     // aligned to bottom
    private static final float RIGHT_BUTTON_WIDTH_1 = 0.1f;
    private static final float RIGHT_BUTTON_HEIGHT_1 = 0.0f; // keep ratio with width

//////// CLIMBING 20141024 - MID
    private static final HudElement.Size      UP_BUTTON_SIZE = HudElement.Size.RELATIVE;
    private static final HudElement.Alignment UP_BUTTON_X_ORIGIN = HudElement.Alignment.LEFT;
    private static final HudElement.Alignment UP_BUTTON_X_ALIGNMENT = HudElement.Alignment.LEFT;
    private static final HudElement.Alignment UP_BUTTON_Y_ORIGIN = HudElement.Alignment.BOTTOM;
    private static final HudElement.Alignment UP_BUTTON_Y_ALIGNMENT = HudElement.Alignment.BOTTOM;
    private static final float                UP_BUTTON_PADDING = 0.0f;

    private static final float UP_BUTTON_X_1 = 0.3f;
    private static final float UP_BUTTON_Y_1 = 0.01f;     // aligned to bottom
    private static final float UP_BUTTON_WIDTH_1 = 0.1f;
    private static final float UP_BUTTON_HEIGHT_1 = 0.0f; // keep ratio with width


    private static final HudElement.Size      DOWN_BUTTON_SIZE = HudElement.Size.RELATIVE;
    private static final HudElement.Alignment DOWN_BUTTON_X_ORIGIN = HudElement.Alignment.LEFT;
    private static final HudElement.Alignment DOWN_BUTTON_X_ALIGNMENT = HudElement.Alignment.LEFT;
    private static final HudElement.Alignment DOWN_BUTTON_Y_ORIGIN = HudElement.Alignment.BOTTOM;
    private static final HudElement.Alignment DOWN_BUTTON_Y_ALIGNMENT = HudElement.Alignment.BOTTOM;
    private static final float                DOWN_BUTTON_PADDING = 0.0f;

    private static final float DOWN_BUTTON_X_1 = 0.4f;
    private static final float DOWN_BUTTON_Y_1 = 0.01f;     // aligned to bottom
    private static final float DOWN_BUTTON_WIDTH_1 = 0.1f;
    private static final float DOWN_BUTTON_HEIGHT_1 = 0.0f; // keep ratio with width
//////// CLIMBING 20141024 - END

    private static final HudElement.Size      JUMP_BUTTON_SIZE = HudElement.Size.RELATIVE;
    private static final HudElement.Alignment JUMP_BUTTON_X_ORIGIN = HudElement.Alignment.RIGHT;
    private static final HudElement.Alignment JUMP_BUTTON_X_ALIGNMENT = HudElement.Alignment.RIGHT;
    private static final HudElement.Alignment JUMP_BUTTON_Y_ORIGIN = HudElement.Alignment.BOTTOM;
    private static final HudElement.Alignment JUMP_BUTTON_Y_ALIGNMENT = HudElement.Alignment.BOTTOM;
    private static final float                JUMP_BUTTON_PADDING = 0.0f;

    private static final float JUMP_BUTTON_X_1 = 0.01f;    // aligned to right
    private static final float JUMP_BUTTON_Y_1 = 0.01f;     // aligned to bottom
    private static final float JUMP_BUTTON_WIDTH_1 = 0.1f;
    private static final float JUMP_BUTTON_HEIGHT_1 = 0.0f; // keep ratio with width

// !!!! TODO: adapt, or remove ... !!!!
    private static final float JUMP_BUTTON_X_2 = 0.01f;     // aligned to left
    private static final float JUMP_BUTTON_Y_2 = 0.01f;     // aligned to bottom
    private static final float JUMP_BUTTON_WIDTH_2 = 0.1f;
    private static final float JUMP_BUTTON_HEIGHT_2 = 0.0f; // keep ratio with width


    private static final HudElement.Size      ATTACK_BUTTON_SIZE = HudElement.Size.RELATIVE;
    private static final HudElement.Alignment ATTACK_BUTTON_X_ORIGIN = HudElement.Alignment.RIGHT;
    private static final HudElement.Alignment ATTACK_BUTTON_X_ALIGNMENT = HudElement.Alignment.RIGHT;
    private static final HudElement.Alignment ATTACK_BUTTON_Y_ORIGIN = HudElement.Alignment.BOTTOM;
    private static final HudElement.Alignment ATTACK_BUTTON_Y_ALIGNMENT = HudElement.Alignment.BOTTOM;
    private static final float                ATTACK_BUTTON_PADDING = 0.0f;

    private static final float ATTACK_BUTTON_X_1 = 0.01f;    // aligned to right
    private static final float ATTACK_BUTTON_Y_1 = 0.2f;      // ????
    private static final float ATTACK_BUTTON_WIDTH_1 = 0.1f;
    private static final float ATTACK_BUTTON_HEIGHT_1 = 0.0f; // keep ratio with width

// !!!! TODO: adapt, or remove ... !!!!
    private static final float ATTACK_BUTTON_X_2 = 0.01f;    // aligned to left
    private static final float ATTACK_BUTTON_Y_2 = 0.01f;     // aligned to bottom
    private static final float ATTACK_BUTTON_WIDTH_2 = 0.1f;
    private static final float ATTACK_BUTTON_HEIGHT_2 = 0.0f; // keep ratio with width

//////// SYSTEM_SPECIFIC - END

//////// GAME_SPECIFIC - END


//////// SYSTEM_SPECIFIC - BEGIN
    private int mLayout;
//////// SYSTEM_SPECIFIC - END

//// DIGITS - MID
    private HudDigits mHudDigits;
//// DIGITS - END

//////// GAME_SPECIFIC - BEGIN

    private HudBar mLifeBar;
    private HudIconCounter mLifeCounter;

    private HudIconCounter mCoinsCounter;
//    private HudCounter mCoinsCounter;

//////// SYSTEM_SPECIFIC - BEGIN

    private HudButtonSlider mDPad;

    private HudButton mLeftButton;
    private HudButton mRightButton;
//////// CLIMBING 20141024 - MID
    private HudButton mUpButton;
    private HudButton mDownButton;
//////// CLIMBING 20141024 - END

    private HudButton mJumpButton1;
    private HudButton mJumpButton2;

    private HudButton mAttackButton1;
    private HudButton mAttackButton2;

//////// SYSTEM_SPECIFIC - END

//////// GAME_SPECIFIC - END


    public HudSystemImpl() {
        super();

//////// SYSTEM_SPECIFIC - BEGIN

        mLayout = -1;

//////// SYSTEM_SPECIFIC - END

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

//////// SYSTEM_SPECIFIC - BEGIN

// !!!! TODO: could use dummy values (as control always hidden) !!!!
        mDPad = new HudButtonSlider(HudElement.Size.ABSOLUTE,
                                    MOVEMENT_SLIDER_BASE_X,
                                    HudElement.Alignment.LEFT,
                                    HudElement.Alignment.LEFT,
                                    MOVEMENT_SLIDER_BASE_Y,
                                    HudElement.Alignment.BOTTOM,
                                    HudElement.Alignment.BOTTOM,
                                    MOVEMENT_SLIDER_BASE_WIDTH,
                                    MOVEMENT_SLIDER_BASE_HEIGHT,
                                    0.0f,
                                    MOVEMENT_SLIDER_BUTTON_WIDTH,
                                    MOVEMENT_SLIDER_BUTTON_HEIGHT);
        addElement(mDPad);
////////

        mLeftButton = new HudButton(LEFT_BUTTON_SIZE,
                                    LEFT_BUTTON_X_1,
                                    LEFT_BUTTON_X_ORIGIN,
                                    LEFT_BUTTON_X_ALIGNMENT,
                                    LEFT_BUTTON_Y_1,
                                    LEFT_BUTTON_Y_ORIGIN,
                                    LEFT_BUTTON_Y_ALIGNMENT,
                                    LEFT_BUTTON_WIDTH_1,
                                    LEFT_BUTTON_HEIGHT_1,
                                    LEFT_BUTTON_PADDING);
        addElement(mLeftButton);

        mRightButton = new HudButton(RIGHT_BUTTON_SIZE,
                                     RIGHT_BUTTON_X_1,
                                     RIGHT_BUTTON_X_ORIGIN,
                                     RIGHT_BUTTON_X_ALIGNMENT,
                                     RIGHT_BUTTON_Y_1,
                                     RIGHT_BUTTON_Y_ORIGIN,
                                     RIGHT_BUTTON_Y_ALIGNMENT,
                                     RIGHT_BUTTON_WIDTH_1,
                                     RIGHT_BUTTON_HEIGHT_1,
                                     RIGHT_BUTTON_PADDING);
        addElement(mRightButton);

//////// CLIMBING 20141024 - MID
        mUpButton = new HudButton(UP_BUTTON_SIZE,
                                  UP_BUTTON_X_1,
                                  UP_BUTTON_X_ORIGIN,
                                  UP_BUTTON_X_ALIGNMENT,
                                  UP_BUTTON_Y_1,
                                  UP_BUTTON_Y_ORIGIN,
                                  UP_BUTTON_Y_ALIGNMENT,
                                  UP_BUTTON_WIDTH_1,
                                  UP_BUTTON_HEIGHT_1,
                                  UP_BUTTON_PADDING);
        addElement(mUpButton);

        mDownButton = new HudButton(DOWN_BUTTON_SIZE,
                                    DOWN_BUTTON_X_1,
                                    DOWN_BUTTON_X_ORIGIN,
                                    DOWN_BUTTON_X_ALIGNMENT,
                                    DOWN_BUTTON_Y_1,
                                    DOWN_BUTTON_Y_ORIGIN,
                                    DOWN_BUTTON_Y_ALIGNMENT,
                                    DOWN_BUTTON_WIDTH_1,
                                    DOWN_BUTTON_HEIGHT_1,
                                    DOWN_BUTTON_PADDING);
        addElement(mDownButton);
//////// CLIMBING 20141024 - END

        mJumpButton1 = new HudButton(JUMP_BUTTON_SIZE,
                                     JUMP_BUTTON_X_1,
                                     JUMP_BUTTON_X_ORIGIN,
                                     JUMP_BUTTON_X_ALIGNMENT,
                                     JUMP_BUTTON_Y_1,
                                     JUMP_BUTTON_Y_ORIGIN,
                                     JUMP_BUTTON_Y_ALIGNMENT,
                                     JUMP_BUTTON_WIDTH_1,
                                     JUMP_BUTTON_HEIGHT_1,
                                     JUMP_BUTTON_PADDING);
        addElement(mJumpButton1);

        mJumpButton2 = new HudButton(JUMP_BUTTON_SIZE,
                                     JUMP_BUTTON_X_2,
                                     JUMP_BUTTON_X_ORIGIN,
                                     JUMP_BUTTON_X_ALIGNMENT,
                                     JUMP_BUTTON_Y_2,
                                     JUMP_BUTTON_Y_ORIGIN,
                                     JUMP_BUTTON_Y_ALIGNMENT,
                                     JUMP_BUTTON_WIDTH_2,
                                     JUMP_BUTTON_HEIGHT_2,
                                     JUMP_BUTTON_PADDING);
        addElement(mJumpButton2);

        mAttackButton1 = new HudButton(ATTACK_BUTTON_SIZE,
                                       ATTACK_BUTTON_X_1,
                                       ATTACK_BUTTON_X_ORIGIN,
                                       ATTACK_BUTTON_X_ALIGNMENT,
                                       ATTACK_BUTTON_Y_1,
                                       ATTACK_BUTTON_Y_ORIGIN,
                                       ATTACK_BUTTON_Y_ALIGNMENT,
                                       ATTACK_BUTTON_WIDTH_1,
                                       ATTACK_BUTTON_HEIGHT_1,
                                       ATTACK_BUTTON_PADDING);
        addElement(mAttackButton1);

        mAttackButton2 = new HudButton(ATTACK_BUTTON_SIZE,
                                       ATTACK_BUTTON_X_2,
                                       ATTACK_BUTTON_X_ORIGIN,
                                       ATTACK_BUTTON_X_ALIGNMENT,
                                       ATTACK_BUTTON_Y_2,
                                       ATTACK_BUTTON_Y_ORIGIN,
                                       ATTACK_BUTTON_Y_ALIGNMENT,
                                       ATTACK_BUTTON_WIDTH_2,
                                       ATTACK_BUTTON_HEIGHT_2,
                                       ATTACK_BUTTON_PADDING);
        addElement(mAttackButton2);

//////// SYSTEM_SPECIFIC - END

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

//////// SYSTEM_SPECIFIC - BEGIN

// !!!! ???? TODO: needed ? ???? !!!!
// (if always hidden, should be ok to have no drawables)
        mDPad.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_dpad_base), 0, 0),
                           new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_dpad_off), 0, 0),
                           new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_dpad_disabled), 0, 0),
                           new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_dpad_on), 0, 0));

        mLeftButton.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_left_off), 0, 0),
                                 new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_left_disabled), 0, 0),
                                 new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_left_on), 0, 0));

        mRightButton.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_right_off), 0, 0),
                                  new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_right_disabled), 0, 0),
                                  new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_right_on), 0, 0));

//////// CLIMBING 20141024 - MID
        mUpButton.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_up_off), 0, 0),
                               new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_up_disabled), 0, 0),
                               new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_up_on), 0, 0));

        mDownButton.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_down_off), 0, 0),
                                 new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_down_disabled), 0, 0),
                                 new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_down_on), 0, 0));
//////// CLIMBING 20141024 - END

        mJumpButton1.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_jump_off), 0, 0),
                                  new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_jump_disabled), 0, 0),
                                  new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_jump_on), 0, 0));

        mJumpButton2.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_jump_off), 0, 0),
                                  new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_jump_disabled), 0, 0),
                                  new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_jump_on), 0, 0));

        mAttackButton1.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_attack_off), 0, 0),
                                    new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_attack_disabled), 0, 0),
                                    new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_attack_on), 0, 0));

        mAttackButton2.setDrawables(new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_attack_off), 0, 0),
                                    new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_attack_disabled), 0, 0),
                                    new DrawableBitmap(longTermTextureLibrary.allocateTexture(R.drawable.ui_button_attack_on), 0, 0));

//////// SYSTEM_SPECIFIC - END

//////// GAME_SPECIFIC - END
    }


//////// SYSTEM_SPECIFIC - BEGIN

    public void switchLayout(int layout) {
        mLayout = layout;

        switch (mLayout) {
            case LAYOUT_TILT:
//////// GAME_SPECIFIC - BEGIN
                mDPad.show(false);
                mLeftButton.show(false);
                mRightButton.show(false);
//////// CLIMBING 20141024 - END
// always visible
                mUpButton.show(true);
                mDownButton.show(true);
//////// CLIMBING 20141024 - END
                mJumpButton1.show(true);
                mJumpButton2.show(false);
                mAttackButton1.show(true);
                mAttackButton2.show(false);
//////// GAME_SPECIFIC - END
                break;
            case LAYOUT_BUTTONS:
//////// GAME_SPECIFIC - BEGIN
                mDPad.show(false);
                mLeftButton.show(true);
                mRightButton.show(true);
//////// CLIMBING 20141024 - END
// always visible
                mUpButton.show(true);
                mDownButton.show(true);
//////// CLIMBING 20141024 - END
                mJumpButton1.show(true);
                mJumpButton2.show(false);
                mAttackButton1.show(true);
                mAttackButton2.show(false);
//////// GAME_SPECIFIC - END
                break;
            default:
                switchLayout(LAYOUT_DEFAULT);
                break;
        }
    }

//////// SYSTEM_SPECIFIC - END


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

//////// SYSTEM_SPECIFIC - BEGIN

    public HudButtonSlider getDPad() {
        return mDPad;
    }

    public HudButton getLeftButton() {
        return mLeftButton;
    }
    public HudButton getRightButton() {
        return mRightButton;
    }
//////// CLIMBING 20141024 - MID
    public HudButton getUpButton() {
        return mUpButton;
    }

    public HudButton getDownButton() {
        return mDownButton;
    }
//////// CLIMBING 20141024 - END

// !!!! TODO: should be made more generic !!!!
// => 1 function returning the correct/current button ?
    public HudButton getJumpButton1() {
        return mJumpButton1;
    }

    public HudButton getJumpButton2() {
        return mJumpButton2;
    }

    public HudButton getAttackButton1() {
        return mAttackButton1;
    }

    public HudButton getAttackButton2() {
        return mAttackButton2;
    }

//////// SYSTEM_SPECIFIC - END

//////// GAME_SPECIFIC - END

}
