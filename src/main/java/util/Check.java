package util;


public abstract class Check {
	/**
	 * 合法表达式
	 */
	public static final int LEGAL_EXPRESSION = 0;
	/**
	 * 空表达式错误
	 */
	public static final int Empty_ERROR = 1;
	/**
	 * 括号不对等错误
	 */
	public static final int NOT_EQUAL_ERROR = 2;
	/**
	 * 数字左为表达式错误
	 */
	public static final int DIGITAL_LEFT_EXPS_ERROR = 3;
	/**
	 * 数字右为表达式错误
	 */
	public static final int DIGITAL_RIGHT_EXPS_ERROR = 4;
	/**
	 * 运算符开头错误
	 */
	public static final int SYMBOL_START_ERROR = 5;
	/**
	 * 运算符结尾错误
	 */
	public static final int SYMBOL_END_ERROR = 6;
	/**
	 * 运算符左边发生错误
	 */
	public static final int SYMBOL_LEFT_ERROR = 7;
	/**
	 * 运算符右边发生错误
	 */
	public static final int SYMBOL_RIGHT_ERROR = 8;
	/**
	 * 数字多小数点错误
	 */
	public static final int MULTI_POINT_ERROR = 9;
	/**
	 * 小数点开头错误
	 */
	public static final int POINT_START_ERROR = 10;
	/**
	 * 数字代表字符
	 */
	public static final char DIGITAL_AGENCY = 'D';
	/**
	 * 表达式代表字符
	 */
	public static final char EXPRESSION_AGENCY = 'E';


	public static int isLegal(final String expression) {
		// 核对信息
		int checkInfo;
		// 去掉空格，顺便拿到一个副本
		String clone = expression.replace(" ", "");
		// 转换为格式化的字符串
		FormatInfo formatInfo = toFormat(clone);
		// 数字表达不合法
		if (formatInfo.result != LEGAL_EXPRESSION) {
			return formatInfo.result;
		}
		// 使用格式化过的字符串
		clone = formatInfo.str;
		// 表达式为空
		if (clone == null || clone.isEmpty()) {
			return Empty_ERROR;
		}
		// 将字符串转换为字符数组
		char[] exps = clone.toCharArray();
		int len = clone.length();
		// 判断表达式（）是否对等
		if (!isEquals(exps, len)) {
			return NOT_EQUAL_ERROR;
		}
		// 提取一对括号内的表达式，取得左右括号下标
		int[] index = getSubStringIndex(exps, len, clone.indexOf('('));
		int leftIndex = index[0];
		int rightIndex = index[1];
		// 存在子表达式
		if (leftIndex != -1 && rightIndex != -1) {
			// 判断子表达式是否合法， 记录错误信息
			checkInfo = isLegal(clone.substring(leftIndex + 1, rightIndex));
			if (checkInfo != LEGAL_EXPRESSION) {
				return checkInfo;
			}
			// 如果子串合法，将整个子串表达式用EXPRESSION_AGENCY替换，例如：(1 + 3 * (2 * 3)) -> (1 + 3 * E)
			clone = clone.substring(0, leftIndex).concat(EXPRESSION_AGENCY + clone.substring(rightIndex + 1));
			// 表达式为空，即已判断完毕，未出现错误
			if (clone.length() == 1) {
				return LEGAL_EXPRESSION;
			}
			// 更新数组
			exps = clone.toCharArray();
			len = clone.length();
		}
		// 判断非含括号表达式是否合法，所有括号已经遍历完毕
		// 该表达式以运算符开头, ÷ × + -
		if (Operator.isOperator(exps[0])) {
			return SYMBOL_START_ERROR;
		}
		// 该表达式以运算符结尾
		if (Operator.isOperator(exps[len - 1])) {
			return SYMBOL_END_ERROR;
		}
		// 检验无括号表达式是否合法
		checkInfo = isBasicLegal(clone);
		return checkInfo;
	}


