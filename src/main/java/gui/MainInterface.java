package gui;

import javax.swing.JFrame;

/**
 * description: 主面板
 */
public class MainInterface extends JFrame {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7096512789109828724L;

	/**
	 * 核心面板
	 */
	private final CoreInterface coreInterface;

	/**
	 * 构造函数
	 */
	public MainInterface(CoreInterface coreInterface) {
		this.coreInterface = coreInterface;
	}

	/**
	 * 初始化界面
	 */
	public void init() {
		this.setTitle("一个简单的计算器");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 500);
		this.setLocationRelativeTo(null);
		addNode();
		this.setVisible(true);
	}

	private void addNode() {
		this.getContentPane().add(coreInterface);
	}

}
