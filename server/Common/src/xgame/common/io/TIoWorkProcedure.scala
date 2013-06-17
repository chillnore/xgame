package xgame.common.io

/**
 * 异步操作过程
 *
 * @author haijiang
 * @since 2013/6/16
 * @tparam T
 *
 */
trait TIoWorkProcedure[T <: TIoWork] {
    /**
     * 开始异步工作
     *
     * @param work
     *
     */
    def startWork(work : T) : Unit
}
