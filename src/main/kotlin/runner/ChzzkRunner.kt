package org.example.runner

import kotlinx.coroutines.*
import org.example.notifier.StreamerNotifier
import org.example.streamer.domain.StreamerDisplayInfo
import org.example.streamer.manager.ChzzkAPIManager
import org.example.streamer.manager.data.CacheStreamerManager
import org.example.streamer.parser.detail.response.StreamerDetail
import kotlin.concurrent.thread

/**
 * 일정 주기로 온라인/오프라인 여부 확인하는 러너
 */
class ChzzkRunner(
    var interval: Int,
    private val chzzkAPIManager: ChzzkAPIManager,
    private val cacheStreamerManager: CacheStreamerManager,
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
                    val updateTarget = parseStreamerLiveDetail(cacheStreamerManager.getStreamerInfos()) //치지직 api 호출해서 제일 최신 정보 불러오기
                    val notifyData = cacheStreamerManager.update(updateTarget) //업데이트 및 변경된 데이터 확인
                    //notify
                    if (notifyData.isNotEmpty())
                        notifier.notify(notifyData)
                }

                Thread.sleep(1000)
            }
        }
    }

    private fun parseStreamerLiveDetail(streamerDisplayInfo: List<StreamerDisplayInfo>): List<StreamerDetail> {
        return runBlocking {
            withContext(Dispatchers.IO) {
                streamerDisplayInfo.map { async { chzzkAPIManager.searchDetail(it.chzzkId) } }.awaitAll()
            }
        }
    }

    fun stop() {
        isRunning = false
        thread?.interrupt()
    }
}