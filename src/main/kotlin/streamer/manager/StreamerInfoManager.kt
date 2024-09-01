package org.example.streamer.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.streamer.domain.StreamerDisplayInfo
import org.example.streamer.domain.StreamerInfo
import org.example.streamer.parser.detail.StreamerDetailParser

/**
 * 스트리머 정보를 관리하는 매니저
 * @property streamers 스트리머 정보가 담겨있는 리스트입니다.
 */
class StreamerInfoManager {

    private val streamers: MutableList<StreamerInfo> = mutableListOf()

    // 스트리머 추가
    fun addStreamer(streamer: StreamerInfo) {
        if (isContains(streamer))
            throw IllegalArgumentException("이미 추가된 스트리머입니다.")
        streamers.add(streamer)
    }

    //해당 인덱스 스트리머 삭제
    fun removeStreamerByIndex(streamerIndex: Int) {
        if (streamers.size <= streamerIndex)
            throw IllegalArgumentException("올바른 번호를 입력해주세요.")
        streamers.removeAt(streamerIndex)
    }

    //이미 스트리머 목록에 추가된경우
    private fun isContains(streamerInfo: StreamerInfo): Boolean {
        return streamers.find { it.chzzkId == streamerInfo.chzzkId } != null
    }

    fun getCount(): Int = streamers.size

    // 안전하게 객체 정보 반환하기. streamers를 그대로 노출하는건 좋지 않아보임
    fun getStreamerInfos(): List<StreamerDisplayInfo> = streamers.mapIndexed { index, streamer -> StreamerDisplayInfo(index, streamer.name) }

}