package com.example.csc475ct5

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

class ImageBoxAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<ImageBoxAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        //val textView: TextView
        var image: Bitmap? = null

        init {
            // Define click listener for the ViewHolder's View
            imageView = view.findViewById(R.id.imageView)
            //textView = view.findViewById(R.id.tvDogImageURL)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_box_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute{
            try{
                viewHolder.image = BitmapFactory.decodeStream(java.net.URL(dataSet[position].substring(1,dataSet[position].length-1)).openStream())

                handler.post{
                    viewHolder.imageView.setImageBitmap(viewHolder.image)
                }
            }
            catch (e: Exception){ e.printStackTrace()}

        }

        //viewHolder.textView.text = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}