package war2.common.msg;

/**
 * 消息类型管理器
 * 
 * @author AfritXia
 * @since 2012/6/3
 *
 */
public interface IMsgTypeManager {
	/**
	 * 获取消息类
	 * 
	 * @param msgTypeID
	 * @return
	 */
	Class<? extends IMsg> getMsgClazz(short msgTypeID);
}
