package net.meilcli.etee

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.holder_main.view.*
import net.meilcli.etee.fixed.FixedActivity
import net.meilcli.etee.navigation.NavigationActivity
import net.meilcli.etee.simple.SimpleActivity
import net.meilcli.etee.sticky.Sticky2Activity
import net.meilcli.etee.sticky.StickyActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainAdapter(::startActivity).apply {
            add(MainItem("Simple", SimpleActivity::class.java))
            add(MainItem("Static Header", FixedActivity::class.java))
            add(MainItem("Sticky Header", StickyActivity::class.java))
            add(MainItem("Sticky Header2", Sticky2Activity::class.java))
            add(MainItem("Bottom Navigation", NavigationActivity::class.java))
        }
    }

    private fun startActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}

class MainItem(val text: String, val activity: Class<*>)

class MainViewHolder private constructor(item: View) : RecyclerView.ViewHolder(item) {

    companion object {

        fun create(parent: ViewGroup): MainViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.holder_main, parent, false)
            return MainViewHolder(view)
        }
    }

    fun bind(item: MainItem, startActivity: (Class<*>) -> Unit) {
        itemView.text.text = item.text
        itemView.setOnClickListener {
            startActivity(item.activity)
        }
    }
}

class MainAdapter(
    private val startActivity: (Class<*>) -> Unit
) : RecyclerView.Adapter<MainViewHolder>() {

    private val list = mutableListOf<MainItem>()

    fun add(item: MainItem) {
        list += item
        notifyItemInserted(list.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position], startActivity)
    }
}