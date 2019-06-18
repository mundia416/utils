package com.nosetrap.util.fragment


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.nosetrap.util.R
import com.nosetrap.util.data.CounterData
import com.nosetrap.util.viewmodel.MainFragmentViewModel
import kotlinx.android.synthetic.main.drawer.*
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setOnClickListeners()
        prepareActionbar()

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        drawerView.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.mi_settings -> {
                    val direction = MainFragmentDirections.actionMainFragmentToSettingsFragment3(tvCounter.text.toString().toInt())
                    Navigation.findNavController(activity!!,R.id.nav_host_fragment).navigate(direction)
                }
            }
            true
        }
    }

    private fun prepareActionbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val actionbar = (activity as AppCompatActivity).supportActionBar!!
        actionbar.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_options_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mi_settings -> {
                val direction = MainFragmentDirections.actionMainFragmentToSettingsFragment3(tvCounter.text.toString().toInt())
                Navigation.findNavController(activity!!,R.id.nav_host_fragment).navigate(direction)
            }
            android.R.id.home -> {
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START)
                }else {
                    drawer.openDrawer(GravityCompat.START)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init(){
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
    }


    private fun setOnClickListeners(){
        btnAdd.setOnClickListener {
            val counterData = CounterData()
            counterData.inputCounterValue(tvCounter.text.toString().toInt())
            val newValue = viewModel.addOneToCounter(counterData)
            tvCounter.text = newValue.toString()
        }

        btnReset.setOnClickListener {
            tvCounter.text = "0"
        }

        btnSettings.setOnClickListener { v ->
            val counterValue = tvCounter.text.toString().toInt()

           val action = MainFragmentDirections.actionMainFragmentToSettingsFragment3(counterValue)

            findNavController(v).navigate(action)
        }

    }

}
