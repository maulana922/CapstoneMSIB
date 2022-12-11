package com.dicodingsib.capstone.model

class Tabungan {
    var id:String? = null
    var kategori: String? = null
    var berat: Int? = null
    var harga: Int? = null
    var total: Int? = null
    var tanggal: String? = null


    constructor() {}


    constructor(kategori: String?, berat: Int?, harga: Int?, total: Int?, tanggal: String?) {
        this.kategori = kategori
        this.berat = berat
        this.harga = harga
        this.total = total
        this.tanggal = tanggal
    }


}