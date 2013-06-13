package war2.autocode.codedom.ic;

import java.util.List;

import war2.autocode.codedom.CustomClazz;


/**
 * 自定义类中间码
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public abstract class AbstractCustomClazzCode<TCustomClazz extends CustomClazz<?>> implements ICustomClazzCode {
	/** 自定义类 */
	private TCustomClazz _customClazz = null;

	/**
	 * 获取自定义类
	 * 
	 * @return
	 */
	public TCustomClazz getCustomClazz() {
		return this._customClazz;
	}

	/**
	 * 设置自定义类
	 * 
	 * @param value
	 */
	public void setCustomClazz(TCustomClazz value) {
		this._customClazz = value;
	}

	@Override
	public String getPackageName() {
		if (this._customClazz == null) {
			return null;
		} else {
			return this._customClazz.getPackageName();
		}
	}

	@Override
	public List<String> getImportList() {
		if (this._customClazz == null) {
			return null;
		} else {
			return this._customClazz.getSortedImportList();
		}
	}

	@Override
	public String getClazzName() {
		if (this._customClazz == null) {
			return null;
		} else {
			return this._customClazz.getName();
		}
	}
}
