package com.example.mamorun_webview

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_main)

        val webView: WebView = findViewById(R.id.webView)

        // JavaScriptを有効化
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // キャッシュモードをデフォルトに設定
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT

        // その他の設定
        webView.settings.domStorageEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true

        // WebViewClientを設定
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return true
            }
        }

        // キャッシュをクリア（必要に応じて）
        webView.clearCache(true)
        webView.clearHistory()

        // URLを読み込む
        webView.loadUrl("https://kamiyama.ac.jp")
    }
}