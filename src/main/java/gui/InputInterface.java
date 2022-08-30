package gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import loader.ButtonSetter;

/**
 * Description: 输入区面板
 */
public class InputInterface extends JPanel {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7745187496875665506L;

	private final ButtonSetter buttonSetter;

	private final ArrayList<JButton> buttonList;

	/**
	 * 构造方法
	 */
	public InputInterface(ButtonSetter buttonSetter) {
		buttonList = new ArrayList<>();
		this.buttonSetter = buttonSetter;
		init();
	}

	private void init() {
		GridLayout grid = new GridLayout(5, 4, 1, 1);
		this.setLayout(grid);
		addNode();
	}

	private void addNode() {
		// 读取所有按钮
		buttonSetter.load(buttonList);
		// 将按钮加在面板上
		for (JButton jButton : buttonList) {
			this.add(jButton);
		}
	}

}
