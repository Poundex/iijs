package iijs

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Iijs {
	static void main(String[] args) {
		Path archive = Paths.get(System.getProperty("user.dir")).resolve(args[0])
		Path dir = Files.createTempDirectory("ijx")
		try {
			int xexit = new ProcessBuilder("tar", "-C", dir.toString(), "-xzf", archive.toString()).
					inheritIO().start().waitFor()
			if(xexit != 0)
				return
			Path current = Paths.get("/opt/idea")
			if(Files.exists(current))
				current.deleteDir()
			int mexit = new ProcessBuilder("mv",
					Files.list(dir).findFirst().get().toString(), current.toString()).inheritIO().start().waitFor()
		} finally {
			dir.deleteDir()
		}
	}
}
