package com.example.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class GameOverDialogFragment : DialogFragment() {

    interface GameOverDialogListener {
        fun onPlayAgain()
        fun onExit()
    }

    private var listener: GameOverDialogListener? = null

    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String): GameOverDialogFragment {
            val fragment = GameOverDialogFragment()
            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.custom_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val message = arguments?.getString(ARG_MESSAGE) ?: "Game Over"
        view.findViewById<TextView>(R.id.tvGameOverMessage).text = message

        view.findViewById<Button>(R.id.btnPlayAgain).setOnClickListener {
            listener?.onPlayAgain()
            dismiss()
        }

        view.findViewById<Button>(R.id.btnExit).setOnClickListener {
            listener?.onExit()
            dismiss()
        }
    }

    fun setGameOverDialogListener(listener: GameOverDialogListener) {
        this.listener = listener
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}