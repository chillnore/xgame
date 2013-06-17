package xgame.common.io.impl

import xgame.common.io.{TIoWork, TIoWorkProcedure}

/**
 * 同步 IO 工作过程
 *
 * @author haijiang
 * @since 2013/6/17
 *
 */
private[io] class SyncIoWorkProcedure extends TIoWorkProcedure[TIoWork] {
    // @Override
    def startWork(work : TIoWork) = {
        // 如果参数为空, 则直接跳过!
        if (work == null) Unit
        // 直接进入下一步操作
        this.nextStep(new StatefulIoWork(work))
    }

    /**
     * 执行下一步操作
     *
     * @param work
     *
     */
    private[this] def nextStep(work : StatefulIoWork) : Unit = {
        // 如果参数为空, 则直接跳过!
        if (work == null) Unit

        work.currState match {
            // 初始化 IO 工作
            case IoWorkStateEnum.nil => work.doInit()
            // 执行异步过程
            case IoWorkStateEnum.initOk => work.doAsyncProc()
            // 完成 IO 工作
            case IoWorkStateEnum.asyncProcOk => work.doFinished()
            // 其他状态 ...
            case _ =>
        }

        // 继续下一步操作
        this.nextStep(work)
    }
}
