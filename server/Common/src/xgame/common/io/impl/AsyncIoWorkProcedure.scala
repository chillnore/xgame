package xgame.common.io.impl

import xgame.common.io.{TIoWork, TIoWorkProcedure}

/**
 * 同步 IO 工作过程
 *
 * @author haijiang
 * @since 2013/6/17
 *
 */
private[io] class AsyncIoWorkProcedure extends TIoWorkProcedure[TIoWork] {
    // @Override
    def startWork(work : TIoWork) = {
    }
}
