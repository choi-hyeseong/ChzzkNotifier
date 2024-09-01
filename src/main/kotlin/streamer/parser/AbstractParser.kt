package org.example.streamer.parser

import com.google.gson.Gson
import okhttp3.ResponseBody
import org.example.web.WebUtil
import java.io.IOException

/**
 * 웹 파싱중 공용으로 사용되는 부분만 따로 추상화시킨 클래스
 */
abstract class AbstractParser<T>{

    protected val gson : Gson = Gson()

    /**
     * 실질적으로 외부에서 Parse를 호출할때 사용하는 함수.
     * @param param url에 추가될 파라미터입니다.
     */
    abstract suspend operator fun invoke(param : String) : T

    /**
     * 결과값이 정상적으로 요청된경우 body를 T로 변환하여 반환함
     */
    protected abstract fun handle(body : ResponseBody) : T

    /**
     * 실제 파싱시 호출할 함수
     * @throws IOException 요청중 오류가 발생했다면 발생합니다.
     */
    protected suspend fun parse(url : String) : T {
        val response = WebUtil.parseGET(url) //목록 파싱
        val body = response.body
        if (response.code != 200 || body == null)
            throw IOException("요청이 정상적으로 이루어지지 않았습니다.")
       return handle(body)
    }
}