package org.example.streamer.domain

/**
 * 스트리머 정보를 담고있는 객체
 * @property name 스트리머의 치지직 이름입니다.
 * @property chzzkId 스트리머의 치지직 id입니다.
 * @property avatarUrl 스트리머의 치지직 아바타 url입니다. 아바타가 지정되어 있지 않을경우 빈 문자열을 반환합니다.
 * @property isBroadcasting 스트리머가 현재 방송중인지 여부입니다.
 */
data class StreamerInfo(
    val name : String,
    val chzzkId : String,
    val avatarUrl : String,
    var isBroadcasting : Boolean
)