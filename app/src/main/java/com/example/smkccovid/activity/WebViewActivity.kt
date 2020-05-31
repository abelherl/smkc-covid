package com.example.smkccovid.activity

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.smkccovid.R
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_web_view.*
import util.tampilToast


class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initView()
    }

    override fun onBackPressed() {
        buttonBack()
    }

    private fun initView() {
        val webSettings = wv_webview.settings
        webSettings.javaScriptEnabled = true

        var url = getBundle()

        wv_webview.setBackgroundColor(Color.TRANSPARENT)
        wv_webview.webViewClient = WebViewClient()
        wv_webview.webChromeClient = WebChromeClient()
        wv_webview.loadUrl(url)

        setText(url)

        wv_webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                pb_webview.visibility = View.VISIBLE
                setText(url!!)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                wv_webview.setBackgroundColor(Color.WHITE)
            }
        }

        wv_webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                pb_webview.visibility = View.VISIBLE
                pb_webview.progress = newProgress
                if (newProgress == 100) {
                    pb_webview.visibility = View.INVISIBLE
                }
            }
        }

        bt_back_wv.setBackgroundColor(Color.TRANSPARENT)
        bt_copy_wv.setBackgroundColor(Color.TRANSPARENT)

        bt_back_wv.setOnClickListener { buttonClose() }
        bt_copy_wv.setOnClickListener { buttonCopy() }
    }

    private fun setText(string: String) {
        val url = string.split("/")[2]
        tv_title_webview.text = url
    }

    private fun getBundle(): String {
        val bundle = intent.extras
        return bundle!!.getString("url")!!
    }

    private fun buttonCopy() {
        tampilToast(this, getString(R.string.url_copied))

        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", getBundle())
        clipboard.setPrimaryClip(clip)
    }

    private fun buttonBack() {
        if (wv_webview.canGoBack()) {
            wv_webview.goBack()
        }
        else {
            buttonClose()
        }
    }

    private fun buttonClose() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }
}
