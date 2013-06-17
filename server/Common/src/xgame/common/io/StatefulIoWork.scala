package xgame.common.io

import IoWorkStateEnum._

/**
 * 有状态的异步操作
 *
 * @author haijiang
 * @since 2013/6/16
 *
 */
class StatefulIoWork(val innerIoWork : TIoWork) extends TIoWork {
    require(innerIoWork != null)

    /** 当前状态 */
    var currState : IoWorkStateEnum = nil

    def doInit() = {
        // 执行初始化过程
        val result = this.innerIoWork.doInit()
        // 设置当前状态
        this.currState = if (result) initOk else exit

        result
    }

    def doAsyncProc() = {
        // 执行异步操作过程
        val result = this.innerIoWork.doAsyncProc()
        // 设置当前状态
        this.currState = if (result) asyncProcOk else exit

        result
    }

    def doFinished() = {
        // 执行完成操作
        val result = this.innerIoWork.doFinished()
        // 设置当前状态
        this.currState = if (result) finished else exit

        result
    }
}
