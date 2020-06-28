package util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.smkccovid.R
import com.example.smkccovid.activity.DetailActivity
import com.example.smkccovid.model.SelectedCountryModel
import com.example.smkccovid.model.SettingsDataModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.voela.actrans.AcTrans
import render.animations.Fade
import render.animations.Render


fun showLoading(context: Context, swipeRefreshLayout: SwipeRefreshLayout) {
    swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context,
        R.color.green))
    swipeRefreshLayout.isEnabled = true
    swipeRefreshLayout.isRefreshing = true
}
fun dismissLoading(context: Context, view: View) {
    val render = Render(context)

    render.setDuration(1300)

    render.setAnimation(Fade().Out(view))
    render.start()

    view.isClickable = false
}
fun disableTouch(activity: FragmentActivity, idFrame: Int?, idNav: Int, botNav: Boolean) {
    if (idFrame != null) {
        val fl = activity.findViewById<FrameLayout>(idFrame)
        fl.isEnabled = true
        fl.isClickable = true
        fl.visibility = View.VISIBLE
    }

    if (botNav) {
        val tl = activity.findViewById<BottomNavigationView>(idNav)
        for (i in 0..2) {
            tl.menu.getItem(i).isEnabled = false
            tl.invalidate()
        }
    }
    else {
        val tl = activity.findViewById<TabLayout>(idNav)
        tl.isEnabled = false
        tl.invalidate()
    }
}
fun enableTouch(activity: FragmentActivity, idFrame: Int, idNav: Int, botNav: Boolean) {
    val fl = activity.findViewById<FrameLayout>(idFrame)
    fl.visibility = View.GONE

    if (botNav) {
        val tl = activity.findViewById<BottomNavigationView>(idNav)
        for (i in 0..2) {
            tl.menu.getItem(i).isEnabled = true
            tl.invalidate()
        }
    }
    else {
        val tl = activity.findViewById<TabLayout>(idNav)
        tl.isEnabled = true
        tl.invalidate()
    }
}
fun enableTouch(activity: FragmentActivity) {
    val pref = activity.getSharedPreferences("test", 0).getInt("loaded", 0)
    if (pref == 3) {
        val tl = activity.findViewById<BottomNavigationView>(R.id.bottomNavMain)
        for (i in 0..2) {
            tl.menu.getItem(i).isEnabled = true
            tl.invalidate()
        }
    }
}
fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }
    return result
}

fun goTo(context: Context, activity: AppCompatActivity, finish: Boolean, withExtra: Int? = 99) {
    val intent = Intent(context, activity::class.java)
    if (withExtra != 99) { intent.putExtra("id", withExtra); }
    if (finish) { intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
    context.startActivity(intent)
    AcTrans.Builder(context).performFade()
}
fun getBitmapFromView(view: View): Bitmap? {
    val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(returnedBitmap)

    val bgDrawable = view.background
    if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)

    view.draw(canvas)

    return returnedBitmap
}
fun goToWithBoolean(context: Context, boolean: Boolean, extra: Int) {
    val edit = context.getSharedPreferences("test", 0).edit()
    edit.putBoolean("global", boolean)
    edit.apply()
    goTo(context, DetailActivity(), false, extra)
}
fun updateSettings(settingsDataModel: SettingsDataModel, uploadToFirebase: Boolean) {
    val ref = FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()

    if (uploadToFirebase) {
        ref.child(auth.currentUser?.uid.toString()).child("UserSettings").removeValue()
        ref.child(auth.currentUser?.uid.toString()).child("UserSettings").push()
            .setValue(settingsDataModel)
    }
}
fun getSettings() {
    val ref = FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()
    var item = SettingsDataModel()

    ref.child(auth.currentUser?.uid.toString()).child("UserSettings").addValueEventListener(object :
        ValueEventListener {

        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            //Inisialisasi ArrayList
            if (dataSnapshot.hasChildren()) {
                for (snapshot in dataSnapshot.children) {
                    //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                    item = snapshot.getValue(SettingsDataModel::class.java)!!

                    Log.d("TAG", "signInWithCredential:util" + " " + item)
                }
            }
        }
    })
}
fun updateSelectedCountry(context: Context, selectedCountryModel: SelectedCountryModel, uploadToFirebase: Boolean) {
    val ref = FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()

    if (uploadToFirebase) {
        ref.child(auth.currentUser?.uid.toString()).child("UserCountry").removeValue()
        ref.child(auth.currentUser?.uid.toString()).child("UserCountry").push()
            .setValue(selectedCountryModel)
    }

    val edit = context.getSharedPreferences("test", 0).edit()
    edit.putString("slug", selectedCountryModel.slug)
    edit.apply()
}
fun getSelectedCountry(context: Context) {
    val ref = FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()
    var item = SelectedCountryModel()

    ref.child(auth.currentUser?.uid.toString()).child("UserCountry").addValueEventListener(object :
        ValueEventListener {
        val edit = context.getSharedPreferences("test", 0).edit()

        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            //Inisialisasi ArrayList
            if (dataSnapshot.hasChildren()) {
                for (snapshot in dataSnapshot.children) {
                    //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                    item = snapshot.getValue(SelectedCountryModel::class.java)!!

                    Log.d("TAG", "signInWithCredential:util" + " " + item)

                    edit.putString("slug", item.slug)
                    edit.apply()

                    //Mengambil Primary Key, digunakan untuk proses Update dan Delete
//                teman?.key = snapshot.key!!
                }
            } else {
                edit.putString("slug", "indonesia")
                edit.apply()
            }
        }
    })
}