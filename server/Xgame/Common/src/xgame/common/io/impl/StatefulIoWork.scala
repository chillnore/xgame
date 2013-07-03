package xgame.common.io.impl

import IoWorkStateEnum._
import xgame.common.io.TIoWork

/**
 * 有状态的异步操作
 *
 * @author haijiang
 * @since 2013/6/16
 *
 */
private[io] class StatefulIoWork(val innerIoWork : TIoWork) extends TIoWork {
    require(innerIoWork != null)

    /** 当前状态 */
    var currState : IoWorkStateEnum = nil

    def doInit() = {
        // 如果当前状态不为空,
        // 则直接跳过!
        if (this.currState != nil) false

        // 执行初始化过程
        val result = this.innerIoWork.doInit()
        // 设置当前状态
        this.currState = if (result) initOk else exit

        result
    }

    def doAsyncProc() = {
        // 如果当前状态不为初始化完成,
        // 则直接跳过!
        if (this.currState != initOk) false

        // 执行异步操作过程
        val result = this.innerIoWork.doAsyncProc()
        // 设置当前状态
        this.currState = if (result) asyncProcOk else exit

        result
    }

    def doFinished() = {
        // 如果当前状态不为异步过程完成状态,
        // 则直接跳过!
        if (this.currState != asyncProcOk) false

        // 执行完成操作
        val result = this.innerIoWork.doFinished()
        // 设置当前状态
        this.currState = if (result) finished else exit

        result
    }
}
