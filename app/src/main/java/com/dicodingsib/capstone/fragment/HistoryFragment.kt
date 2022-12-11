package com.dicodingsib.capstone.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsib.capstone.adapter.DataAdapter
import com.dicodingsib.capstone.databinding.FragmentHistoryBinding
import com.dicodingsib.capstone.model.Tabungan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db_Tabungan : FirebaseDatabase
    private lateinit var dbRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        db_Tabungan = FirebaseDatabase.getInstance()
        dbRef = db_Tabungan.getReference(HomeFragment.TABUNGAN_CHILD).child(user?.uid.toString())


        getData()

}

    private fun getData() {

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list=ArrayList<Tabungan>()
                for (data in snapshot.children){
                    var model=data.getValue(Tabungan::class.java)
                    list.add(model as Tabungan)
                }
                if (list.size>0){
                    var adapter = DataAdapter(list)
                    val manager = LinearLayoutManager(activity)
                    binding.rvHistory.layoutManager = manager
                    binding.rvHistory.adapter = adapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                 Log.e("error","!!")
            }

        })
    }


    }
