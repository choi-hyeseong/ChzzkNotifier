package org.example.web

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * 웹 요청 진행하는 유틸
 */
class WebUtil {

    companion object {

        private val okHttpClient : OkHttpClient = OkHttpClient()

        /**
         * 해당 url로 get요청 수행
         */
        suspend fun parseGET(url : String) : Response {
            val request : Request = Request.Builder()
                .url(url)
                //유저 에이전트 미명시하면 파싱이 안되네..?
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36")
                .get()
                .build()
            return okHttpClient.newCall(request).execute()
        }
    }
}