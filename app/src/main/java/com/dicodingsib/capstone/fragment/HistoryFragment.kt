package com.dicodingsib.capstone.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        dbRef = db_Tabungan.getReference(HomeFragment.TABUNGAN_CHILD)


        getData()
//
//        list = arrayListOf<Tabungan>()

//    }
//    private fun initView() {
//        tvKategori = findViewById(R.id.tvEmpId)
//        tvEmpName = findViewById(R.id.tvEmpName)
//        tvEmpAge = findViewById(R.id.tvEmpAge)
//        tvEmpSalary = findViewById(R.id.tvEmpSalary)
//
//        btnUpdate = findViewById(R.id.btnUpdate)
//        btnDelete = findViewById(R.id.btnDelete)
//    }
//    private fun setValuesToViews() {
//        binding.tvSaldo.text = intent.getStringExtra("empId")
//        tvEmpName.text = intent.getStringExtra("empName")
//        tvEmpAge.text = intent.getStringExtra("empAge")
//        tvEmpSalary.text = intent.getStringExtra("empSalary")
//
//    }

//    private fun getData() {
//        dbRef = FirebaseDatabase.getInstance().getReference(HomeFragment.TABUNGAN_CHILD)
//        val tabunganRef = db_Tabungan.reference.child(HomeFragment.TABUNGAN_CHILD)
//
//        dbRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                list.clear()
//                if (snapshot.exists()) {
//                    for (empSnap in snapshot.children) {
//                        val data = empSnap.getValue(Tabungan::class.java)
//                        list.add(data!!)
//                    }
//                    val mAdapter = Adapter(list)
//                    recyclerView.adapter = mAdapter
//
////                    mAdapter.setOnItemClickListener(object : Adapter.onItemClickListener {
////                        override fun onItemClick(position: Int) {
////
////                            val intent =
////                                Intent(this@FetchingActivity, EmployeeDetailsActivity::class.java)
////
////                            //put extras
////                            intent.putExtra("empId", empList[position].empId)
////                            intent.putExtra("empName", empList[position].empName)
////                            intent.putExtra("empAge", empList[position].empAge)
////                            intent.putExtra("empSalary", empList[position].empSalary)
////                            startActivity(intent)
////                        }
//
////                    })
//
//                    recyclerView.visibility = View.VISIBLE
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        }
//
//    }


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
                    binding.rvHistory.adapter = adapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                 Log.e("error","haiii")
            }

        })
    }


    }
