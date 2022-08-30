package gui;

import javax.swing.JTextArea;

import util.WidgetStyle;

/**
 * Description: 显示面板
 * @version V1.0 Just Do IT!
 */
public class DisplayInterface extends JTextArea {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2012803619119644680L;

	public DisplayInterface() {
		System.out.println("显示面板初始化成功");
		this.setEditable(false);
		this.setRows(2);
		this.setColumns(30);
		this.setText("\n");
		this.setFont(WidgetStyle.Dis_Font);
	}
}
