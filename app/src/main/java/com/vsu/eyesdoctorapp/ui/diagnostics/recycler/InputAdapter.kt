package com.vsu.eyesdoctorapp.ui.diagnostics.recycler

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class InputAdapter(
    private val context: Context,
    private val audio_paths: List<String>
) : RecyclerView.Adapter<InputAdapter.InputViewHolder>() {

    class InputViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: InputViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}