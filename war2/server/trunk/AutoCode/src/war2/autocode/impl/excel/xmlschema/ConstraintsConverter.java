package war2.autocode.impl.excel.xmlschema;

import java.util.Properties;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;

import war2.autocode.codedom.constraints.IFieldConstraints;
import war2.autocode.utils.StringUtil;


/**
 * 约束转换器
 * 
 * @author AfritXia
 * @version $Rev: 0 $
 * @since 2011/12/17
 * 
 */
public class ConstraintsConverter implements Converter<IFieldConstraints> {
	/** 约束包名称 */
	private static final String CONSTRAINTS_PACKAGE_NAME = "com.xgame.autocode.codedom.constraints";

	@Override
	public IFieldConstraints read(InputNode n) 
		throws Exception {
		if (n == null) {
			return null;
		}

		// 将类名称首字母大写
		String fullName = "";

		fullName += CONSTRAINTS_PACKAGE_NAME;
		fullName += ".";
		fullName += StringUtil.firstCharToUpperCase(n.getName());
		
		// 创建类对象
		Class<?> clazz = Class.forName(fullName);
		// 创建约束对象
		IFieldConstraints fc = (IFieldConstraints)clazz.newInstance();

		// 创建属性字典
		Properties props = this.createProps(n);

		if (props != null) {
			// 从属性字典中还原约束
			fc.loadFrom(props);
		}

		return fc;
	}

	/**
	 * 根据读入节点, 创建属性字典
	 * 
	 * @param n
	 * @return
	 */
	private Properties createProps(InputNode n) {
		if (n == null) {
			return null;
		}

		// 获取 xml 属性字典
		NodeMap<InputNode> attrs = n.getAttributes();
		// 创建属性字段
		Properties props = new Properties();
		
		for (String key : attrs) {
			try {
				// 获取 xml 属性值
				String value = n.getAttribute(key).getValue();
				// 设置到属性字典
				props.put(key, value);
			} catch (Exception ex) {
				// 抛出运行时异常
				throw new RuntimeException(ex);
			}
		}

		return props;
	}

	@Override
	public void write(OutputNode n, IFieldConstraints fc)
		throws Exception {
		throw new UnsupportedOperationException();
	}
}
