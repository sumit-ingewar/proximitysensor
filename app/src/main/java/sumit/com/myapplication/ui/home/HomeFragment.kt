package sumit.com.myapplication.ui.home

import android.app.Service
import android.hardware.*
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_home.*
import sumit.com.myapplication.R
import kotlin.math.abs

class HomeFragment : Fragment(), SensorEventListener2 {

    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor

    var timeStamp = 0L
    var sensorValu = 0F

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onFlushCompleted(sensor: Sensor?) {
    }

    override fun onSensorChanged(event: SensorEvent?) {

        event?.let {

            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                text_home.text = "${event.values?.get(0)}"


//
//                if(abs(sensorValu - event.values?.get(0)!!) > 10){
//                    Log.e("value","${abs(sensorValu - event.values?.get(0)!!)}")
//                    if((System.currentTimeMillis() - timeStamp) < 1000){
//                        Log.e("value","Swiped")
//                        text_detect.text = "Swiped"
//                        sensorValu = event.values?.get(0)!!
//                    }
//
//                    timeStamp = System.currentTimeMillis()
//                }
//
//
//            }
//
//            Handler().postDelayed({
//                text_detect.text = ""
//            },2000)
            }
        }
    }

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        sensorManager = activity!!.getSystemService(Service.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)



        return root
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
    }
}