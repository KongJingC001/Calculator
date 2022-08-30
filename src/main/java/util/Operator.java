package util;

/**
 * Description: 运算符

 */
public abstract class Operator {
	/**
	 * 加法运算符
	 */
	public static final char ADD = '+';
	/**
	 * 减法运算符
	 */
	public static final char SUBTRACT = '-';
	/**
	 * 乘法运算符
	 */
	public static final char MULTIPLY = '×';
	/**
	 * 除法运算符
	 */
	public static final char DIVIDE = '÷';
	/**
	 * 左括号
	 */
	public static final char LEFT_BRACKET = '(';
	/**
	 * 右括号
	 */
	public static final char RIGHT_BRACKET = ')';

	/**
	 * 判断是否是一个运算符
	 */
	public static boolean isOperator(char ch) {
		return ch == Operator.ADD || ch == Operator.SUBTRACT || ch == Operator.MULTIPLY || ch == Operator.DIVIDE;
	}
}
