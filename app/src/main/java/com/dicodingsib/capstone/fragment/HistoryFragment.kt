package com.dicodingsib.capstone.fragment

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsib.capstone.adapter.DataAdapter
import com.dicodingsib.capstone.databinding.FragmentHistoryBinding
import com.dicodingsib.capstone.databinding.ItemHistoryBinding
import com.dicodingsib.capstone.model.Tabungan
import com.dicodingsib.capstone.utility.Extensions.rupiahFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db_Tabungan : FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    var saldo = 0
    private lateinit var imageUri: Uri

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

        if (user!=null){
            if (user.photoUrl != null){
                Picasso.get().load(user.photoUrl).into(binding.ivProfile)
            }
        }


}

    private fun getData() {
        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list=ArrayList<Tabungan>()
                for (data in snapshot.children){
                    var model=data.getValue(Tabungan::class.java)
                    list.add(Tabungan(
                        id = data.key,
                        kategori = model?.kategori,
                        berat = model?.berat,
                        harga = model?.harga,
                        total = model?.total,
                        tanggal = model?.tanggal
                    ))
                    saldo += model?.total!!
                }

                binding.tvSaldo.text = rupiahFormat(saldo)
                if (list.size>0){
                    var adapter = DataAdapter(list)
                    adapter.setOnItemClickCallback(object : DataAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: Tabungan) {
                            deleteTabungan(data)
                        }
                    })
                    val manager = LinearLayoutManager(activity)
                    binding.rvHistory.layoutManager = manager
                    binding.rvHistory.adapter = adapter
                }
                else{
                    binding.tvNotFound.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.GONE

                }

            }

            override fun onCancelled(error: DatabaseError) {
                 Log.e("error","!!")
            }

        })
    }


    private fun deleteTabungan(tabungan: Tabungan){
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("Hapus riwayat ini?")
        alertDialogBuilder.setPositiveButton("Ya, Hapus") { dialogInterface: DialogInterface?, i: Int ->
            Toast.makeText(activity, "Tabungan telah dihapus", Toast.LENGTH_LONG).show()
            dbRef.child(tabungan.id.toString()).removeValue()
        }

        alertDialogBuilder.setNegativeButton("Batal") { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

//    private fun onDelete(snapshot: DataSnapshot) {
//        var list=ArrayList<Tabungan>()
//        val alertDialogBuilder = AlertDialog.Builder(this)
//        alertDialogBuilder.setMessage("Hapus riwayat ini?")
//        alertDialogBuilder.setPositiveButton("Ya, Hapus") { dialogInterface: DialogInterface?, i: Int ->
////            val uid =
////
////            Toast.makeText(this@RiwayatActivity, "Data yang dipilih sudah dihapus", Toast.LENGTH_SHORT).show()
//        }
//
//        alertDialogBuilder.setNegativeButton("Batal") { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel() }
//        val alertDialog = alertDialogBuilder.create()
//        alertDialog.show()
//    }


    }
