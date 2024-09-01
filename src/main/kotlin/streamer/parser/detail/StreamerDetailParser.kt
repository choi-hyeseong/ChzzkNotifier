package org.example.streamer.parser.detail

import okhttp3.ResponseBody
import org.example.streamer.parser.AbstractParser
import org.example.streamer.parser.detail.response.StreamerDetail
import org.example.streamer.parser.detail.response.StreamerDetailResponseDTO

/**
 * 스트리머 상세정보 파싱
 */
class StreamerDetailParser : AbstractParser<StreamerDetail>() {

    private val url = "https://api.chzzk.naver.com/service/v1/channels/"

    /**
     * 치지직 id를 기반으로 stream live여부 파싱
     * @param param 치지직 id를 제공받습니다.
     */
    override suspend fun invoke(param: String): StreamerDetail {
        return parse(url.plus(param))
    }

    override fun handle(body: ResponseBody): StreamerDetail {
        val parseResult = kotlin.runCatching {
            val responseDTO: StreamerDetailResponseDTO = gson.fromJson(body.string(), StreamerDetailResponseDTO::class.java)
            responseDTO.content
        }.onFailure {
            throw IllegalStateException("파싱중 오류가 발생했습니다.")
        }
        return parseResult.getOrThrow() //파싱 못하는건 throw
    }


    suspend fun parseDetail(chzzkId : String) : StreamerDetail {
        return this.invoke(chzzkId)
    }
}