package com.example.mamorun_webview

import android.Manifest
import android.content.pm.PackageManager
import android.net.http.SslError
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val LOCATION_PERMISSION_REQUEST_CODE = 101
    private val AUDIO_PERMISSION_REQUEST_CODE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_main)

        // カメラのパーミッションを確認・リクエスト
        checkAndRequestPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE)

        // 位置情報のパーミッションを確認・リクエスト
        checkAndRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE)

        // マイクのパーミッションを確認・リクエスト
        checkAndRequestPermission(Manifest.permission.RECORD_AUDIO, AUDIO_PERMISSION_REQUEST_CODE)

        // WebViewの設定
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

        // Geolocationの有効化
        webView.settings.setGeolocationEnabled(true)

        // WebViewClientを設定
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return true
            }

            // SSLエラーを無視して続行する
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed() // SSLエラーを無視して続行
            }
        }

        // WebChromeClientを設定して、カメラ、マイク、位置情報のアクセスを許可
        webView.webChromeClient = object : WebChromeClient() {

            // カメラ・マイクへのアクセスを許可
            override fun onPermissionRequest(request: PermissionRequest) {
                request.grant(request.resources)
            }

            // Geolocationへのアクセスを許可
            override fun onGeolocationPermissionsShowPrompt(
                origin: String,
                callback: GeolocationPermissions.Callback
            ) {
                // 位置情報のアクセスを許可
                callback.invoke(origin, true, false)
            }
        }

        // キャッシュをクリア（必要に応じて）
        webView.clearCache(true)
        webView.clearHistory()

        // URLを読み込む
        webView.loadUrl("https://172.16.20.113:3000")
    }

    // 権限を確認し、リクエストするメソッド
    private fun checkAndRequestPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    // パーミッションの結果を処理する
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // カメラのパーミッションが許可された
                } else {
                    // カメラのパーミッションが拒否された場合の処理
                }
            }
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 位置情報のパーミッションが許可された
                } else {
                    // 位置情報のパーミッションが拒否された場合の処理
                }
            }
            AUDIO_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // マイクのパーミッションが許可された
                } else {
                    // マイクのパーミッションが拒否された場合の処理
                }
            }
        }
    }
}
