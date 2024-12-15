package com.example.diarynote.UI

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.diarynote.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)  // Pastikan layout ini ada

        Handler(Looper.getMainLooper()).postDelayed({
            // Pindah ke MainActivity setelah 2 detik
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Tutup StartActivity supaya tidak bisa kembali ke activity ini
        }, 2000) // Delay 2 detik
    }
}