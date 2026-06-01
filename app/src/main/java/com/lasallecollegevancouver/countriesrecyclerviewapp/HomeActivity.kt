package com.lasallecollegevancouver.countriesrecyclerviewapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface RowListener
{
    fun onRowListener(index: Int)
}
class HomeActivity : AppCompatActivity(), RowListener
{
    lateinit var selectedText: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_layout)



        val countriesRV = findViewById<RecyclerView>(R.id.countries_rv_id)
        countriesRV.layoutManager = LinearLayoutManager(this)
        countriesRV.adapter = CountryAdapter(this)
        selectedText = findViewById<TextView>(R.id.selected_country_text_id)


    }

    override fun onRowListener(index: Int) {
        val country = AppData.countries[index]
        selectedText.text = country


    }
}

class CountryViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

class CountryAdapter(val listener: RowListener) : RecyclerView.Adapter<CountryViewHolder>()
{
    override fun getItemCount(): Int = AppData.countries.count() // this is like return inside {}

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): CountryViewHolder {
        val getCountryTextView = LayoutInflater.from(container.context)
                                 .inflate(R.layout.country_row,
                                  container, false) as TextView

        return CountryViewHolder(getCountryTextView)
    }

    override fun onBindViewHolder(viewHolder: CountryViewHolder, index: Int) {
        val country = AppData.countries[index]
        viewHolder.textView.text = country

        viewHolder.itemView.setOnClickListener {
          listener.onRowListener(index)
        }
    }
}

