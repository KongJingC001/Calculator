package loader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;

import util.Operator;
import util.WidgetStyle;

/**
 * description: 按钮设置器
 */
public class ButtonSetter {

	private final HandleEvent handleEvent;

	private ArrayList<JButton> calcList, numList, controlList;

	public ButtonSetter(HandleEvent handleEvent) {
		this.handleEvent = handleEvent;
		init();
	}

	/**
	 * 初始化按钮
	 */
	private void init() {
		setControlButton();
		setNumberButton();
		setCalculateButton();
	}

	/**
	 * 设置计算按钮：÷ × + - =
	 */
	private void setCalculateButton() {
		calcList = new ArrayList<>();
		addButton(Operator.DIVIDE, calcList);
		addButton(Operator.MULTIPLY, calcList);
		addButton(Operator.SUBTRACT, calcList);
		addButton(Operator.ADD, calcList);
		addButton("=", calcList);
		for (JButton jButton : calcList) {
			jButton.addActionListener(new CalcListener());
			if (jButton.getText().equals("=")) {
				jButton.setBackground(WidgetStyle.Equal_Button_Background);
				continue;
			}
			jButton.setBackground(WidgetStyle.Control_Calc_Button_Background);
		}
	}

	/**
	 * 设置数值按钮：0 - 9 . %
	 */
	private void setNumberButton() {
		numList = new ArrayList<>();
		addButton("7", numList);
		addButton("8", numList);
		addButton("9", numList);
		addButton("4", numList);
		addButton("5", numList);
		addButton("6", numList);
		addButton("1", numList);
		addButton("2", numList);
		addButton("3", numList);
		addButton(".", numList);
		addButton("0", numList);
		addButton("%", numList);
		for (JButton jButton : numList) {
			jButton.addActionListener(new NumListener());
			jButton.setBackground(WidgetStyle.Num_Button_Background);
		}
	}

	/**
	 * 设置控制按钮 ( ) C
	 */
	private void setControlButton() {
		controlList = new ArrayList<>();
		addButton(Operator.LEFT_BRACKET, controlList);
		addButton(Operator.RIGHT_BRACKET, controlList);
		addButton("C", controlList);
		for (JButton jButton : controlList) {
			jButton.addActionListener(new ControlListener());
			jButton.setBackground(WidgetStyle.Control_Calc_Button_Background);
		}
	}

	/**
	 * 添加按钮到集合中
	 * 
	 */
	private void addButton(String text, ArrayList<JButton> list) {
		JButton button = new JButton(text);
		button.setFont(WidgetStyle.Button_Font);
		list.add(button);
	}

	/**
	 * 添加按钮到集合中
	 * 
	 */
	private void addButton(char ch, ArrayList<JButton> list) {
		JButton button = new JButton(String.valueOf(ch));
		button.setFont(WidgetStyle.Button_Font);
		list.add(button);
	}

	/**
	 * 像集合中添加所有按钮
	 * 
	 */
	public void load(ArrayList<JButton> buttonList) {
		buttonList.addAll(controlList);
		buttonList.add(calcList.get(0));
		Iterator<JButton> iter = numList.iterator();
		for (int i = 1; i <= 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (j < 3) {
					buttonList.add(iter.next());
				} else {
					buttonList.add(calcList.get(i));
				}
			}
		}
	}

	/**
	 * description: 数字按钮监视器，数值按钮：0 - 9 . %
	 */
	private class NumListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.print("数字按钮被触发");
			JButton srcButton = (JButton) e.getSource();
			System.out.println(srcButton.getText());
			handleEvent.display(srcButton.getText());
		} // 关闭actionPerformed()方法

	} // 关闭NumListener内部类

	/**
	 * Description: 计算按钮监视器，计算按钮：÷ × + - =

	 */
	private class CalcListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.print("计算按钮被触发");
			JButton srcButton = (JButton) e.getSource();
			System.out.println(srcButton.getText());
			// 处理事件
			if ("=".equals(srcButton.getText())) {
				handleEvent.showResult();
			} else {
				handleEvent.display(srcButton.getText());
			}
		} // 关闭actionPerformed()方法

	} // 关闭CalcListener内部类

	/**
	 * Description: 控制按钮监视器，控制按钮 ( ) C

	 */
	private class ControlListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.print("控制按钮被触发");
			JButton srcButton = (JButton) e.getSource();
			System.out.println(srcButton.getText());
			// 处理事件
			if ("C".equals(srcButton.getText())) {
				handleEvent.clear();
			} else {
				handleEvent.display(srcButton.getText());
			}
		} // 关闭actionPerformed()方法

	} // 关闭ControlListener内部类

}
