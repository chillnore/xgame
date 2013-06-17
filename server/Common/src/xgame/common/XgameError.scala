package xgame.common

/**
 * 游戏异常
 *
 * @author haijiang
 * @since 2013/6/16
 *
 */
class XgameError(val msg : String = null, val err : Throwable = null) extends RuntimeException(msg, err) {
}
