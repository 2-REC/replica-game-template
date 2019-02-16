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

/*
- points, kills, coins, etc.
- last death position, etc.

Example with Coins:
- Game.getCoins()
	=> called in Main Activity ( MyGame )

- GameObjectFactory.spawnCoin(...)
	LifetimeComponent life = ( LifetimeComponent )allocateComponent( LifetimeComponent.class );
	life.setIncrementEventCounter( EventRecorder.COUNTER_PEARLS_COLLECTED );
	=> in LifetimeComponent.die() :
		if ( mIncrementEventCounter )
		{
			EventRecorder recorder = sSystemRegistry.eventRecorder;
			recorder.incrementEventCounter( mEventCounter );
		}

	EventRecorder recorder = sSystemRegistry.eventRecorder;
	recorder.incrementEventCounter( EventRecorder.COUNTER_PEARLS_TOTAL );
*/

/*
TODO:
Could have an array of "counters" ( and others ? ) defined in "mother" class,
 which size is given as parameter to constructor (or init function) ;
 and in the derived class (here), a correspondence made (eg: COUNTER_PEARLS_COLLECTED = 1).
Then, in "game" could call "getCounter( i )" to get the desired counter value.
*/

public class EventRecorderImpl extends EventRecorder {

//// GAME - BEGIN
    public final static int COUNTER_LIVES = 0;
//    public final static int COUNTER_LIVES_LOST = 0;
//    public final static int COUNTER_LIVES_WON = 1;

//////// PEDRO 20140821 - BEGIN
    public final static int COUNTER_COINS_TOTAL = 4;
    public final static int COUNTER_COINS_COLLECTED = 5;

//    public final static int COUNTER_ENEMIES_TOTAL = 6;
    public final static int COUNTER_ENEMIES_KILLED = 7;

//////// SPEEDUP - BEGIN
    public final static int COUNTER_SPEEDUP = 8;
//////// SPEEDUP - END

//////// PEDRO 20140821 - END

//    public final static int NB_COUNTERS = 9;

//// GAME - END


//// GAME - BEGIN

    private int mNbLives = 0;
//    private int mNbLivesLost = 0;
//    private int mNbLivesWon = 0;

//////// PEDRO 20140821 - BEGIN
    private int mNbCoinsTotal = 0;
    private int mNbCoinsCollected = 0;

//    private int mNbEnemiesTotal = 0;
    private int mNbEnemiesKilled = 0;

//////// SPEEDUP - BEGIN
    private int mNbSpeedUp = 0;
//////// SPEEDUP - END

//////// PEDRO 20140821 - END

//// GAME - END

    public EventRecorderImpl(DifficultyConstantsSpecific difficultyConstants) {
        super(difficultyConstants);
    }

    @Override
    public void reset() {
        super.reset();

//// GAME - BEGIN

//        mNbLivesLost = 0;
//        mNbLivesWon = 0;

//////// PEDRO 20140821 - BEGIN
        mNbCoinsTotal = 0;
        mNbCoinsCollected = 0;

//        mNbEnemiesTotal = 0;
        mNbEnemiesKilled = 0;

//////// SPEEDUP - BEGIN
        mNbSpeedUp = 0;
//////// SPEEDUP - END

//////// PEDRO 20140821 - END

//// GAME - END
    }

    @Override
    public synchronized void incrementEventCounter(int event, int value) {

//// GAME - BEGIN

        if (event == COUNTER_LIVES) {
            mNbLives += value;
/*
        if (event == COUNTER_LIVES_LOST) {
            mNbLivesLost += value;
        } else if (event == COUNTER_LIVES_WON) {
            mNbLivesWon += value;
*/
//////// PEDRO 20140821 - BEGIN
        } else if (event == COUNTER_COINS_TOTAL) {
            mNbCoinsTotal += value;
        } else if (event == COUNTER_COINS_COLLECTED) {
            mNbCoinsCollected += value;
/*
        } else if (event == COUNTER_ENEMIES_TOTAL) {
            mNbEnemiesTotal += value;
*/
        } else if (event == COUNTER_ENEMIES_KILLED) {
            mNbEnemiesKilled += value;
//////// SPEEDUP - BEGIN
        } else if (event == COUNTER_SPEEDUP) {
            mNbSpeedUp += value;
//////// SPEEDUP - END
        }

//////// PEDRO 20140821 - END

//// GAME - END

    }

    public synchronized void decrementEventCounter(int event, int value) {

//// GAME - BEGIN

        if (event == COUNTER_LIVES) {
            if (mNbLives > 0) {
                mNbLives -= value;
            }
        }
/*
        if (event == COUNTER_LIVES_LOST) {
            mNbLivesLost -= value;
        } else if (event == COUNTER_LIVES_WON) {
            mNbLivesWon -= value;
        }
*/
//////// PEDRO 20140821 - BEGIN
/*
        else if (event == COUNTER_COINS_TOTAL) {
            mNbCoinsTotal -= value;
        } else if (event == COUNTER_COINS_COLLECTED) {
            mNbCoinsCollected -= value;
        } else if (event == COUNTER_ENEMIES_TOTAL) {
            mNbEnemiesTotal -= value;
        } else if (event == COUNTER_ENEMIES_KILLED) {
            mNbEnemiesKilled -= value;
        }
*/
/*
        else if (event == COUNTER_SPEEDUP) {
            mNbSpeedUp -= value;
        }
*/
//////// PEDRO 20140821 - END

//// GAME - END

    }

//// GAME - BEGIN
    synchronized int getNbLives() {
        return mNbLives;
    }
/*
    synchronized int getNbLivesLost() {
        return mNbLivesLost;
    }

    synchronized int getNbLivesWon() {
        return mNbLivesWon;
    }
*/

//////// PEDRO 20140821 - BEGIN
    synchronized int getNbCoinsTotal() {
        return mNbCoinsTotal;
    }

    synchronized int getNbCoinsCollected() {
        return mNbCoinsCollected;
    }
/*
    synchronized int getNbEnemiesTotal() {
        return mNbEnemiesTotal;
    }
*/
    synchronized int getNbEnemiesKilled() {
        return mNbEnemiesKilled;
    }

//////// SPEEDUP - BEGIN
    synchronized int getNbSpeedUp() {
        return mNbSpeedUp;
    }
//////// SPEEDUP - END

//////// PEDRO 20140821 - END

    synchronized public void setNbLives(int nbLives) {
        mNbLives = nbLives;
    }

/*
    synchronized public void setNbBallsLeft(int nbBalls) {
        mNbBallsLeft = nbBalls;
    }
*/

//// GAME - END

}
