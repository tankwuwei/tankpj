package engine.util;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileWatcher {
	public static interface ReloadListener {
		void onReloaded(String fileName, String fileContent);

		void onDeleted(String fileName);
	}

	private String watchDir;
	private ReloadListener listener;
	private ExecutorService worker;
	private StringBuilder builder = new StringBuilder();
	private String fileExtension = ".js";

	private boolean recursive = true;
	private WatchService watcher;

	public FileWatcher(String dir, String fileExtension, ReloadListener listener) {
		this.watchDir = dir;
		this.fileExtension = fileExtension;
		this.listener = listener;
		worker = Executors.newCachedThreadPool();
		try {
			watcher = FileSystems.getDefault().newWatchService();
			registerAll(Paths.get(watchDir));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void startWatch() {
		worker.submit(new Runnable() {
			public void run() {
				watch();
			}
		});
	}

	private void reload(final String fileName) {
		try {
			builder.setLength(0);
			loadFile(fileName, builder);
			final String fileContent = builder.toString();
			worker.submit(new Runnable() {
				public void run() {
					try {
						listener.onReloaded(fileName, fileContent);
					} catch (Exception e) {
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void delete(final String fileName) {
		worker.submit(new Runnable() {
			public void run() {
				try {
					listener.onDeleted(fileName);
				} catch (Exception e) {
				}
			}
		});
	}

	private void loadFile(String name, StringBuilder builder) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(
					new FileReader(new File(watchDir, name)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e2) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void watch() {

		while (true) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException ex) {
				return;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				if (kind == StandardWatchEventKinds.OVERFLOW)
					continue;

				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				Path fileName = ev.context();
				String name = fileName.toString();
				if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
					delete(name);
				} else if (name.endsWith(fileExtension)) {
					reload(name);
				}
				if (recursive && (kind == ENTRY_CREATE)) {
					try {
						if (Files.isDirectory(fileName, NOFOLLOW_LINKS)) {
							registerAll(fileName);
						}
					} catch (IOException x) {
					}
				}
			}

			boolean valid = key.reset();
			if (!valid) {
				break;
			}
		}

	}

	private void registerAll(final Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
