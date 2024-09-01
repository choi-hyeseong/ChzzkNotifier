package org.example

import org.example.bot.ChzzkBot

fun main(args : Array<String>) {
    val interval = if (args.size == 2) args[1].toIntOrNull() else 60
    ChzzkBot.create(args[0], interval ?: 60)
}