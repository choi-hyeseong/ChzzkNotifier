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
    override fun removeStreamerByIndex(streamerIndex: Int) {
        if (streamers.size <= streamerIndex)
            throw IllegalArgumentException("올바른 번호를 입력해주세요.")
        streamers.removeAt(streamerIndex)
    }

    /**
     * 라이브 정보 업데이트. 만약 변동된 (라이브 여부 변동) 데이터가 있다면 반환
     */
    fun update(details : List<StreamerDetail>) : List<StreamerInfo> {
        val foundDetails = details.map { Pair(it, find(it.channelId)) }.filter { it.second != null } //등록된 스트리머만 반환. Pair로 묶어서 업데이트 전,후로 연계
        val changedDetails = foundDetails.filter { it.first.openLive != it.second!!.isBroadcasting } //위 매칭된 스트리머 객체중 라이브여부가 변동된 객체만 반환

        changedDetails.forEach { it.second!!.isBroadcasting = it.first.openLive } //라이브여부 업데이트
        return changedDetails.map { it.second!! }
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
    fun getStreamerInfos(): List<StreamerDisplayInfo> = streamers.mapIndexed { index, streamer -> StreamerDisplayInfo(index, streamer.name, streamer.chzzkId) }

}