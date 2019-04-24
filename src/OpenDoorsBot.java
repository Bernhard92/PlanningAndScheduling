import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenDoorsBot {

	static int keyInput[] = { KeyEvent.VK_J, KeyEvent.VK_A, KeyEvent.VK_V, KeyEvent.VK_A, KeyEvent.VK_SPACE,
			KeyEvent.VK_P, KeyEvent.VK_R, KeyEvent.VK_O, KeyEvent.VK_G, KeyEvent.VK_R, KeyEvent.VK_A, KeyEvent.VK_M,
			KeyEvent.VK_M, KeyEvent.VK_I, KeyEvent.VK_N, KeyEvent.VK_G, KeyEvent.VK_SPACE, KeyEvent.VK_F, KeyEvent.VK_O,
			KeyEvent.VK_R, KeyEvent.VK_U, KeyEvent.VK_M, KeyEvent.VK_S, KeyEvent.VK_SPACE, KeyEvent.VK_PERIOD,
			KeyEvent.VK_C, KeyEvent.VK_O, KeyEvent.VK_M };

	public static void main(String[] args) throws AWTException, IOException {
		OpenDoorsBot instance = new OpenDoorsBot(); 
		instance.parseInput(1);

		
	}
	
	
	private void startWalking() throws IOException, AWTException {
		Runtime.getRuntime().exec(new String[]{"\"C:\\Program Files\\Mozilla Firefox\\firefox.exe\"", "https://armorgames.com/play/3357/open-doors-2?rt=r"});
		//Runtime.getRuntime().exec("notepad");

		Robot robot = new Robot();

//		for (int i = 0; i < keyInput.length; i++) {
//
//			robot.keyPress(keyInput[i]);
//			robot.delay(100);
//
//		}
		
		robot.delay(20000);
		
		goRight(robot);
		goDown(robot);
		goDown(robot);
		goRight(robot);
		goRight(robot);
		goUp(robot);
		goRight(robot);
		goUp(robot);
		goRight(robot);
		goRight(robot);
		goDown(robot);
		goDown(robot);
		goRight(robot);
	}
	
	private void goRight(Robot robot) {
		System.out.println("Going R");
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(500);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		robot.delay(1000);
	}
	
	
	private void goLeft(Robot robot) {
		System.out.println("Going L");
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(500);
		robot.keyRelease(KeyEvent.VK_LEFT);
		robot.delay(1000);
	}
	
	private void goUp(Robot robot) {
		System.out.println("Going U");
		robot.keyPress(KeyEvent.VK_UP);
		robot.delay(500);
		robot.keyRelease(KeyEvent.VK_UP);
		robot.delay(1000);
	}

	private void goDown(Robot robot) {
		System.out.println("Going D");
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.delay(500);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.delay(1000);
}
	
	private void parseInput(int planNumber) {
		
		File planFile = new File("./Plans/sas_plan"+planNumber+".txt");
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(planFile));
			List<KeyEvent> commandList = new ArrayList<>(); 
			String from = "";
			String to = ""; 
			
			String command = ""; 
			while ((command = reader.readLine()) != null) {
				if (command.contains("walk_free")) {
					from = command.substring(12, 14); 
					to = command.substring(16, 18);
					
					int x1 = Integer.parseInt(from.charAt(0));
					int x2 = Integer.parseInt(from.charAt(1));
				}
				
				System.out.print(from + " " + to);
			}
		} catch (IOException e) {
			System.out.println("Error while reading from file");
			e.printStackTrace();
		}

		
	}

}
