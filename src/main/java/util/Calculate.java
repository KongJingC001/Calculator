package util;

/**
 * Description: 计算表达式
 */
public abstract class Calculate {

	/**
	 * 得到计算结果
	 * 
	 */
	public static double getResult(String expression) {
		return calculate(suffix(expression));
	}

	/**
	 * 传入数值和运算符，计算表达式结果
	 * 
	 */
	public static double execute(double num1, double num2, char op) {
		double result = Double.MIN_VALUE;
		switch (op) {
		case Operator.ADD:
			result = num1 + num2;
			break;
		case Operator.SUBTRACT:
			result = num1 - num2;
			break;
		case Operator.MULTIPLY:
			result = num1 * num2;
			break;
		case Operator.DIVIDE:
			if (num2 == 0) {
				return result;
			}
			result = num1 / num2;
			break;
		}
		return result;
	}

	/**
	 * 中缀转后缀
	 * 
	 */
	public static String[] suffix(String expression) { // 将一个字符串式子转换为后缀表达式,前提是这个字符串式子的表达式合法
		char[] a = new char[10]; // a用来暂时存储数字
		String[] b = new String[99]; // b为式子的后缀表示数组
		char[] stack = new char[99]; // stack为符号栈
		int j = 0, k = 0, l = 0; // j为a下标,k为b下标,l为stack下标,j k l永远指向下一个空的位置
		for (int m = 0; m < 10; m++) // 清零a
			a[m] = 0;
		for (int i = 0; i < expression.length(); i++) { // 对字符串式子中字符依次提取出来
			if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9' || expression.charAt(i) == '.') { // 如果是数字或者.就被a暂时存储起来,遇到符号时a会被打包存入
				a[j] = expression.charAt(i);
				j = j + 1;
			} else if (expression.charAt(i) == Operator.ADD || expression.charAt(i) == Operator.SUBTRACT
					|| expression.charAt(i) == Operator.MULTIPLY || expression.charAt(i) == Operator.DIVIDE) // 如果是符号就进行对符号栈的相关操作
			{
				b[l] = String.valueOf(a); // 将a中字符打包放入b中
				l = l + 1;
				j = 0;
				for (int m = 0; m < 10; m++) // 清空数组a
					a[m] = 0;
				if (k == 0) { // 如果符号栈是空栈就直接入栈
					stack[k] = expression.charAt(i);
					k = k + 1;
				} else {
					if (Calculate.weight(stack[k - 1]) >= Calculate.weight(expression.charAt(i))) { // 否则如果改符号的权限不大于栈中符号
						k = k - 1;
						while (k != -1 && stack[k] != Operator.LEFT_BRACKET
								&& Calculate.weight(stack[k]) >= Calculate.weight(expression.charAt(i))) { // 直到遇到'('或者栈空
							b[l] = String.valueOf(stack[k]); // 或者栈顶符号权限大于该符号将一直循环出栈到b中
							l = l + 1;
							k = k - 1;
						}
						stack[k + 1] = expression.charAt(i); // 搞完后把字符串式子的符号入栈
						k = k + 2;
					} else {
						stack[k] = expression.charAt(i); // 不满足上面那个if就直接入栈
						k = k + 1;
					}
				}

			} else if (expression.charAt(i) == Operator.LEFT_BRACKET) { // 如果遇到'('
				stack[k] = expression.charAt(i); // 直接入栈
				k = k + 1;
			} else if (expression.charAt(i) == Operator.RIGHT_BRACKET) { // 如果遇到')'
				b[l] = String.valueOf(a); // 把之前a中的数字打包存入b
				l = l + 1;
				j = 0;
				for (int m = 0; m < 10; m++) // 清空a
					a[m] = 0;
				k = k - 1;
				while (stack[k] != Operator.LEFT_BRACKET) { // 直到遇到'(' 一直出栈
					b[l] = String.valueOf(stack[k]);
					l = l + 1;
					k = k - 1;
				}
				stack[k] = 0; // 把'('出栈
			}
		}
		b[l] = String.valueOf(a); // 下面是把原来a中残留打包存入b
		l = l + 1;
		k = k - 1;
		while (k != -1) { // stack中残留出栈并存入b
			b[l] = String.valueOf(stack[k]);
			l = l + 1;
			k = k - 1;
		}
		return b;
	}

	/**
	 * 运算符权重
	 * 
	 */
	public static int weight(char a) {
		int b = 0;
		switch (a) {
		case Operator.ADD:
		case Operator.SUBTRACT:
			b = 1;
			break;
		case Operator.MULTIPLY:
		case Operator.DIVIDE:
			b = 2;
			break;
		}
		return b;

	}

	/**
	 * 后缀表达式计算
	 * 
	 */
	public static double calculate(String[] Suffix) // 根据string数组的后缀串计算 ,规则是根据后缀串遇到数字就存入栈
	{ // 遇到符号就把栈头两个数字做运算,注意顺序,然后再存入,直到遍历完string数组
		int count = 0;
		int i = 0;
		double[] a = new double[99];
		while (Suffix[count] != null) {
			if (Suffix[count].charAt(0) >= '0' && Suffix[count].charAt(0) <= '9') {
				a[i] = Double.parseDouble(Suffix[count]);
				i = i + 1;
			} else {
				switch (Suffix[count].charAt(0)) {
				case Operator.ADD:
					a[i - 2] = execute(a[i - 2], a[i - 1], Operator.ADD);
					i = i - 1;
					break;
				case Operator.SUBTRACT:
					a[i - 2] = execute(a[i - 2], a[i - 1], Operator.SUBTRACT); // 出栈后的运算规则是后出的运算先出的
					i = i - 1;
					break;
				case Operator.DIVIDE:
					a[i - 2] = execute(a[i - 2], a[i - 1], Operator.DIVIDE);
					i = i - 1;
					break;
				case Operator.MULTIPLY:
					a[i - 2] = execute(a[i - 2], a[i - 1], Operator.MULTIPLY); // 出栈后的运算规则是后出的运算先出的
					i = i - 1;
					break;
				}
			}
			count = count + 1;
		}
		return a[0];
	}

}
