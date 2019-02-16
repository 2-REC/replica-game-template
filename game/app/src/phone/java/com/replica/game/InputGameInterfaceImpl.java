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

import com.replica.engine.InputInterfaceSliderImpl;
import com.replica.engine.InputInterfaceDPadImpl;
import com.replica.engine.InputInterfaceButtonImpl;
import com.replica.engine.InputSystemImpl;
import com.replica.engine.BaseObject;
import com.replica.engine.HudButton;
import com.replica.engine.HudButtonSlider;
import com.replica.engine.InputButton;
import com.replica.engine.InputGameInterface;
import com.replica.engine.InputInterfaceSlider;
import com.replica.engine.InputXY;

/*
!!!! TODO: should make the controls stuff more generic !!!!

=> GENERIC: use lists of controls (Buttons, DPads & Sliders) and add elements to them

=> read the controls configuration from an XML file
    ( containing the position, size and drawables for each control )
    - look if a "custom_config" file exists for the current layout (onscreen or orientation)
      - if exists: load the file (located where ?)
      - if doesn't exist: load the default file (located in "res/xml")
=> make extra activity to configure the controls and save the data in an XML file (named according to current layout)
!!!! => "custom_config" file should only contain "overridden" values, that differs from default config
        ( would avoid having to specify the drawables in the file )
        ( and could also specify the rest of the hud elements in the default config file )

    - create all the controls in "InputGameInterface" instead of "HudSystemImpl"
    => with that, getting rid of the "get..." functions from "HudSystemImpl"
        ( could actually get rid of "HudSystemImpl" completely, and handle everything generically in "HudSystem"
*/

/**
 * Input implementation for normal Android device (on-screen controls & orientation sensor).
 */
public class InputGameInterfaceImpl extends InputGameInterface {

//////// SYSTEM_SPECIFIC - BEGIN

    private HudSystemImpl mHud;
    private int mLayout;

//////// SYSTEM_SPECIFIC - END

//////// GAME_SPECIFIC - BEGIN

    private InputInterfaceSlider mMovementPad;
    private InputInterfaceButtonImpl mJumpButton;
    private InputInterfaceButtonImpl mAttackButton;

//////// SYSTEM_SPECIFIC - BEGIN

    private InputInterfaceDPadImpl mMovementPadButtons;
    private InputInterfaceSliderImpl mMovementPadSensor;

    private InputInterfaceButtonImpl mJumpButton1;
    private InputInterfaceButtonImpl mJumpButton2;

    private InputInterfaceButtonImpl mAttackButton1;
    private InputInterfaceButtonImpl mAttackButton2;

//////// SYSTEM_SPECIFIC - END

//////// GAME_SPECIFIC - END


    public InputGameInterfaceImpl() {
        super();

        final InputSystemImpl input = (InputSystemImpl)sSystemRegistry.inputSystem;
        assert input != null;

//////// SYSTEM_SPECIFIC - BEGIN

        mHud = (HudSystemImpl)sSystemRegistry.hudSystem;
        assert mHud != null;

        mSupportOrientation = true;
        mOrientationSensitivity = 1.0f;

        mSupportOnScreenControls = true;
        mMovementSensitivity = 1.0f;

//////// SYSTEM_SPECIFIC - END

//////// GAME_SPECIFIC - BEGIN

//////// SYSTEM_SPECIFIC - BEGIN

        final HudButtonSlider dpad = mHud.getDPad();
        mMovementPadSensor = new InputInterfaceSliderImpl(input, dpad);
//! mMovementPadSensor.useOrientation(true);

        final HudButton left = mHud.getLeftButton();
        final HudButton right = mHud.getRightButton();
//////// CLIMBING 20141024 - BEGIN
//        mMovementPadButtons = new InputInterfaceDPadPhone(input, left, right, null, null);
//////// CLIMBING 20141024 - MID
        final HudButton up = mHud.getUpButton();
        final HudButton down = mHud.getDownButton();
        mMovementPadButtons = new InputInterfaceDPadImpl(input, left, right, up, down);
//////// CLIMBING 20141024 - END

        final HudButton jump1 = mHud.getJumpButton1();
        mJumpButton1 = new InputInterfaceButtonImpl(input, jump1);

        final HudButton jump2 = mHud.getJumpButton2();
        mJumpButton2 = new InputInterfaceButtonImpl(input, jump2);

        final HudButton attack1 = mHud.getAttackButton1();
        mAttackButton1 = new InputInterfaceButtonImpl(input, attack1);

        final HudButton attack2 = mHud.getAttackButton2();
        mAttackButton2 = new InputInterfaceButtonImpl(input, attack2);

//////// SYSTEM_SPECIFIC - END

        mMovementPad = mMovementPadButtons;
        mJumpButton = mJumpButton1;
        mAttackButton = mAttackButton1;

//////// GAME_SPECIFIC - END

//////// SYSTEM_SPECIFIC - BEGIN

        switchLayout(HudSystemImpl.LAYOUT_DEFAULT);

//////// SYSTEM_SPECIFIC - END

        reset();
    }

