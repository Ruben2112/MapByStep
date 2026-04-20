package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.model.StepData
import com.heveamobile.mapbystep.domain.repository.UserRepository
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

class UpdateUserRecordsUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        println("Starting update user records")
        val startDuration: Instant = Clock.System.now()

        val user = userRepository.getUserWithStepData()
        val stepData = user?.stepData
            ?: return

        if (stepData.isEmpty()) return

        // Sort by time to ensure the sliding window moves correctly
        val sortedSteps = stepData.sortedBy { it.startTime }

        // Calculate all records using sliding windows
        val new24h = calculateSlidingWindowMax(
            steps = sortedSteps,
            windowDuration = 24.hours,
        )
        val new7d = calculateSlidingWindowMax(
            steps = sortedSteps,
            windowDuration = 7.days,
        )
        val new30d = calculateSlidingWindowMax(
            steps = sortedSteps,
            windowDuration = 30.days,
        )

        // Only update if a record is actually broken
        if (new24h > user.twentyFourHourRecord || new7d > user.sevenDayRecord || new30d > user.thirtyDayRecord) {
            userRepository.updateUser(
                user.copy(
                    twentyFourHourRecord = maxOf(
                        user.twentyFourHourRecord,
                        new24h,
                    ),
                    sevenDayRecord = maxOf(
                        user.sevenDayRecord,
                        new7d,
                    ),
                    thirtyDayRecord = maxOf(
                        user.thirtyDayRecord,
                        new30d,
                    ),
                ),
            )
        }
        println("Done updating user records, took ${Clock.System.now() - startDuration}")
    }

    /**
     * Calculates the maximum steps found within any rolling window of [windowDuration].
     */
    private fun calculateSlidingWindowMax(
        steps: List<StepData>,
        windowDuration: Duration,
    ): Long {
        var maxSteps = 0L
        var currentWindowSum = 0L
        var right = 0

        // Sliding window using two pointers (left and right)
        for (left in steps.indices) {
            val windowStart = steps[left].startTime
            val windowEnd = windowStart + windowDuration

            // Expand the window to the right as long as steps fall within the duration
            while (right < steps.size && steps[right].startTime < windowEnd) {
                currentWindowSum += steps[right].count
                right++
            }

            if (currentWindowSum > maxSteps) {
                maxSteps = currentWindowSum
            }

            // Shrink the window from the left for the next iteration
            currentWindowSum -= steps[left].count
        }

        return maxSteps
    }
}