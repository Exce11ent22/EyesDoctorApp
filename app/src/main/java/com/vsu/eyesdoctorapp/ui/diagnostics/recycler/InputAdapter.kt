package com.vsu.eyesdoctorapp.ui.diagnostics.recycler

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.vsu.eyesdoctorapp.R
import com.vsu.eyesdoctorapp.service.diagnostics.ResultCalculator
import java.io.IOException

class InputAdapter(
    private val context: Context,
    private val audioPaths: ArrayList<String>,
    private val answers: ResultCalculator.Answers,
    private val letters: Array<String>
) : RecyclerView.Adapter<InputAdapter.InputViewHolder>() {

    private var player: MediaPlayer? = null

    class InputViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val btnPlay: Button = view.findViewById(R.id.btn_play_letter)
        val spinner: Spinner = view.findViewById(R.id.spn_choose_letter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return InputViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return audioPaths.size
    }

    override fun onBindViewHolder(holder: InputViewHolder, position: Int) {
        ArrayAdapter.createFromResource(
            context,
            R.array.letters,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.spinner.adapter = adapter
        }
        holder.btnPlay.text = "Буква №${position + 1}"

        // воспроизвести звук произнесенной буквы
        holder.btnPlay.setOnClickListener {
            if (player != null) {
                player!!.stop()
                player!!.release()
                player = null
            }
            player = MediaPlayer().apply {
                try {
                    setDataSource(audioPaths[position])
                    prepare()
                    start()
                } catch (e: IOException) {
                    Log.e("AUDIO", "prepare() failed")
                }
            }
        }

        // установить значение на основе ответов
        holder.spinner.setSelection(letters.indexOf(answers.answers[holder.adapterPosition]))

        // установить значение ответа
        holder.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                answers.answers[holder.adapterPosition] = holder.spinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
}