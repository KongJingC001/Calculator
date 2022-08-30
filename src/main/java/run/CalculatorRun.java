package run;

import javax.swing.SwingUtilities;

import gui.CoreInterface;
import gui.DisplayInterface;
import gui.InputInterface;
import gui.MainInterface;
import loader.ButtonSetter;
import loader.HandleEvent;

/**
 * Description: 主运行器
 */
public class CalculatorRun {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			createGUI();
			System.out.println("界面启动成功！");
		});
		
	}

	/**
	 * 创建GUI
	 */
	private static void createGUI() {
		// 显示面板
		DisplayInterface displayInterface = new DisplayInterface();
		// 事件处理器
		HandleEvent handleEvent = new HandleEvent(displayInterface);
		// 按钮设置器
		ButtonSetter buttonSetter = new ButtonSetter(handleEvent);
		// 输入面板
		InputInterface inputInterface = new InputInterface(buttonSetter);
		// 核心面板
		CoreInterface coreInterface = new CoreInterface(displayInterface, inputInterface);
		// 主界面
		MainInterface mainInterface = new MainInterface(coreInterface);
		// 初始化
		mainInterface.init();
	}

}
