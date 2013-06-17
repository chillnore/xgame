package xgame.common

import io.IoWorkService

/**
 * 测试对象
 *
 */
object Test {
    IoWorkService.asyncMode = true
    IoWorkService.startWork(null)
}
