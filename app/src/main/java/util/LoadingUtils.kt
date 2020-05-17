package util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.smkccovid.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
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