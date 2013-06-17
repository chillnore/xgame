package war2.autocode.codedom.constraints;

import java.util.Properties;

import war2.autocode.codedom.FieldTypeEnum;


/**
 * 空白约束
 * 
 * @author AfritXia
 *
 */
public class Blank implements IFieldConstraints {
	/** 对象 ID */
	private static int OBJECT_ID = 1001;

	/** ID */
	private int _id;

	/**
	 * 类默认构造器
	 * 
	 */
	public Blank() {
		this._id = OBJECT_ID++;
	}

	@Override
	public int getID() {
		return this._id;
	}

	@Override
	public FieldTypeEnum getFieldType() {
		return FieldTypeEnum.voidType;
	}

	@Override
	public void loadFrom(Properties props) {
		throw new UnsupportedOperationException();
	}
}