    @Override
    public void reset() {

//////// GAME_SPECIFIC - BEGIN

//////// SYSTEM_SPECIFIC - BEGIN

        mMovementPadSensor.reset();
        mMovementPadButtons.reset();
        mJumpButton1.reset();
        mJumpButton2.reset();
        mAttackButton1.reset();
        mAttackButton2.reset();

//////// SYSTEM_SPECIFIC - END

// useless
//        mMovementPad.reset();
//        mJumpButton.reset();
//        mAttackButton.reset();

//////// GAME_SPECIFIC - END
    }

    @Override
    public void update(float timeDelta, BaseObject parent) {
        final float gameTime = sSystemRegistry.timeSystem.getGameTime();

//////// GAME_SPECIFIC - BEGIN

        mMovementPad.update(timeDelta, gameTime);
        mJumpButton.update(timeDelta, gameTime);
        mAttackButton.update(timeDelta, gameTime);

//////// GAME_SPECIFIC - END
    }

    @Override
    public void setMovementSensitivity(float sensitivity) {
        super.setMovementSensitivity(sensitivity);

//////// GAME_SPECIFIC - BEGIN

//////// SYSTEM_SPECIFIC - BEGIN

        mMovementPadSensor.setSensitivity(sensitivity);
        mMovementPadButtons.setSensitivity(sensitivity);

//////// SYSTEM_SPECIFIC - END

// useless
//        mMovementPad.setSensitivity(sensitivity);

//////// GAME_SPECIFIC - END
    }


//////// SYSTEM_SPECIFIC - BEGIN

    @Override
    public void setOrientationSensitivity(float sensitivity) {
        super.setOrientationSensitivity(sensitivity);

        if (mSupportOrientation) {
//////// GAME_SPECIFIC - BEGIN

            mMovementPadSensor.setOrientationSensitivity(sensitivity);

//////// GAME_SPECIFIC - END
        }
    }

    @Override
    public void useOnScreenControls(boolean use) {
        super.useOnScreenControls(use);

        if (mUseOnScreenControls) {
            switchLayout(HudSystemImpl.LAYOUT_BUTTONS);
        } else {
// !!!! TODO: make sure no endless loop is created by recursive calls !!!!
            useOrientation(true);
        }

        reset();
    }

    @Override
    public void useOrientation(boolean use) {
        super.useOrientation(use);

        if (mUseOrientation) {
            switchLayout(HudSystemImpl.LAYOUT_TILT);
        } else {
// !!!! TODO: make sure no endless loop is created by recursive calls !!!!
            useOnScreenControls(true);
        }

        reset();
    }

//////// SYSTEM_SPECIFIC - END


//////// SYSTEM_SPECIFIC - BEGIN

    public void switchLayout(int layout) {
        mLayout = layout;

        switch (mLayout) {
// !!!! TODO: add more layouts !!!!
// (flip buttons, etc.)
            case HudSystemImpl.LAYOUT_TILT:
                mUseOrientation = true;
                mUseOnScreenControls = false;

//////// GAME_SPECIFIC - BEGIN

                mMovementPad = mMovementPadSensor;
//                mCurrentLaunchButton = mLaunchButton1;
               mMovementPadSensor.useOrientation(true);

//////// GAME_SPECIFIC - END
                break;

            case HudSystemImpl.LAYOUT_BUTTONS:
                mUseOrientation = false;
                mUseOnScreenControls = true;

//////// GAME_SPECIFIC - BEGIN

                mMovementPad = mMovementPadButtons;
//                mCurrentLaunchButton = mLaunchButton1;
                mMovementPadSensor.useOrientation(false); // isn't it useless ?

//////// GAME_SPECIFIC - END
                break;

            default:
                mLayout = HudSystemImpl.LAYOUT_DEFAULT;
                switchLayout(mLayout);
                break;
        }

        mHud.switchLayout(mLayout);
    }

//////// SYSTEM_SPECIFIC - END


//////// GAME_SPECIFIC - BEGIN

    @Override
    public final InputXY getMovementPad() {
        return mMovementPad.getSlider();
    }

    public final InputButton getJumpButton() {
        return mJumpButton.getButton();
    }

    public final InputButton getAttackButton() {
        return mAttackButton.getButton();
    }

//////// GAME_SPECIFIC - END

}
