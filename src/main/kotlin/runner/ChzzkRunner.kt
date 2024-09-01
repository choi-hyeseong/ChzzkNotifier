package org.example.runner

import kotlinx.coroutines.*
import org.example.notifier.StreamerNotifier
import org.example.streamer.domain.StreamerDisplayInfo
import org.example.streamer.manager.StreamerInfoManager
import org.example.streamer.parser.detail.StreamerDetailParser
import org.example.streamer.parser.detail.response.StreamerDetail
import kotlin.concurrent.thread

/**
 * 일정 주기로 온라인/오프라인 여부 확인하는 러너
 */
class ChzzkRunner(
    var interval: Int,
    private val streamerDetailParser: StreamerDetailParser,
    private val streamerInfoManager: StreamerInfoManager,
    private val notifier : StreamerNotifier
) {

    private var thread: Thread? = null
    private var lastSyncTime: Long = System.currentTimeMillis()
    private var isRunning = false

    fun start() {
        isRunning = true
        thread = thread {
            while (isRunning) {
                val gap = (System.currentTimeMillis() - lastSyncTime) / 1000 //초단위 시간
                if (gap >= interval) {
                    //갱신
                    lastSyncTime = System.currentTimeMillis()
                    val updateTarget = updateInfos(streamerInfoManager.getStreamerInfos()) //업데이트된 대상들
                    val notifyData = streamerInfoManager.update(updateTarget)
                    //notify
                    if (notifyData.isNotEmpty())
                        notifier.notify(notifyData)
                }

                Thread.sleep(1000)
            }
        }
    }

    private fun updateInfos(streamerDisplayInfo: List<StreamerDisplayInfo>): List<StreamerDetail> {
        return runBlocking {
            withContext(Dispatchers.IO) {
                streamerDisplayInfo.map { async { streamerDetailParser.parseDetail(it.chzzkId) } }.awaitAll()
            }
        }
    }

    fun stop() {
        isRunning = false
        thread?.interrupt()
    }
}