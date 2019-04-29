import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class OpenDoorsBot {

	private Field position = null;

	public static void main(String[] args) throws AWTException, IOException {
		OpenDoorsBot instance = new OpenDoorsBot();
		// steps = instance.getStepsForLevel(1);

		Queue<Integer> steps = instance.getStepsForLevel(9);
		instance.startWalking(steps);
	}

	private void startWalking(Queue<Integer> steps) throws IOException, AWTException {
		// Runtime.getRuntime().exec(new String[] { "\"C:\\Program Files\\Mozilla
		// Firefox\\firefox.exe\"",
		// "https://armorgames.com/play/3357/open-doors-2?rt=r" });

		Robot robot = new Robot();
		robot.delay(5000);

		for (int step : steps) {
			robot.keyPress(step);
			robot.delay(300);
			robot.keyRelease(step);
			robot.delay(500);
		}
	}

	private Queue<Integer> getStepsForLevel(int planNumber) {

		File planFile = new File("./Plans/sas_plan" + planNumber + ".txt");
		BufferedReader reader;
		Queue<Integer> commandList = new LinkedList<>();
		try {
			reader = new BufferedReader(new FileReader(planFile));

			String command = "";
			String from = "";
			String to = "";

			while ((command = reader.readLine()) != null) {

				if (command.contains("walk_over_open_door")) {
					from = command.substring(30, 32);
					to = command.substring(26, 28);
				} else if (command.contains("walk_over_door_angle_from_inside")) {
					from = command.substring(39, 41);
					to = command.substring(43, 45);
				} else if (command.contains("walk_free") || command.contains("open_door")) {
					from = command.substring(12, 14);
					to = command.substring(16, 18);
				} else if (command.contains("open_gate")) {
					from = command.substring(12, 14);
					to = command.substring(16, 18);
				} else if (command.contains("close_gate")) {
					from = command.substring(13, 15);
					to = command.substring(17, 19);
				}

				System.out.println(from + " " + to);
				commandList.add(calcDirection(from, to));
			}
		} catch (

		IOException e) {
			System.out.println("Error while reading from file");
			e.printStackTrace();
		}
		return commandList;
	}

	private int calcDirection(String from, String to) {

		Field fromField = new Field(Integer.parseInt(from.substring(0, 1)), Integer.parseInt(from.substring(1)));

		Field toField = new Field(Integer.parseInt(to.substring(0, 1)), Integer.parseInt(to.substring(1)));

		int direction;
		if (fromField.x < toField.x) {
			direction = KeyEvent.VK_RIGHT;
		} else if (fromField.x > toField.x) {
			direction = KeyEvent.VK_LEFT;
		} else if (fromField.y < toField.y) {
			direction = KeyEvent.VK_UP;
		} else {
			direction = KeyEvent.VK_DOWN;
		}

		return direction;
	}

}
