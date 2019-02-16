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
import com.replica.engine.DebugLog;
import com.replica.engine.MainSpecific;
import android.content.Intent;
import android.content.SharedPreferences;

// !!!! TODO: clean ... !!!!
// !!!! TODO: move specific stuff to derived classes & make this one abstract !!!!
// (as for GameImpl)

public class MainImpl extends MainSpecific {
    private int mNbLives;

//////// GAME_SPECIFIC - BEGIN

    private int mCoinsTotal;
    private int mCoinsCollected;

//?    private int mEnemiesTotal;
    private int mEnemiesKilled;

//////// GAME_SPECIFIC - END

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
*/

/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
*/

/*
    @Override
    protected void onPause() {
        super.onPause();
    }
*/

/*
    @Override
    protected void onResume() {
        super.onResume();
    }
*/

//////// touch - b
// !!!! ???? TODO: can be used for touchpad events ? ???? !!!!
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
*/
//////// touch - e

/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = true;
        return result;
    }
*/

/*
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean result = false;
        return result;
    }
*/

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if ...
            ...
        else
            super.onActivityResult(requestCode, resultCode, intent);
    }
*/

    @Override
    public void onGameFlowEvent(int eventCode, int index) {
        switch (eventCode) {
//////// GAME_SPECIFIC - BEGIN
/*
            case GameFlowEvent.EVENT_SHOW_DIARY:
            {
                Intent i = new Intent(this, DiaryActivity.class);
                LevelTree.Level level = LevelTree.get(mLevelRow, mLevelIndex);
                level.diaryCollected = true;
                i.putExtra("text", level.dialogResources.diaryEntry);
                startActivity(i);
                if (UIConstants.mOverridePendingTransition != null) {
                    try {
                        UIConstants.mOverridePendingTransition.invoke(AndouKun.this,
                                R.anim.activity_fade_in, R.anim.activity_fade_out);
                    } catch (InvocationTargetException ite) {
                        DebugLog.d("Activity Transition", "Invocation Target Exception");
                    } catch (IllegalAccessException ie) {
                        DebugLog.d("Activity Transition", "Illegal Access Exception");
                    }
                }
                break;
            }
*/
//////// GAME_SPECIFIC - END

            default:
                super.onGameFlowEvent(eventCode, index);
                break;
        }
    }

/*
    @Override
    protected void saveGame() {
        super.saveGame();
    }
*/

// !!!! ???? TODO: return type GameImpl or GameImplSpecific ? ???? !!!!
// => & check impact when change!
    @Override
    protected GameImpl getGame() {
        return new GameImplSpecific();
    }


    @Override
    protected void setViewSize() {
//////// GAME_SPECIFIC - BEGIN

        mViewWidth = 800;
        mViewHeight = 600;

//////// GAME_SPECIFIC - END
    }

    @Override
    protected boolean keepScreenRatio() {
//////// GAME_SPECIFIC - BEGIN

        return false;

//////// GAME_SPECIFIC - END
    }


    @Override
    protected void loadSpecificPrefs(SharedPreferences prefs) {
        mNbLives = prefs.getInt(PreferenceConstantsSpecific.PREFERENCE_NB_LIVES, -2); // -2 => unset value

//////// GAME_SPECIFIC - BEGIN

        mCoinsTotal = prefs.getInt(PreferenceConstantsSpecific.PREFERENCE_COINS_TOTAL, 0);
        mCoinsCollected = prefs.getInt(PreferenceConstantsSpecific.PREFERENCE_COINS_COLLECTED, 0);

//?        mEnemiesTotal = prefs.getInt(PreferenceConstantsSpecific.PREFERENCE_ENEMIES_TOTAL, 0);
        mEnemiesKilled = prefs.getInt(PreferenceConstantsSpecific.PREFERENCE_ENEMIES_KILLED, 0);

//////// GAME_SPECIFIC - END
    }

