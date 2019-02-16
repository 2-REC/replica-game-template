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
import com.replica.engine.DifficultyConstants;

/*
TODO :
- adapt to game
- see what we want/need
- wouldn't be better to have public constants ?
*/

public class DifficultyConstantsSpecific extends DifficultyConstants {

//////// GAME - BEGIN

//////// PEDRO 20140821 - BEGIN

// !!!! TODO: add chilli bonus handling !!!!
//    protected float PICKUP_SPEEDUP_DURATION;

// !!!! TODO: add tequila bonus handling !!!!
//...
//////// PEDRO 20140821 - END

    public DifficultyConstantsSpecific() {
        super();

// !!!! TODO: get values from XML files depending on "difficulty" !!!!
// => though not easy as need to keep XML and code synchronised ...
        final int difficulty = BaseObject.sSystemRegistry.contextParameters.difficulty;
        switch (difficulty) {
            case 0:
//////// PEDRO 20140821 - BEGIN
//////// CONTINUE 20140411 - MID
                PLAYER_NB_CONTINUES = 3;
//////// CONTINUE 20140411 - END
                PLAYER_NB_LIVES = 5;
                PLAYER_MAX_LIFE = 5;

//                PICKUP_SPEEDUP_DURATION = 20.0f;

//?                MAX_ENNEMY_SPEED = ?;

// !!!! ???? TODO : handle DDA stuff ? ??? !!!!
/*
                // DDA boosts
                DDA_STAGE_1_ATTEMPTS = 3;
                DDA_STAGE_2_ATTEMPTS = 5;
                DDA_STAGE_1_LIFE_BOOST = 1;
                DDA_STAGE_2_LIFE_BOOST = 3;
                ...
*/
//////// PEDRO 20140821 - END
                break;
            case 1:
//////// PEDRO 20140821 - BEGIN
//////// CONTINUE 20140411 - MID
                PLAYER_NB_CONTINUES = 1;
//////// CONTINUE 20140411 - END
                PLAYER_NB_LIVES = 3;
                PLAYER_MAX_LIFE = 3;

//                PICKUP_SPEEDUP_DURATION = 10.0f;

//?                MAX_ENNEMY_SPEED = ?;

// !!!! ???? TODO : handle DDA stuff ? ??? !!!!
/*
                // DDA boosts
                DDA_STAGE_1_ATTEMPTS = 3;
                DDA_STAGE_2_ATTEMPTS = 8;
                DDA_STAGE_1_LIFE_BOOST = 1;
                DDA_STAGE_2_LIFE_BOOST = 2;
                ...
*/
//////// PEDRO 20140821 - END
                break;
            case 2:
//////// PEDRO 20140821 - BEGIN
//////// CONTINUE 20140411 - MID
                PLAYER_NB_CONTINUES = 0;
//////// CONTINUE 20140411 - END
                PLAYER_NB_LIVES = 1;
                PLAYER_MAX_LIFE = 2;

//                PICKUP_SPEEDUP_DURATION = 5.0f;

//?                MAX_ENNEMY_SPEED = ?;

// !!!! ???? TODO : handle DDA stuff ? ??? !!!!
/*
                // DDA boosts
                DDA_STAGE_1_ATTEMPTS = 4;
                DDA_STAGE_2_ATTEMPTS = 8;
                DDA_STAGE_1_LIFE_BOOST = 1;
                DDA_STAGE_2_LIFE_BOOST = 2;
                ...
*/
//////// PEDRO 20140821 - END
                break;
        }
    }

//////// PEDRO 20140821 - BEGIN
/*
    public int getNbChillis() {
        return NB_CHILLIS;
    }
*/

/*
    public float getSpeedUpDuration() {
        return PICKUP_SPEEDUP_DURATION;
    }
*/


/*
?    @Override
    public int getMaxEnnemySpeed() {
        return MAX_ENNEMY_SPEED;
    }
*/

// !!!! ???? TODO : handle DDA stuff ? ??? !!!!
/*
@Override
public int getDDAStage1Attempts() {
return DDA_STAGE_1_ATTEMPTS;
}

@Override
public int getDDAStage2Attempts() {
return DDA_STAGE_2_ATTEMPTS;
}

@Override
public int getDDAStage1LifeBoost() {
return DDA_STAGE_1_LIFE_BOOST;
}

@Override
public int getDDAStage2LifeBoost() {
return DDA_STAGE_2_LIFE_BOOST;
}

...
*/
//////// PEDRO 20140821 - END

//////// GAME - END

}
