package org.example.streamer.manager

import org.example.streamer.domain.StreamerInfo
import org.example.streamer.parser.detail.StreamerDetailParser
import org.example.streamer.parser.detail.response.StreamerDetail
import org.example.streamer.parser.search.StreamerSearcher

/**
 * 치지직 API 파서를 관리하는 매니저. 파싱은 해당 클래스를 통해 접근.
 * 파서가 너무 여러군데에서 사용되고 있음. 객체로 분리한건 좋으나.. 너무 여러군데서 난잡하게 사용 :(
 */
class ChzzkAPIManager(
    private val streamerSearcher: StreamerSearcher,
    private val streamerDetailParser: StreamerDetailParser
) {

    suspend fun searchStreamer(name : String) : List<StreamerInfo> {
        return streamerSearcher.searchStreamer(name)
    }

    suspend fun searchDetail(chzzkId : String) : StreamerDetail {
        return streamerDetailParser.parseDetail(chzzkId)
    }

}