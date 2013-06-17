package xgame.common.io

import impl.SyncIoWorkProcedure

/**
 * IO 操作服务
 *
 * @author haijiang
 * @since 2013/6/17
 *
 */
object IoWorkService {
    var p : TIoWorkProcedure[TIoWork] = new SyncIoWorkProcedure
}
