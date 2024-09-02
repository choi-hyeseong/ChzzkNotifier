package org.example.streamer.manager.data

import org.example.streamer.domain.StreamerInfo

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


    fun getCount(): Int
}