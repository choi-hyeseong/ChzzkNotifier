package org.example.streamer.parser.detail.response

import org.example.streamer.domain.StreamerInfo

data class StreamerDetailResponseDTO(
    val code: Long,
    val message: String?,
    val content: StreamerDetail,
)

data class StreamerDetail(
    val channelId: String,
    val channelName: String,
    val channelImageUrl: String,
    val verifiedMark: Boolean,
    val channelType: String,
    val channelDescription: String,
    val followerCount: Long,
    val openLive: Boolean,
    val subscriptionAvailability: Boolean,
    val subscriptionPaymentAvailability: SubscriptionPaymentAvailability,
    val adMonetizationAvailability: Boolean,
) {
    fun toDomain() : StreamerInfo = StreamerInfo(this.channelId, this.channelName, this.channelImageUrl, openLive)
}

data class SubscriptionPaymentAvailability(
    val iapAvailability: Boolean,
    val iabAvailability: Boolean,
)
