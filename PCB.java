/***
 * 
 * Roaa Alnader , Ebtisam Alsomali ,Meral Homoud
 * 
 ***/

public class PCB {
	static int count = 0; // counts
	static final int quantum = 4;
	int id = 0;
	int priority;
	int CPUburst;
	int startT;
	int endT;
	int turnT;
	int WaitingT;
	int responseTime;
	int time = 0;
	int arriveTime = 0; // all processes same arrive time

	int executionT[]; // array to save execution for each process at index[0] for p0 and ... index[n]
						// for pn

	public PCB() {
		priority = 0;
		CPUburst = 0;
		startT = 0;
		endT = 0;
		turnT = 0;
		WaitingT = 0;
		id = count;
		count++;

	}

	public PCB(int priority, int cPUburst, int startT, int endT, int turnT, int waitingT) {
		this.priority = priority;
		CPUburst = cPUburst;
		this.startT = startT;
		this.endT = endT;
		this.turnT = turnT;
		WaitingT = waitingT;

	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) throws Exception {
		if (priority > 10 || priority < 0)
			throw new Exception("Out of range you should enter priority from 0 to 10"); // msg to user for invalid
																						// priority range
		this.priority = priority;
	}

	public int getCPUburst() {
		return CPUburst;
	}

	public void setCPUburst(int cPUburst) {
		CPUburst = cPUburst;
	}

	public int getStartT() {
		return startT;
	}

	public void setStartT(int startT) {
		this.startT = startT;
	}

	public int getEndT() {
		return endT;
	}

	public void setEndT(int endT) {
		this.endT = endT;
	}

	public int getTurnT() {
		return turnT;
	}

	public void setTurnT(int turnT) {
		this.turnT = turnT;
	}

	public int getWaitingT() {
		return WaitingT;
	}

	public void setWaitingT(int waitingT) {
		WaitingT = waitingT;
	}

	public String processInfo() {
		return "ID = P" + id + ", priority=" + priority + ", CPU burst=" + CPUburst;
	}

	public String timeInfo() {
		return "ID = P" + id + " , start time =" + startT + ", termination time=" + endT + ", turn around time=" + turnT
				+ ", waiting time=" + WaitingT + ", response time=" + responseTime;
	}

	// schedules processes
	public void PS(PCB processes[]) {
		executionT = new int[processes.length];

		int i = 0;
		boolean flag = false; // flag true = when they same priority // false = different priority

		int last = processes.length - 1; // index last element in array

		// check if they equal priority
		while (i < processes.length && processes[i].CPUburst != 0) { // not include last element
			if (i < processes.length - 1 && processes[i].priority == processes[i + 1].priority) {
				RR(processes[i], i); // GO TO round robin
				flag = true; // change flag TO True
			}
			// check if last element has equal priority
			if (processes.length != 1) // if just one element in array dn't need do Line 123
				if (i == processes.length - 1 && processes[processes.length - 1].priority == processes[processes.length - 2].priority) {
					RR(processes[last], last); // GO TO round robin
					flag = true;// change flag TO True
				}

			if (flag == false) { // if they not equal priority
				processes[i].setStartT(time);
				processes[i].responseTime = time;
				time += processes[i].CPUburst;

				processes[i].setEndT(time);
				processes[i].setTurnT(processes[i].endT);
				processes[i].setWaitingT(processes[i].turnT - processes[i].CPUburst);
				executionT[i] = processes[i].endT - processes[i].startT; // save execution for every process

			}

			i++;
		}

		// ******** cheak didnt finish ********
		for (int j = 0; j < processes.length; j++) {
			boolean finish = true; // to exite from loop if all processes finished Line 149
			if (executionT[j] < processes[j].CPUburst) {
				finish = false;
				cheakRR(processes[j], j, processes); // if did not finish then GO To cheakRR to complete execute
			}
			if (j == processes.length - 1 && finish == false) // back to loop if process didn't finish
				j = -1;
		}

	}

	public void RR(PCB process, int i) {

		process.setStartT(time);
		process.responseTime = time;

		int count = 0;

		while (executionT[i] < process.CPUburst && count < quantum) {
			executionT[i]++;
			count++; // = how much we increase execution time

		}
		time += count;

		process.setEndT(time);
		process.setTurnT(process.endT - arriveTime);
		process.setWaitingT(process.turnT - process.CPUburst);

	}

	public void cheakRR(PCB p, int i, PCB process[]) {
		// >>>******** this case for processe didn't finish burst time ********<<<
		int count = 0;

		while (executionT[i] < p.CPUburst && count < quantum) {
			executionT[i]++;
			count++; // = how much we increase execution time

		}
		time += count;
		p.setEndT(time);
		p.setTurnT(p.endT - arriveTime); // termination time - arrival time
		p.setWaitingT(p.turnT - p.CPUburst);

	}

	public String average(PCB processes[]) {
		int TAT = 0;// turnaround time
		int WT = 0;// waiting time
		int RT = 0;// response time
		int size = processes.length;

		for (int i = 0; i < processes.length; i++) {
			TAT += processes[i].turnT;
			WT += processes[i].WaitingT;
			RT += processes[i].responseTime;
		}
		return "the average for turnaround time : " + TAT / size + "\n" + "the average for waiting time : " + WT / size
				+ "\n" + "the average for response time : " + RT / size + "\n";
	}

}