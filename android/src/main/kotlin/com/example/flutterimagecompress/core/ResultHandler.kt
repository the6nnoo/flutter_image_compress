package com.example.flutterimagecompress.core

import android.os.Handler
import android.os.Looper
import com.example.flutterimagecompress.core.heif.HeicHandler
import io.flutter.plugin.common.MethodChannel

abstract class ResultHandler(private var result: MethodChannel.Result?) : HeicHandler {
  
  companion object {
    private val handler = Handler(Looper.getMainLooper())
  }
  
  var isReply = false
  
  fun reply(any: Any?) {
    if (isReply) {
      return
    }
    isReply = true
    val result = this.result
    this.result = null
    handler.post {
      result?.success(any)
    }
  }
  
  fun replyError(code: String, message: String? = null, obj: Any? = null) {
    if (isReply) {
      return
    }
    isReply = true
    val result = this.result
    this.result = null
    handler.post {
      result?.error(code, message, obj)
    }
  }
  
}