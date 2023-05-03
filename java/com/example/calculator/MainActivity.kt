package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getTextView(row : Int, col : Int): TextView {
        // e.g., idName is textView31
        val idName = if (col > 5) "textView${row}5" else "textView${row}${col}"
        // resources.getIdentifier will return corresponding number (e.g.,2131231192)
        val id = resources.getIdentifier(idName, "id", packageName)
//        println("idName is $idName and id is $id")    //for debugging
        return findViewById<TextView>(id)
    }

    // get letter button (e.g., buttonS, buttonQ)
    private fun getButton(letter : String): Button {
        // e.g., idName is buttonA, buttonB, buttonC, etc
        val idName = "button${letter.uppercase()}"
        // resources.getIdentifier will return corresponding number (e.g., 2131231192)
        val id = resources.getIdentifier(idName, "id", packageName)
        //println("idName is $idName and id is $id")    // for debugging
        return findViewById<Button>(id)
    }

    fun buttonHandler(view: View) {
        // if game is over, just return
        if (gameOver) return
        // when a user press a letter, show the letter to current textView
        getTextView(row, col).text = (view as Button).text.toString()

        println((view as Button).text.toString())     // for debugging
        // advance cursor to next textView
        if (col != 5){
            col ++
        }
    }

    fun backspaceHandler(view: View) {
        // if game is over, just return
        if (gameOver) return
        // if we went past the end, so clamp down
        if (col < 1) col = 1
        // Go back if we advanced
        if (col > 5) col = 5
        // Erase the text
        getTextView(row, col).text = ""
        if (col != 1) col--
    }

    fun enterHandler(view: View) {
        // No change to game state if the word is incomplete
        if (col != 5) {
            findViewById<TextView>(R.id.message).text = "Your guess is not finished"
            return
        }

        // grab text from textView and concatenate
        getGuess()

        // No change to game state if the word is not in dictionary
        if (!legitGuess()) {
            findViewById<TextView>(R.id.message).text = "Please use a legit word"
            return
        }

        // At this point, reveal the game state
        colorCode()

        // If we got here, the guessed word is in the dictionary
        // If it matches the word, the game is over
        if (guess == word){
            gameOver = true
            findViewById<TextView>(R.id.message).text = "Congratulations!"
            return
        }
        // If we're on the last row, the game is over
        if (row == 6){
            gameOver = true
            findViewById<TextView>(R.id.message).text = "Sorry, you did not guess the word :("
            return
        }
        row++
        col = 1
    }

    // grab text from textView and concatenate
    private fun getEquation() {
        guess = ""
        for (i in 1..5) {
            guess += getTextView(row, i).text
        }
        guess = guess.lowercase()
        println(guess)
    }

}