package com.example.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PlayActivity : AppCompatActivity(), GameOverDialogFragment.GameOverDialogListener {

    val btns = arrayOf(R.id.btn_11, R.id.btn_12, R.id.btn_13, R.id.btn_21, R.id.btn_22, R.id.btn_23,
        R.id.btn_31, R.id.btn_32, R.id.btn_33)
    lateinit var textLabel : TextView
    var playerTurn : String = "A"
    val board = Array(3) { CharArray(3) { ' ' } }
    lateinit var resetBtn : Button

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_play)

        textLabel = findViewById(R.id.turnLabel)
        textLabel.text = "Player $playerTurn's Turn"
        resetBtn = findViewById(R.id.resetBtn)

        for (btn in btns) {
            val clickBtn = findViewById<Button>(btn)
            clickBtn.setOnClickListener {
                makeTurn(clickBtn)
            }
        }

        resetBtn.setOnClickListener {
            resetGame()
        }
    }

    @SuppressLint("SetTextI18n")
    fun makeTurn(btn: Button) {
        val row = btns.indexOf(btn.id) / 3
        val col = btns.indexOf(btn.id) % 3

        if (board[row][col] == ' ') {
            if (playerTurn == "A") {
                board[row][col] = 'X'
                btn.foreground = ContextCompat.getDrawable(this, R.drawable.x_img)
                if (checkWin('X')) {
                    disableAllButtons()
                    return
                }
            } else {
                board[row][col] = 'O'
                btn.foreground = ContextCompat.getDrawable(this, R.drawable.o_img)
                if (checkWin('O')) {
                    disableAllButtons()
                    return
                }
            }

            if (checkDraw()) {
                disableAllButtons()
                return
            }

            playerTurn = if (playerTurn == "A") "B" else "A"
            textLabel.text = "Player $playerTurn's Turn"
        }
    }

    private fun checkWin(player: Char): Boolean {
        var cond : Boolean = false
        // Check rows
        for (i in 0..2) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) cond = true
        }
        // Check columns
        for (i in 0..2) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) cond = true
        }
        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) cond = true
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) cond = true

        if(cond) {
            val winnerMessage = if (player == 'X') "Player A Wins!" else "Player B Wins!"
            showGameOverPopup(winnerMessage)
            return true
        }
        return false
    }

    private fun checkDraw(): Boolean {
        val isDraw = board.all { row -> row.all { it != ' ' } }
        if (isDraw) {
            showGameOverPopup("It's a Draw!")
        }
        return isDraw
    }

    private fun disableAllButtons() {
        for (btnId in btns) {
            findViewById<Button>(btnId).isEnabled = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun resetGame() {
        // Reset the board
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = ' '
            }
        }
        // Reset UI elements
        for (btnId in btns) {
            findViewById<Button>(btnId).apply {
                foreground = null
                isEnabled = true
            }
        }
        // Reset player turn
        playerTurn = "A"
        textLabel.text = "Player $playerTurn's Turn"
    }

    private fun showGameOverPopup(message: String) {
        val dialogFragment = GameOverDialogFragment.newInstance(message)
        dialogFragment.setGameOverDialogListener(this)
        dialogFragment.show(supportFragmentManager, "gameOverDialog")
    }

    override fun onPlayAgain() {
        resetGame()
    }

    override fun onExit() {
        finish()
    }
}