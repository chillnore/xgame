package xgame.common

/**
 * 游戏异常
 *
 * @author haijiang
 * @since 2013/6/16
 *
 */
class XgameError(val msg : String = null, val err : Throwable = null) extends RuntimeException(msg, err) {
   /**
    * 类参数构造器
    *
    * @param err
    * @return
    *
    */
    def this(err : Throwable) = this(null, err)
}
