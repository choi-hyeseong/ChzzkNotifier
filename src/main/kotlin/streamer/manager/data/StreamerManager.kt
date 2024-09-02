package org.example.streamer.manager.data

import org.example.streamer.domain.StreamerDisplayInfo
import org.example.streamer.domain.StreamerInfo
import org.example.streamer.parser.detail.response.StreamerDetail

/**
 * 스트리머 정보 관리하는 인터페이스
 *
 * 현재는 인메모리 방식 관리하는 클래스만 구현됨. 추후 Write-Through 방식 support하는 클래스 구현하기
 *
 * @see CacheStreamerManager
 */
interface StreamerManager {

    // 스트리머 추가
    fun addStreamer(streamer: StreamerInfo)

    //해당 인덱스 스트리머 삭제
    fun removeStreamerByIndex(streamerIndex: Int)

    // 등록된 스트리머 갯수
    fun getCount(): Int

    // 업데이트 수행. 값이 변경된 객체 반환
    fun update(streamerInfos : List<StreamerInfo>) : List<StreamerInfo>

    // 안전하게 객체 정보 VO로 반환하기. streamers를 그대로 노출하는건 좋지 않아보임
    fun getStreamerInfos(): List<StreamerDisplayInfo>
}