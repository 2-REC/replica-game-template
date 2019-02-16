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

import com.replica.engine.GameSpecific;
import com.replica.engine.BaseObject;
import android.content.Context;

/**
 * High-level setup object for the AndouKun game engine.
 * This class sets up the core game engine objects and threads.  It also passes events to the
 * game thread from the main UI thread.
 */
public abstract class GameImpl extends GameSpecific {
    private InputGameInterfaceImpl mInputGameInterfaceImpl;
    private GameObjectFactoryImpl mGameObjectFactoryImpl;
    private HudSystemImpl mHudSystemImpl;
    private EventRecorderImpl mEventRecorderImpl;

    public GameImpl() {
        super();

        mInputGameInterfaceImpl = null;
        mGameObjectFactoryImpl = null;
        mHudSystemImpl = null;
        mEventRecorderImpl = null;
    }

    @Override
    protected InputGameInterfaceImpl getInputGameInterface() {
        if (mInputGameInterfaceImpl == null) {
    	    mInputGameInterfaceImpl = new InputGameInterfaceImpl();
        }
        return mInputGameInterfaceImpl;
    }

    @Override
    protected GameObjectFactoryImpl getGameObjectFactory() {
        if (mGameObjectFactoryImpl == null) {
        	mGameObjectFactoryImpl = new GameObjectFactoryImpl();
        }
        return mGameObjectFactoryImpl;
    }

    @Override
    protected HudSystemImpl getHudSystem(Context context) {
        if (mHudSystemImpl == null) {
        	mHudSystemImpl = new HudSystemImpl();
        }
        return mHudSystemImpl;
    }

    @Override
    protected EventRecorderImpl getEventRecorder() {
        if (mEventRecorderImpl == null) {
            mEventRecorderImpl = new EventRecorderImpl(new DifficultyConstantsSpecific());
        }
        return mEventRecorderImpl;
    }


    @Override
    public int getNbLives() {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        return evtRec.getNbLives();
    }

    public void setNbLives(int nbLives) {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        evtRec.setNbLives(nbLives);
    }

//////// CONTINUE 20140411 - MID
    public int getNbContinues() {
        return BaseObject.sSystemRegistry.eventRecorder.getDifficultyConstants().getPlayerNbContinues();
    }
//////// CONTINUE 20140411 - END


//////// GAME_SPECIFIC - BEGIN

/*
    public int getNbBalls() {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        return evtRec.getNbBallsLeft();
    }

    public void setNbBalls(int nbBalls) {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        evtRec.setNbBallsLeft(nbBalls);
    }
*/

/*
    public int getNbLivesLost() {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        return evtRec.getNbLivesLost();
    }

    public int getNbLivesWon() {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        return evtRec.getNbLivesWon();
    }
*/

/*
?    public int getNbEnemiesTotal() {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        return evtRec.getNbEnemiesTotal();
    }
*/

    public int getNbEnemiesKilled() {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        return evtRec.getNbEnemiesKilled();
    }

    public int getNbCoinsTotal() {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        return evtRec.getNbCoinsTotal();
    }

    public int getNbCoinsCollected() {
        final EventRecorderImpl evtRec = (EventRecorderImpl)BaseObject.sSystemRegistry.eventRecorder;
        return evtRec.getNbCoinsCollected();
    }

//////// GAME_SPECIFIC - END

}
