package com.example.foodorderanddeliveryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderanddeliveryapp.adapter.RestaurantListAdapter
import com.example.foodorderanddeliveryapp.adapter.RestaurantListAdapter.RestaurantListClickListener
import com.example.foodorderanddeliveryapp.models.RestaurantModel
import com.google.gson.Gson
import java.io.*
import android.content.Intent

class MainActivity : AppCompatActivity() , RestaurantListClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar:ActionBar? = supportActionBar
        actionBar?.setTitle("Restaurant List")

        val restaurantModel = getRestaurantData()
        initRecyclerView(restaurantModel)
    }
private fun initRecyclerView(restaurantList:List<RestaurantModel>?){
    val recyclerViewRestaurant = findViewById<RecyclerView>(R.id.recyclerViewRestaurant)
    recyclerViewRestaurant.layoutManager = LinearLayoutManager(this)
    val adapter = RestaurantListAdapter(restaurantList,this)
    recyclerViewRestaurant.adapter = adapter
}
    private fun getRestaurantData(): List<RestaurantModel>? {
        val inputStream: InputStream = resources.openRawResource(R.raw.restaurant)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }

        } catch (e: Exception) {}
            val jsonStr: String = writer.toString()
            val gson = Gson()
            val restaurentModel = gson.fromJson<Array<RestaurantModel>>(
                jsonStr,
                Array<RestaurantModel>::class.java
            ).toList()
            return restaurentModel
        }

    override fun onItemClick(restaurantModel: RestaurantModel) {
        val intent = Intent(this@MainActivity, RestaurantMenuActivity::class.java)
        intent.putExtra("RestaurantModel", restaurantModel)
        startActivity(intent)
    }

}