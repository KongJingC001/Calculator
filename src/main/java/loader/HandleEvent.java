package loader;

import gui.DisplayInterface;
import util.Calculate;
import util.Check;

import java.lang.reflect.Field;

/**
 * Description: 处理事务
 */
public class HandleEvent {

	/**
	 * 显示面板
	 */
	private final DisplayInterface displayInterface;

	/**
	 * 用户输入的表达式
	 */
	private String expression;

	/**
	 * 构造方法
	 */
	public HandleEvent(DisplayInterface displayInterface) {
		this.displayInterface = displayInterface;
	}

	/**
	 * 将表达式输出到标签上
	 * 
	 */
	public void display(String ch) {
		if (expression == null) {
			expression = ch;
		} else {
			expression += ch;
		}
		displayInterface.setText(expression + "\n");
	}

	/**
	 * 得到计算结果
	 * 
	 */
	private String getResult(String expression) {
		double result = Double.MIN_VALUE;
		// 判断表达式是否合法
		if (isValidate(expression) == Check.LEGAL_EXPRESSION) {
			System.out.println("表达式合法");
			result = calc();
		} else {
			System.out.println("表达式不合法");
			// 利用反射获取变量名
			try {
				Field[] fields = Class.forName("util.Check").getDeclaredFields();
				return fields[isValidate(expression)].getName();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		return String.valueOf(result);
	}

	/**
	 * 计算结果
	 */
	private double calc() {
		return Calculate.getResult(expression);
	}

	/**
	 * 判断表达式是否合法
	 * 
	 */
	private int isValidate(String expression) {
		return Check.isLegal(expression);
	}

	/**
	 * 当用户按下“=”，显示结果
	 */
	public void showResult() {
		displayInterface.setText(displayInterface.getText() + "= " + getResult(expression));
	}

	/**
	 * 清空显示区域
	 */
	public void clear() {
		expression = null;
		displayInterface.setText("\n");
	}
}
