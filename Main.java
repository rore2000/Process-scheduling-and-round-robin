

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static Scanner read = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {

		boolean menu = true;
		PCB[] array = null;
		PCB objPcb = new PCB();

		while (menu) {
			int choose;
			System.out.println("\nchoose number from menu");
			System.out.println(
					" 1. Enter process’ information.\n 2. Report detailed information about each process.\n 3. Report the average turnaround time, waiting time, and response time.\n "
							+ "4. Exit the program.\n");
			choose = read.nextInt();

			switch (choose) {

			case 1:
				System.out.print("Enter process number: ");
				int p = read.nextInt();
				array = new PCB[p];

				for (int i = 0; i < p; i++) {
					array[i] = new PCB();
					System.out.print("Enter priority number for process " + (i + 1) + ": ");
					int priority;

					while (true) {

						try {
							priority = read.nextInt();
							array[i].setPriority(priority);
							break;
						} catch (Exception e) {
							System.out.println(e.getMessage() + "\n");

						}
					}

					System.out.print("Enter CPU burst number for process " + (i + 1) + ": ");
					int CPUburst = read.nextInt();
					array[i].setCPUburst(CPUburst);

				}

				PCB temp = null;
				int len = array.length;
				// rearrange array from higher priority to lowest priority
				for (int i = 0; i < len - 1; i++) {
					for (int j = 0; j < (len - i) - 1; j++) {
						if (array[j].priority == array[j + 1].priority)
							continue;
						if (array[j].priority < array[j + 1].priority) {
							temp = array[j];
							array[j] = array[j + 1];
							array[j + 1] = temp;
						}
					}
				}

				break;

			case 2:
				objPcb.PS(array); // call method to schedules processes

				PrintWriter write = new PrintWriter("Report1.txt");
				FileOutputStream out = new FileOutputStream("Report1.txt");

				for (int i = 0; i < array.length; i++) {
					//System.out.println(array[i].processInfo());
					write.print("\n"+array[i].processInfo());
					//System.out.println(array[i].timeInfo());
					write.print("\n"+array[i].timeInfo() + "\n");
					//System.out.println("\n-----------------------------------------------------------------------------------------");
					write.print("\n-----------------------------------------------------------------------------------------");
				}
				write.close();
				break;

			case 3:

				PrintWriter write2 = new PrintWriter("Report2.txt");
				FileOutputStream out2 = new FileOutputStream("Report2.txt");

				//System.out.println("\n"+objPcb.average(array));
				write2.print("\n"+objPcb.average(array));
				write2.close();
				break;

			case 4:
				menu = false;
				break;

			default:
				System.out.println("Error, Wrong input! try agian");

			}

		}

	}

}
