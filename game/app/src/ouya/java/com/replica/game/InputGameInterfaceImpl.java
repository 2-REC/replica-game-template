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

import com.replica.engine.InputGameInterfaceOuya;
import com.replica.engine.InputButton;

/**
 * Specific input implementation for OUYA.
 *
 * ! - CAUTION:
 * The buttons defined here have to be the same as defined "xml/buttons" (& defined in same order!).
 */
public class InputGameInterfaceImpl extends InputGameInterfaceOuya {

//////// GAME_SPECIFIC - BEGIN

    private static final int JUMP_BUTTON = 0;
    private static final int ATTACK_BUTTON = 1;

    public final InputButton getJumpButton() {
        return getButton(JUMP_BUTTON);
    }

    public final InputButton getAttackButton() {
        return getButton(ATTACK_BUTTON);
    }

//////// GAME_SPECIFIC - END

}
