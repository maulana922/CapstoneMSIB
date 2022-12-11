package com.dicodingsib.capstone.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicodingsib.capstone.R
import com.dicodingsib.capstone.article.RecycleActivity
import com.dicodingsib.capstone.article.ReduceActivity
import com.dicodingsib.capstone.article.ReuseActivity
import com.dicodingsib.capstone.databinding.FragmentHomeBinding
import com.dicodingsib.capstone.model.Tabungan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db_Tabungan : FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    lateinit var id: String
    lateinit var kategoriSelected: String
    lateinit var hargaSelected: String
    lateinit var kategori: Array<String>
    lateinit var harga: Array<String>
    lateinit var tanggal: String
    var countBerat = 0
    var countHarga = 0
    var countTotal = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        db_Tabungan = Firebase.database
        dbRef = db_Tabungan.getReference(TABUNGAN_CHILD)
        setInitLayout()

        clickReuse()
        clickReduce()
        clickRecycle()


        binding.btnTabung.setOnClickListener{v: View? ->
            tanggal = binding.inputTanggal.text.toString()

            if ((kategori.isEmpty()) || (countBerat == 0) || (countHarga == 0 || tanggal.isEmpty() )){
                Toast.makeText(
                    activity,
                    "Data tidak boleh ada yang kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                val tabungan = Tabungan(
                    kategoriSelected,
                    countBerat,
                    countHarga,
                    countTotal,
                    tanggal
                )
                id = dbRef.push().key.toString()
                if (id != null) {
                    dbRef.child(id).push().setValue(tabungan)
                    Toast.makeText(
                        activity,
                        "Tabungan anda sedang diproses, cek di menu riwayat",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.inputBerat.text?.clear()
                    binding.inputTanggal.text?.clear()
                }
            }
        }
    }

    private fun clickReduce() {
        binding.cvReduce.setOnClickListener {
            val intent = Intent(activity, ReduceActivity::class.java)
            startActivity(intent)
        }

    }private fun clickRecycle() {
        binding.cvRecycle.setOnClickListener {
            val intent = Intent(activity, RecycleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickReuse() {
        binding.cvReuse.setOnClickListener {
            val intent = Intent(activity, ReuseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setInitLayout() {
        kategori = resources.getStringArray(R.array.kategori_sampah)
        harga = resources.getStringArray(R.array.harga_perkilo)

        val arrayBahasa =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, kategori) }
        arrayBahasa?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spKategori.setAdapter(arrayBahasa)

        binding.spKategori.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                kategoriSelected = parent.getItemAtPosition(position).toString()
                hargaSelected = harga[position]
                binding.spKategori.setEnabled(true)
                countHarga = hargaSelected.toInt()
                if (binding.inputBerat.getText().toString() != "") {
                    countBerat = binding.inputBerat.getText().toString().toInt()
                    setTotalPrice(countBerat)
                } else {
                    binding.inputHarga.setText(rupiahFormat(countHarga))
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        binding.inputBerat.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(editable: Editable) {
                binding.inputBerat.removeTextChangedListener(this)
                if (editable.length > 0) {
                    countBerat = editable.toString().toInt()
                    setTotalPrice(countBerat)
                }
                else {
                    binding.inputHarga.setText(rupiahFormat(countHarga))
                }
                binding.inputBerat.addTextChangedListener(this)
            }
        })

        binding.inputTanggal.setOnClickListener { view: View? ->
            val tanggalSetor = Calendar.getInstance()
            val date =
                DatePickerDialog.OnDateSetListener { view1: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    tanggalSetor[Calendar.YEAR] = year
                    tanggalSetor[Calendar.MONTH] = monthOfYear
                    tanggalSetor[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val strFormatDefault = "d MMMM yyyy"
                    val simpleDateFormat = SimpleDateFormat(strFormatDefault, Locale.getDefault())
                    binding.inputTanggal.setText(simpleDateFormat.format(tanggalSetor.time))
                }
            activity?.let {
                DatePickerDialog(
                    it, date,
                    tanggalSetor[Calendar.YEAR],
                    tanggalSetor[Calendar.MONTH],
                    tanggalSetor[Calendar.DAY_OF_MONTH]
                ).show()
            }
        }
    }

    private fun setTotalPrice(berat: Int) {
        countTotal = countHarga * berat
        binding.inputHarga.setText(rupiahFormat(countTotal))
    }

    fun rupiahFormat(price: Int): String {
        val formatter = DecimalFormat("#,###")
        return "Rp " + formatter.format(price.toLong())
    }




    companion object {
        const val TABUNGAN_CHILD = "Tabungan"
    }


}