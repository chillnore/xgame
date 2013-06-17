package xgame.common.io

import impl.{SyncIoWorkProcedure, AsyncIoWorkProcedure}

/**
 * IO 工作服务
 *
 * @author haijiang
 * @since 2013/6/17
 *
 */
object IoWorkService {
    /** 内置 IO 工作过程 */
    private[this] var currProc : TIoWorkProcedure[TIoWork] = null
    /** 异步工作过程 */
    private[this] val asyncProc : AsyncIoWorkProcedure = new AsyncIoWorkProcedure
    /** 同步工作过程 */
    private[this] val syncProc : SyncIoWorkProcedure = new SyncIoWorkProcedure

    /** 工作于异步模式 */
    var asyncMode : Boolean = false

    /**
     * 开始工作
     *
     * @param work
     *
     */
    def startWork(work : TIoWork) : Unit = {
        // 如果参数为空, 则直接退出!
        if (work == null) Unit

        // 设置工作过程
        this.currProc = if (this.asyncMode) this.syncProc else this.asyncProc
        // 开始工作
        this.currProc.startWork(work)
    }
}

