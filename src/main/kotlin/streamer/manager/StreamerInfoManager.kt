package org.example.streamer.manager

import org.example.streamer.domain.StreamerInfo

/**
 * 스트리머 정보를 관리하는 매니저
 * @property streamers 스트리머 정보가 담겨있는 리스트입니다.
 */
class StreamerInfoManager {

    private val streamers : MutableList<StreamerInfo> = mutableListOf()
}