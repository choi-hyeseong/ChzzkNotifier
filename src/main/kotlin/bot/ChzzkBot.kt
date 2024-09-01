package org.example.bot

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.example.command.ChzzkCommand
import org.example.command.add.StreamerAddCommand
import org.example.command.list.StreamerListCommand
import org.example.command.reload.StreamerReloadCommand
import org.example.command.remove.StreamerRemoveCommand
import org.example.listener.ButtonListener
import org.example.streamer.manager.StreamerInfoManager
import org.example.streamer.parser.detail.StreamerDetailParser
import org.example.streamer.parser.search.StreamerSearcher

/**
 * 치지직 파싱 봇 메인
 * @param token 봇 구동을 위한 토큰값입니다. notnull입니다.
 */
class ChzzkBot private constructor(private val token : String) {

    //composition

    //info
    private val streamerInfoManager : StreamerInfoManager = StreamerInfoManager()

    //parser
    private val streamerSearcher : StreamerSearcher = StreamerSearcher()
    private val streamerDetailParser : StreamerDetailParser = StreamerDetailParser()

    //command
    private val addCommand : StreamerAddCommand = StreamerAddCommand(streamerSearcher)
    private val removeCommand : StreamerRemoveCommand = StreamerRemoveCommand(streamerInfoManager)
    private val listCommand : StreamerListCommand = StreamerListCommand(streamerInfoManager)
    private val reloadCommand : StreamerReloadCommand = StreamerReloadCommand() //미구현

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
        jda = builder.addEventListeners(ChzzkCommand(addCommand, removeCommand, listCommand, reloadCommand), ButtonListener(streamerDetailParser, streamerInfoManager)).build().awaitReady()
    }
}