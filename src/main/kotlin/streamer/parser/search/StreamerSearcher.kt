package org.example.streamer.parser.search

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.example.streamer.domain.StreamerInfo
import org.example.streamer.parser.AbstractParser
import org.example.streamer.parser.search.response.SearchResponseDTO
import org.example.web.WebUtil
import java.io.IOException

/**
 * 스트리머 검색 API
 */
class StreamerSearcher : AbstractParser<List<StreamerInfo>>() {

    private val url = "https://api.chzzk.naver.com/service/v1/search/channels"

    /**
     * 스트리머를 검색합니다.
     * @param param 스트리머의 이름
     * @return 파싱된 스트리머 정보를 반환합니다.
     */
    override suspend fun invoke(param: String): List<StreamerInfo> {
        return parse(url.plus("?keyword=$param"))
    }

    override fun handle(body: ResponseBody): List<StreamerInfo> {
        val parseResult = kotlin.runCatching {
            val responseDTO: SearchResponseDTO = gson.fromJson(body.string(), SearchResponseDTO::class.java)
            responseDTO.content.data.map { StreamerInfo(it.channel.channelName, it.channel.channelId, it.channel.channelImageUrl ?: "", false) }
        }.onFailure {
            throw IllegalStateException("파싱중 오류가 발생했습니다. - ${it.message}")
        }
        return parseResult.getOrNull() ?: mutableListOf()
    }

    /**
     * 기존 invoke를 사용해도 되고 해당 메소드를 사용해도 됨
     */
    suspend fun searchStreamer(name : String) : List<StreamerInfo> {
        return invoke(name)
    }
}