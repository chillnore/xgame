package war2.autocode.impl.excel.work;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import war2.autocode.codedom.FieldTypeEnum;
import war2.autocode.codedom.constraints.IFieldConstraints;
import war2.autocode.impl.excel.model.ExcelBaseTmplClazz;
import war2.autocode.impl.excel.model.ExcelBaseTmplField;
import war2.autocode.impl.excel.model.MultiLangAnnotation;
import war2.autocode.impl.excel.xmlschema.BaseTmplXml;
import war2.autocode.impl.excel.xmlschema.ConstraintsConverter;
import war2.autocode.impl.excel.xmlschema.ExcelBaseTmplXml;
import war2.autocode.impl.excel.xmlschema.FieldXml;
import war2.autocode.kernal.Logger;


/**
 * 加载表定义
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 45 $
 *
 */
class ParseXmlWork extends AbstractExcelWork {
	/** MultiLang 注解 */
	private static final String MULTI_LANG_ANNOTATION = "com.xgame.server.template.MultiLang";
	/** 键值对枚举 - key */
	private static final String KEY_VALUE_PAIR_ENUM_KEY = "static com.xgame.server.KeyValuePairEnum.key";
	/** 键值对枚举 - val */
	private static final String KEY_VALUE_PAIR_ENUM_VAL = "static com.xgame.server.KeyValuePairEnum.val";

	@Override
	protected void workStart(WorkParams params) {
		if (params == null) {
			return;
		}

		// 输出日志信息
		Logger.log("解析 " + params.getXmlFileName());

		try {
			// 获取根节点
			ExcelBaseTmplXml xmlroot = this.loadFrom(params.getXmlFileName());
			// 分析根节点
			List<ExcelBaseTmplClazz> etcl = this.parseRoot(xmlroot);
			// 设置 Excel 模板类列表
			params.setExcelBaseTmplClazzList(etcl);
		} catch (Exception ex) {
			// 将异常抛出
			throw new RuntimeException(params.getXmlFileName() + "解析错误!", ex);
		}
	}

	/**
	 * 从文件中加载 excelBaseTmpl 节点
	 * 
	 * @param xmlFileName 
	 * @return
	 */
	private ExcelBaseTmplXml loadFrom(String xmlFileName) {
		if (xmlFileName == null) {
			return null;
		}

		Registry registry = new Registry();
		Strategy strategy = new RegistryStrategy(registry);

		// 创建序列化器
		Serializer serializer = new Persister(strategy);

		try {
			// 绑定转换器
			registry.bind(IFieldConstraints.class, ConstraintsConverter.class);
			// 读取文件并解析
			return serializer.read(ExcelBaseTmplXml.class, new File(xmlFileName));
		} catch (Exception ex) {
			// 抛出运行时异常
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 解析根标记
	 * 
	 * @param xmlroot
	 * @param param
	 * @throws Exception
	 */
	private List<ExcelBaseTmplClazz> parseRoot(ExcelBaseTmplXml xml) {
		if (xml == null) {
			return Collections.emptyList();
		}

		// 获取包名称
		String packageName = xml.getPackageName();
		// 创建 excelTemplateClazz 列表
		List<ExcelBaseTmplClazz> etcl = new ArrayList<ExcelBaseTmplClazz>();

		for (BaseTmplXml btx : xml.getBaseTemplList()) {
			// 解析模板类
			ExcelBaseTmplClazz clazz = this.parseBaseTmpl(btx);
			// 设置类所属包
			clazz.setPackageName(packageName);
			// 添加模板类
			etcl.add(clazz);
		}

		return etcl;
	}

	/**
	 * 解析 template 标记
	 * 
	 * @param btx
	 * @return
	 * 
	 */
	private ExcelBaseTmplClazz parseBaseTmpl(BaseTmplXml btx) {
		if (btx == null) {
			return null;
		}

		// 创建 Excel 模板类
		ExcelBaseTmplClazz etc = new ExcelBaseTmplClazz();
		// 设置 Excel 工作表单名称
		etc.setSheet(btx.getSheet());

		// 创建字段列表
		List<ExcelBaseTmplField> fl = new ArrayList<ExcelBaseTmplField>();

		for (FieldXml fx : btx.getFieldList()) {
			// 解析为字段
			ExcelBaseTmplField f = this.parseField(fx);

			if (f.isMultiLangField()) {
				// 添加 multiLang 注解
				etc.addImport(MULTI_LANG_ANNOTATION);
				// 添加键值对枚举
				etc.addImport(KEY_VALUE_PAIR_ENUM_KEY);
				etc.addImport(KEY_VALUE_PAIR_ENUM_VAL);
			}

			fl.add(f);
		}

		// 设置字段列表
		etc.setFieldList(fl);
		return etc;
	}

	/**
	 * 解析 field 标记
	 * 
	 * @param fx
	 * @return
	 */
	private ExcelBaseTmplField parseField(FieldXml fx) {
		// 创建类字段
		ExcelBaseTmplField f = new ExcelBaseTmplField();

		f.setColumnName(fx.getColumn());
		f.setType(FieldTypeEnum.parse(fx.getType()));
		f.setNullable(fx.isNullable());

		if (fx.isMultiLangField()) {
			// 设置多语言注解
			f.setMultiLangAnnotation(
				new MultiLangAnnotation(fx.getMultiLang()));
		}

		if (fx.hasConstraints()) {
			// 设置约束列表
			f.setConstraintsList(fx.getConstraintsList());
		}

		return f;
	}
}
