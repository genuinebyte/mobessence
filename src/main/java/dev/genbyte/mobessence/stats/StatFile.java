package dev.genbyte.mobessence.stats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.logging.Level;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import dev.genbyte.mobessence.MobEssence;

public class StatFile extends BukkitRunnable {
	private final MobEssence me;
	private BukkitTask task;

	private final int[] numbers;
	private int totalNumbers;
	private final String fname;

	public StatFile(MobEssence me) {
		this.me = me;

		numbers = new int[me.dropChance];
		for (int i = 0; i < numbers.length; ++i) {
			numbers[i] = 0;
		}

		fname = "stats_" + me.dropChance + ".csv";

		loadFile();
	}

	public void logNumber(int n) {
		numbers[n]++;
		totalNumbers++;
	}

	public int getTotal() {
		return totalNumbers;
	}

	public int getTotalHits() {
		return numbers[me.dropChance-1];
	}

	public Stat[] getSectionalNumbers() {
		int sectionCount = 4;
		int sectionBreak = Math.floorDiv(me.dropChance, sectionCount);
		int[] sections = new int[sectionCount+1];
		
		sections[0] = 0;
		for (int i = 1; i < sectionCount; ++i) {
			sections[i] = sectionBreak*i;
		}
		sections[sectionCount] = me.dropChance;

		Stat[] sectionTotals = new Stat[sectionCount];
		for (int i = 1; i < sections.length; ++i) {
			int sectionTotal = 0;
			for (int j = sections[i-1]; j < sections[i]; j++) {
				sectionTotal += numbers[j];
			}
			sectionTotals[i-1] = new Stat(sections[i-1], sections[i], sectionTotal);
		}

		return sectionTotals;
	}

	private Optional<File> getFile() {
		File file = new File(me.getDataFolder(), fname);
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException | SecurityException ex) {
				ex.printStackTrace();
				return Optional.empty();
			}
		}

		return Optional.of(file);
	}

	private void loadFile() {
		Optional<File> fopt = getFile();
		if (!fopt.isPresent()) {
			printSaveError();
			return;
		}

		try {
			FileReader freader = new FileReader(fopt.get());
			BufferedReader buffread = new BufferedReader(freader);

			String line;
			while ((line = buffread.readLine()) != null) {
				int delimLoc = line.indexOf(',');
				int index = Integer.parseInt(line.substring(0, delimLoc));
				int count = Integer.parseInt(line.substring(delimLoc+1));

				numbers[index] = count;
				totalNumbers += count;
			}
			buffread.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			printLoadError();
			return;
		} catch (NumberFormatException | IndexOutOfBoundsException ex) {
			ex.printStackTrace();
			me.getLogger().log(Level.SEVERE, "Stats file '" + fname + "' has formatting errors. How did this happen?");
			return;
		}
	}

	@Override
	public void run() {
		saveFile();
	}

	public void startSaveTask() {
		// Run the save task after a minute and then once every 10 minutes
		task = this.runTaskTimerAsynchronously(me, 20 * 60, 20 * 600);
		me.getLogger().log(Level.INFO, "Started stats file save timer");
	}

	public void stopSaveTask() {
		if (task != null) {
			task.cancel();
			task = null;
			me.getLogger().log(Level.INFO, "Stopped stats file save timer");
		}
	}

	private void saveFile() {
		Optional<File> fopt = getFile();
		if (!fopt.isPresent()) {
			printSaveError();
			return;
		}

		try {
			FileWriter fwriter = new FileWriter(fopt.get());
			PrintWriter pw = new PrintWriter(fwriter);
			
			for (int i = 0; i < numbers.length; ++i) {
				pw.println(i + "," + numbers[i]);
			}

			pw.close();
			me.getLogger().log(Level.INFO, "Saved stats file");
		} catch (IOException ex) {
			ex.printStackTrace();
			printSaveError();
			return;
		}
	}

	private void printSaveError() {
		me.getLogger().log(Level.SEVERE, "Could not save stats file!");
	}

	private void printLoadError() {
		me.getLogger().log(Level.SEVERE, "Could open stats file!");
	}

	public class Stat {
		public int start;
		public int end;
		public int number;

		public Stat(int s, int e, int n) {
			start = s;
			end = e;
			number = n;
		}
	}
}