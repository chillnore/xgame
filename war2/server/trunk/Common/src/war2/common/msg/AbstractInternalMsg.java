package war2.common.msg;

/**
 * 内部消息
 * 
 * @author hjj2017
 * 
 */
public abstract class AbstractInternalMsg extends AbstractMsg {
	/** 内部消息类型 ID */
	static final short INTERNAL_MSG_TYPE_ID = -1024;
	
	@Override
	public final short getMsgTypeID() {
		return INTERNAL_MSG_TYPE_ID;
	}

	/**
	 * 自执行函数
	 * 
	 */
	public abstract void execute();
}
