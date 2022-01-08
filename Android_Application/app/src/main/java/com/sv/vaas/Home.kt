package com.sv.vaas

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.sv.vaas.api.RetrofitInstance
import com.sv.vaas.api.vaasApi
import com.sv.vaas.databinding.FragmentHomeBinding
import com.sv.vaas.model.locations
import com.sv.vaas.model.locs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home : Fragment() {

    val TAG = "vroom"   
    private lateinit var databaseReference: DatabaseReference
    private lateinit var incidentList:ArrayList<Incidents>
    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private var LocationList:MutableList<locations> = mutableListOf<locations>()
    private var LocsList:MutableList<locs> = mutableListOf<locs>()
    lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       //incidents()
        newIncidents()
        binding.refreshBtn.setOnClickListener {
            newIncidents()
        }
        handler=Handler()
        handler.postDelayed({newIncidents()},10000)
    }
    private fun newIncidents() {
        binding.incidentRecycler.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val vaasapi = RetrofitInstance.buildService(vaasApi::class.java)
        Log.i(TAG, "instance build success ")
        val requestcall=vaasapi.getList()
        Log.i(TAG, "request failed")
        requestcall.enqueue(object : Callback<locs> {
            override fun onResponse(call: Call<locs>, response: Response<locs>) {
                if(response.isSuccessful){
                    val Locs: locs?=response.body()
                    Log.i(TAG, "response successfull")
                    binding.incidentRecycler.adapter=IncidentAdapter(Locs!!.feeds)
                   // Toast.makeText(context,Locs.feeds+Locs.feeds,Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(context,"Call Failed",Toast.LENGTH_SHORT).show()
                }
            }



            override fun onFailure(call: Call<locs>, t: Throwable) {
                Toast.makeText(context,"Failed to connect",Toast.LENGTH_SHORT).show()
            }
        })
    }

  /*  private fun incidents(){
        binding.incidentRecycler.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        incidentList= arrayListOf<Incidents>()
        databaseReference=FirebaseDatabase.getInstance().getReference("Incidents")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val incident=userSnapshot.getValue(Incidents::class.java)
                        incidentList.add(incident!!)
                    }
                    binding.incidentRecycler.adapter=IncidentAdapter(incidentList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }*/

}