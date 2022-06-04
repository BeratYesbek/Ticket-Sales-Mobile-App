package com.example.ticketmobileapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import androidx.activity.viewModels
import com.example.ticketmobileapp.R
import com.example.ticketmobileapp.adapters.gridViewAdapter.SeatGridViewAdapter
import com.example.ticketmobileapp.databinding.ActivitySeatSelectionBinding
import com.example.ticketmobileapp.modals.Seat
import com.example.ticketmobileapp.mvvm.SeatViewModel
import com.example.ticketmobileapp.utilities.OnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatSelectionActivity : AppCompatActivity() , OnClickListener<Seat>{

    private lateinit var binding: ActivitySeatSelectionBinding
    private lateinit var adapter : SeatGridViewAdapter
    private val viewModel : SeatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val salonID  = intent.getStringExtra("salonID")
        getData(salonID?.toInt())
    }
    private fun getData(salonID : Number?){

        viewModel.getSeatsBySalonID(salonID)
        viewModel.seatsLiveData.observe(this){
            runGridView(it)
        }
    }
    private fun runGridView(seats : List<Seat>) {
        adapter = SeatGridViewAdapter(seats,this,this)
        binding.gridView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onClickListener(data: Seat) {
        val intent =  Intent(this,PurchaseActivity::class.java)
        intent.putExtra("SeatID",data.id)
        startActivity(intent)
    }


}