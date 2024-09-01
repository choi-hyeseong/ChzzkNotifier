package org.example.bot

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

/**
 * 치지직 파싱 봇 메인
 * @param token 봇 구동을 위한 토큰값입니다. notnull입니다.
 */
class ChzzkBot private constructor(private val token : String) {

    companion object {
        // 전역 JDA 인스턴스. nullable 함. create 호출로 새로운 인스턴스 형성시 초기화됨.
        var jda : JDA? = null

        /**
         * 봇 생성용 static method
         * @param token 봇의 토큰입니다. nullable 하지만, null 입력시 Exception이 발생합니다
         * @throws IllegalArgumentException 토큰이 제공되지 않을경우 발생합니다.
         */
        fun create(token : String?) : ChzzkBot {
            if (token.isNullOrEmpty())
                throw IllegalArgumentException("Token cannot be null or empty")
            return ChzzkBot(token).also { it.init() }
        }
    }

    /**
     * 최초 봇 '초기화' 함수. JDA 인스턴스를 초기화합니다.
     */
    private fun init() {
        // 메시지 가져오는 권한 부여한 JDA 인스턴스 빌더
        val builder = JDABuilder.create(token, listOf(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_MESSAGE_POLLS))
        jda = builder.build()
    }
}