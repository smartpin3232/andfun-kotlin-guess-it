/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.util.*

class GameViewModel: ViewModel(){

    companion object{
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 10000L
    }

    private val timer: CountDownTimer

    private val _time = MutableLiveData<String>()
    val time :LiveData<String>
        get() = _time

    var word = ""
    var score = MutableLiveData<Int>()

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish : LiveData<Boolean>
        get() = _eventGameFinish

    private lateinit var wordList: MutableList<String>

    init {
        _eventGameFinish.value = false
        Timber.i("GameViewModel created!")
        resetList()
        score.value = 0


        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onFinish() {
                _eventGameFinish.value = true
            }

            override fun onTick(p0: Long) {

                _time.value = DateUtils.formatElapsedTime(p0/1000)
            }

        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("GameViewModel destroyed!")
        timer.cancel()
    }

    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
           resetList()
        }
        word = wordList.removeAt(0)

    }

    fun onSkip() {
        score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        score.value = (score.value)?.plus(1)
        nextWord()
    }
    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }
}