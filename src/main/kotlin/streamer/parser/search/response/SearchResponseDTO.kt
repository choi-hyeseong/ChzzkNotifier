package org.example.streamer.parser.search.response

/**
 * 검색 결과 API Response
 */
data class SearchResponseDTO(
    val code: Long,
    val message: String?,
    val content: Content,
)

data class Content(
    val size: Long,
    val page: Page,
    val data: List<Data>,
)

data class Page(
    val next: Next,
)

data class Next(
    val offset: Long,
)

data class Data(
    val channel: Channel,
)

data class Channel(
    val channelId: String,
    val channelName: String,
    val channelImageUrl: String,
    val verifiedMark: Boolean,
    val channelDescription: String,
    val followerCount: Long,
    val openLive: Boolean,
    val personalData: PersonalData,
)

data class PersonalData(
    val following: Following,
    val privateUserBlock: Boolean,
)

data class Following(
    val following: Boolean,
    val notification: Boolean,
    val followDate: Any?,
)