	private static FormatInfo toFormat(String str) {
		// 将字符串转换为字符数组
		final char[] exps = str.toCharArray();
		final int len = str.length();
		// 清空字符串
		str = "";
		// 开始遍历
		for (int i = 0; i < len; i++) {
			// 以小数点开始，一定为错误表达式
			if (exps[i] == '.') {
				return new FormatInfo(POINT_START_ERROR, null);
			}
			// 第i个下标处是一个数字，且后续存在符号，开始截取数字
			if (exps[i] <= '9' && exps[i] >= '0') {
				// 向字符串中写入N
				str += DIGITAL_AGENCY;
				// 记录小数点个数，用于判断是否数字格式错误
				int points = 0;
				// 开始遍历
				for (i++; i < len; i++) {
					// 如果是非数字部分，直接结束遍历
					if (!(exps[i] <= '9' && exps[i] >= '0' || exps[i] == '.')) {
						break;
					} else {
						// 如果该数字部分是小数点，累计小数点个数
						if (exps[i] == '.') {
							points++;
						}
					}
					// 该数字部分有两个以上的点
					if (points > 1) {
						return new FormatInfo(MULTI_POINT_ERROR, null);
					}
				}
			} // 关闭if语句
				// 如果已经遍历完毕，直接结束循环
			if (i == len) {
				break;
			}
			// 记录非数字部分
			str += exps[i];
		}
		// 正常转换，返回全部信息
		return new FormatInfo(LEGAL_EXPRESSION, str);
	}


	static class FormatInfo {
		int result;
		String str;

		public FormatInfo(int result, String str) {
			super();
			this.result = result;
			this.str = str;
		}

	}

	/**
	 * 判断基本表达式是否合法，该表达式是一个经过格式化且无括号的表达式
	 * 
	 */
	public static int isBasicLegal(final String expression) {
		// 将字符串转换为字符数组
		char[] exps = expression.toCharArray();
		int len = expression.length();
		// 判断无括号表达式是否合法
		for (int i = 0; i < len; i++) {
			// 一个运算符两边都应该是数字或表达式
			if (Operator.isOperator(exps[i])) {
				// 判断左边
				if (!(exps[i - 1] == DIGITAL_AGENCY || exps[i - 1] == EXPRESSION_AGENCY)) {
					return SYMBOL_LEFT_ERROR;
				}
				// 判断右边
				if (!(exps[i + 1] == DIGITAL_AGENCY || exps[i + 1] == EXPRESSION_AGENCY)) {
					return SYMBOL_RIGHT_ERROR;
				}
			}
			// 一个数字两边不能存在表达式
			if (exps[i] == DIGITAL_AGENCY) {
				// 防止数组下标越界，判断左边
				if (i - 1 >= 0 && exps[i - 1] == EXPRESSION_AGENCY)
					return DIGITAL_LEFT_EXPS_ERROR;
				// 防止数组下标越界，判断右边
				if (i + 1 <= len - 1 && exps[i + 1] == EXPRESSION_AGENCY) {
					return DIGITAL_RIGHT_EXPS_ERROR;
				}
			}
		}
		return LEGAL_EXPRESSION;
	}

	/**
	 * 得到字符串中的一组对应括号下标
	 * 
	 */
	private static int[] getSubStringIndex(char[] exps, int len, int leftIndex) {
		int[] index = new int[2];
		// 提取一对括号内的表达式，取得左右括号下标
		index[0] = leftIndex;
		index[1] = -1;
		// 如果存在括号表达式
		if (index[0] != -1) {
			// 记录左右括号个数
			int left = 0, right = -1;
			// 从左括号右边开始访问
			for (int i = index[0] + 1; i < len; i++) {
				if (exps[i] == '(') {
					left++;
				}
				if (exps[i] == ')') {
					index[1] = i;
					right++;
				}
				// 寻求对应的括号
				if (left == right)
					break;
			}
		}
		return index;
	}

	/**
	 * 判断字符串表达式中括号数是否对等
	 * 
	 */
	private static boolean isEquals(char[] exps, int len) {
		// 首先判断括号对等情况
		int left = 0;
		int right = 0;
		for (int i = 0; i < len; i++) {
			if (exps[i] == '(') {
				left++;
			} else if (exps[i] == ')') {
				right++;
			}
			// 一旦左括号数小于右括号数，表达式一定不合法
			if (left < right)
				return false;
		}
		// 左右括号数不对等
		return left == right;
	}

}
