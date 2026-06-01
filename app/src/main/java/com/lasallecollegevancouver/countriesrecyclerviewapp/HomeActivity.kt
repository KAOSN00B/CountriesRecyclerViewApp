package com.lasallecollegevancouver.countriesrecyclerviewapp

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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

        // Add the divider lines between rows (skips the last item automatically)
        countriesRV.addItemDecoration(CountryDividerDecoration())

        selectedText = findViewById<TextView>(R.id.selected_country_text_id)


    }

    override fun onRowListener(index: Int) {
        val country = AppData.countries[index]
        selectedText.text = country


    }
}

/*
    ItemDecoration lets you draw custom visuals between RecyclerView rows
    without touching the row layouts themselves.

    onDraw() is called by the RecyclerView before it draws its children,
    so we can paint lines underneath each visible row.
*/
class CountryDividerDecoration : RecyclerView.ItemDecoration() {

    // Paint defines how the line looks — color and thickness
    private val linePaint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 2f
    }

    // Gap in pixels from the left and right edges of the RecyclerView
    private val horizontalGapPx = 40f

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter ?: return

        // Index of the last item — we won't draw a line under this one
        val lastItemIndex = adapter.itemCount - 1

        for (i in 0 until parent.childCount) {
            val childView = parent.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(childView)

            // Skip the last item so there's no line at the very bottom
            if (adapterPosition == lastItemIndex) continue

            // Draw the line just below the bottom edge of this row
            val lineY = childView.bottom.toFloat()
            val lineLeft = parent.left + horizontalGapPx
            val lineRight = parent.right - horizontalGapPx

            canvas.drawLine(lineLeft, lineY, lineRight, lineY, linePaint)
        }
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

