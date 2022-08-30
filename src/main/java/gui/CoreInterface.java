package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * description: 核心面板
 * @author KongJing E-mail:15072138321@163.com
 * @version V1.0 Just Do IT!
 */
public class CoreInterface extends JPanel {

	/**
	 * 核心面板
	 */
	private static final long serialVersionUID = -4893452654623304609L;
	/**
	 * 显示面板
	 */
	private final DisplayInterface displayInterface;
	/**
	 * 输入面板
	 */
	private final InputInterface inputInterface;

	/**
	 * 构造方法
	 * 
	 */
	public CoreInterface(DisplayInterface displayInterface, InputInterface inputInterface) {
		this.displayInterface = displayInterface;
		this.inputInterface = inputInterface;
		init();
	}

	private void init() {
		System.out.println("核心面板初始化成功");
		this.setLayout(new BorderLayout());
		addNode();
	}

	private void addNode() {
		this.add(BorderLayout.NORTH, displayInterface);
		this.add(BorderLayout.CENTER, inputInterface);
	}

}
