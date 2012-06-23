package war2.common.msg;

/**
 * 消息行为, 向下调用 Service 类
 * 
 * @author hjj2017
 *
 */
public interface IMsgAction<TMsg extends AbstractMsg> {
	/**
	 * 执行消息
	 * 
	 * @param msg
	 */
	void execute(TMsg msg);
}
