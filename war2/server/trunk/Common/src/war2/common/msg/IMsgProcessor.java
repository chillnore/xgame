package war2.common.msg;

/**
 * 消息处理器接口, 注意: 所有消息都是以队列方式处理!
 * 
 * @author haijiang
 * @since 2012/6/3 
 * 
 */
public interface IMsgProcessor {
	/**
	 * 令消息入队
	 * 
	 * @param msg
	 * 
	 */
	void enqueue(AbstractMsg msg);
}