/*
// !!!! ???? TODO: could be removed ? ???? !!!!
// => If implemented, have problems with derived classes ...
    @Override
    protected void loadSpecificPrefsCtrl(SharedPreferences prefs) {
//////// SYSTEM_SPECIFIC - BEGIN

// !!!! TODO: add specifics controls settings !!!!
// !!!! TODO: move to engine !!!!
/*
         final boolean flipControls = prefs.getBoolean(PreferenceConstantsSpecific.PREFERENCE_FLIPCONTROLS, false);
         ((GameImpl) mGame).flipControls(flipControls);
*/
/*
//////// SYSTEM_SPECIFIC - END
    }
*/

    @Override
    protected void saveSpecificPrefs() {
        if (mPrefsEditor != null) {
            mPrefsEditor.putInt(PreferenceConstantsSpecific.PREFERENCE_NB_LIVES, mNbLives);

//////// GAME_SPECIFIC - BEGIN

            mPrefsEditor.putInt(PreferenceConstantsSpecific.PREFERENCE_COINS_TOTAL, mCoinsTotal);
            mPrefsEditor.putInt(PreferenceConstantsSpecific.PREFERENCE_COINS_COLLECTED, mCoinsCollected);

//?            mPrefsEditor.putInt(PreferenceConstantsSpecific.PREFERENCE_ENEMIES_TOTAL, mEnemiesTotal);
            mPrefsEditor.putInt(PreferenceConstantsSpecific.PREFERENCE_ENEMIES_KILLED, mEnemiesKilled);

//////// GAME_SPECIFIC - END
        }
    }

    @Override
    protected void initSpecifics() {
        final EventRecorderImpl evtRec = (EventRecorderImpl) BaseObject.sSystemRegistry.eventRecorder;

        if (mNbLives == -2) {
            mNbLives = evtRec.getDifficultyConstants().getPlayerNbLives();
        }
        ((GameImpl) mGame).setNbLives(mNbLives);

//////// GAME_SPECIFIC - BEGIN
// !!!! ???? TODO: shouldn't be initialised here ? ???? !!!!
/*
//?        mEnemiesTotal = 0;
        mEnemiesKilled = 0;
        mCoinsTotal = 0;
        mCoinsCollected = 0;
*/
//////// GAME_SPECIFIC - END
    }

    @Override
    protected void updateSpecifics() {
        final GameImpl game = (GameImpl) mGame;

        mNbLives = game.getNbLives();

// !!!! TODO: can add time spent in level with "game.getGameTime()" !!!!
//...

//////// GAME_SPECIFIC - BEGIN

        mCoinsTotal += game.getNbCoinsTotal();
        mCoinsCollected += game.getNbCoinsCollected();

//?        mEnemiesTotal += game.getNbEnemiesTotal();
        mEnemiesKilled += game.getNbEnemiesKilled();

//////// GAME_SPECIFIC - END
    }

    @Override
    protected void clearSpecificPrefs() {
        if (mPrefsEditor != null) {
            mPrefsEditor.remove(PreferenceConstantsSpecific.PREFERENCE_NB_LIVES);

//////// GAME_SPECIFIC - BEGIN

            mPrefsEditor.remove(PreferenceConstantsSpecific.PREFERENCE_COINS_TOTAL);
            mPrefsEditor.remove(PreferenceConstantsSpecific.PREFERENCE_COINS_COLLECTED);

//?            mPrefsEditor.remove(PreferenceConstantsSpecific.PREFERENCE_ENEMIES_TOTAL);
            mPrefsEditor.remove(PreferenceConstantsSpecific.PREFERENCE_ENEMIES_KILLED);

//////// GAME_SPECIFIC - END
        }
    }

//////// lev res - m
    @Override
    protected void addLevelResultsData(Intent intent) {
        GameImpl game = (GameImpl) mGame;
//?        intent.putExtra("nb_lives", game.getNbLives());

//////// GAME_SPECIFIC - BEGIN

////?        intent.putExtra("enemies_total", game.getNbEnemiesTotal());
//        intent.putExtra("enemies_killed", game.getNbEnemiesKilled());
        intent.putExtra("coins_total", game.getNbCoinsTotal());
        intent.putExtra("coins_collected", game.getNbCoinsCollected());

//////// GAME_SPECIFIC - END
    }
//////// lev res - e

    @Override
    protected void addGameOverData(Intent intent) {
//?        intent.putExtra("nb_lives", mNbLives());

//////// GAME_SPECIFIC - BEGIN

////?        intent.putExtra("enemies_total", mEnemiesTotal);
//        intent.putExtra("enemies_killed", mEnemiesKilled);
        intent.putExtra("coins_total", mCoinsTotal);
        intent.putExtra("coins_collected", mCoinsCollected);

//////// GAME_SPECIFIC - END
    }

}
