package com.freshworks.yana

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {


    lateinit var  alertDialog: android.app.AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        alertDialog = SpotsDialog.Builder().setContext(this).build()


        webView.settings.javaScriptEnabled= true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object  : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                alertDialog.dismiss()
            }
        }

         if (intent!=null)
         {
             val url = intent.getStringExtra("WEBURL")
             if(url.length!=0)
             {
                 webView.loadUrl(url)
             }
         }

    }
}
