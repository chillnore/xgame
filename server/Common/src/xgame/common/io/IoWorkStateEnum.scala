package xgame.common.io

/**
 * 异步操作状态枚举
 *
 * @author haijiang
 * @since 2013/6/16
 *
 */
object IoWorkStateEnum extends Enumeration {
    type IoWorkStateEnum = Value

    /** 无状态 */
    val nil = Value(-1, "nil")
    /** 初始化完成 */
    val initOk = Value(0, "initOk")
    /** 异步调用完成 */
    val asyncProcOk = Value(1, "asyncProcOk")
    /** 执行完成 */
    val finished = Value(2, "finishOk")
    /** 退出 */
    val exit = Value(99, "exit")
}
