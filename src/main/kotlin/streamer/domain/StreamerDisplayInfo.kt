package org.example.streamer.domain

/**
 * VO 느낌의 읽기전용 info 객체. 내부 객체를 반환하지 않고, index와 이름, 치지직 id 반환
 */
data class StreamerDisplayInfo(
    val index : Int,
    val name : String,
    val chzzkId : String
)