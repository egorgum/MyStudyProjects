package com.example.daggerandkoin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.daggerandkoin.bicycle.factorys.BicycleFactory
import com.example.daggerandkoin.databinding.ActivityMainBinding
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import javax.inject.Inject

class MainActivity(

) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btDagger.setOnClickListener{
            val com = App().mainComponent
            Toast.makeText(this,"Dagger: ${com.bicycleFactory().frameFactory.getFrame().color} " +
                    "and ${com.bicycleFactory().build().logo}", Toast.LENGTH_SHORT).show()
        }
        binding.btKoin.setOnClickListener{
            val com = get<BicycleFactory>()

            Toast.makeText(this,"Koin: ${com.frameFactory.getFrame().color} " +
                    "and ${com.build().logo}", Toast.LENGTH_SHORT).show()


        }
    }

}