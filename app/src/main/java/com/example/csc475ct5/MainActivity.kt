package com.example.csc475ct5

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val getDogButton : Button = this.findViewById(R.id.btnGetDogs)
        getDogButton.setOnClickListener{ getDogs() }

        getDogs()

    }

    private fun getDogs(){

            // getting a new volley request
            // queue for making new requests
            val volleyQueue = Volley.newRequestQueue(this)

            // url of the api through which we get random dog images
            val url = "https://dog.ceo/api/breed/mix/images/random/10"

            // since the response we get from the api is in JSON,
            // we need to use `JsonObjectRequest` for
            // parsing the request response
            val jsonObjectRequest = JsonObjectRequest(
                // we are using GET HTTP request method
                Request.Method.GET,
                // url we want to send the HTTP request to
                url,
                // this parameter is used to send a JSON object
                // to the server, since this is not required in
                // our case, we are keeping it `null`
                null,

                // lambda function for handling the case
                // when the HTTP request succeeds
                { response ->
                    // get the image url from the JSON object
                    val dogImageUrls = response.get("message")

                    //Log.i(TAG, dogImageUrls.size.toString())
                    val dataset = dogImageUrls.toString().substring(1,dogImageUrls.toString().length-1).replace("\\","").split(",")
                    val imageBoxAdapter = ImageBoxAdapter(dataset)
                    val recyclerView : RecyclerView = findViewById(R.id.rvDogImages)
                    recyclerView.layoutManager = StaggeredGridLayoutManager(1,1)
                    recyclerView.adapter = imageBoxAdapter
                },

                // lambda function for handling the
                // case when the HTTP request fails
                { error ->
                    // make a Toast telling the user
                    // that something went wrong
                    Toast.makeText(this, "Some error occurred! Cannot fetch dog image", Toast.LENGTH_LONG).show()
                    // log the error message in the error stream
                    Log.e("MainActivity", "loadDogImage error: ${error.localizedMessage}")
                }
            )

            // add the json request object created
            // above to the Volley request queue
            volleyQueue.add(jsonObjectRequest)

    }
}