package org.example.streamer.manager.data

import org.example.streamer.domain.StreamerDisplayInfo
import org.example.streamer.domain.StreamerInfo
import org.example.streamer.parser.detail.response.StreamerDetail

/**
 * 스트리머 정보를 관리하는 매니저
 * @property streamers 스트리머 정보가 담겨있는 리스트입니다.
 */
class CacheStreamerManager : StreamerManager{

    private val streamers: MutableList<StreamerInfo> = mutableListOf()

    // 스트리머 추가
    override fun addStreamer(streamer: StreamerInfo) {
        if (isContains(streamer))
            throw IllegalArgumentException("이미 추가된 스트리머입니다.")
        streamers.add(streamer)
    }

    //해당 인덱스 스트리머 삭제
    override fun removeStreamerByIndex(streamerIndex: Int) : StreamerInfo {
        if (streamers.size <= streamerIndex)
            throw IllegalArgumentException("올바른 번호를 입력해주세요.")
        return streamers.removeAt(streamerIndex)
    }

    /**
     * 라이브 정보 업데이트. 만약 변동된 (라이브 여부 변동) 데이터가 있다면 반환
     */
    override fun update(streamerInfos : List<StreamerInfo>) : List<StreamerInfo> {
        val result : MutableList<StreamerInfo> = mutableListOf()
        streamerInfos.forEach { info ->
            val savedStreamer = find(info.chzzkId) //리스트에 등록된 스트리머일경우 검색
            if (savedStreamer != null && info.isBroadcasting != savedStreamer.isBroadcasting) { //리스트에 등록되있고, 방송 여부 상태가 서로 다른경우
                savedStreamer.isBroadcasting = info.isBroadcasting
                result.add(savedStreamer)
            }
        }
        return result
    }

    //이미 스트리머 목록에 추가된경우
    private fun isContains(streamerInfo: StreamerInfo): Boolean {
        return find(streamerInfo.chzzkId) != null
    }

    private fun find(chzzkId : String): StreamerInfo? {
        return streamers.find { it.chzzkId == chzzkId }
    }

    override fun getCount(): Int = streamers.size

    // 안전하게 객체 정보 반환하기. streamers를 그대로 노출하는건 좋지 않아보임
    override fun getStreamerInfos(): List<StreamerDisplayInfo> = streamers.mapIndexed { index, streamer -> StreamerDisplayInfo(index, streamer.name, streamer.chzzkId) }

}
