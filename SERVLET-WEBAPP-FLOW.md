# Servlet Webapp Flow

This project is a Java servlet web application. Tomcat runs the application; the servlet JAR is not a standalone program.

## 1. Project files

- `src\main\java\com\example\HelloServlet.java` is the servlet source.
- `lib\servlet-api.jar` provides the Jakarta Servlet API while compiling.
- `src\main\webapp\WEB-INF\web.xml` registers `com.example.HelloServlet` at `/hello`.
- `src\main\webapp\index.html` is the webapp home page.
- `src\main\webapp\WEB-INF\lib\hello-servlet.jar` contains the compiled servlet class.

The servlet API JAR is for compilation. Tomcat provides the Servlet API at runtime, so do not put `servlet-api.jar` in the webapp's `WEB-INF\lib`.

## 2. Check Java

Open PowerShell in `C:\JavaServlet` and check that Java is available:

```powershell
java -version
javac -version
```

`java` runs Java programs, while `javac` compiles Java source. Tomcat needs a compatible Java runtime; compiling the servlet requires a JDK, which includes `javac`.

## 3. Compile the servlet

Compile the source with the Servlet API JAR on the classpath:

```powershell
New-Item -ItemType Directory -Force "build\classes" | Out-Null
javac -cp "lib\servlet-api.jar" -d "build\classes" "src\main\java\com\example\HelloServlet.java"
```

- `-cp` tells the compiler where to find `jakarta.servlet` classes.
- `-d` sets the output folder. The package name creates `build\classes\com\example\HelloServlet.class`.

## 4. Package the class as a JAR

Create the JAR in the webapp's `WEB-INF\lib` folder:

```powershell
New-Item -ItemType Directory -Force "src\main\webapp\WEB-INF\lib" | Out-Null
& "C:\Program Files\Java\jdk-25\bin\jar.exe" cf "src\main\webapp\WEB-INF\lib\hello-servlet.jar" -C "build\classes" .
```

`jar.exe` is included with the JDK. `cf` means create a JAR file. `-C "build\classes" .` tells the tool to include the contents of that folder, preserving the `com\example` package path.

You can inspect the JAR contents with:

```powershell
& "C:\Program Files\Java\jdk-25\bin\jar.exe" tf "src\main\webapp\WEB-INF\lib\hello-servlet.jar"
```

The listing should include `com/example/HelloServlet.class`.

The JDK path above is an example for this machine. If your JDK is installed elsewhere, use the `jar.exe` from that JDK's `bin` folder.

## 5. Put the webapp in Tomcat

The webapp was placed in Tomcat manually. The exact Tomcat installation path can differ; in the example below, replace `C:\path\to\apache-tomcat` with the folder containing Tomcat's `bin` and `webapps` directories.

Copy the **contents** of `src\main\webapp` into a folder under Tomcat's `webapps`. For example, the deployed layout should be:

```text
<Tomcat folder>\webapps\JavaServlet\
    index.html
    WEB-INF\
        web.xml
        lib\
            hello-servlet.jar
```

If the deployed application still has `WEB-INF\classes\com\example\HelloServlet.class`, remove that specific old class when you want Tomcat to load the servlet from the JAR instead. Do not delete the whole `WEB-INF\classes` folder if it contains other application classes.

## 6. Start Tomcat and open the servlet

Set `CATALINA_HOME` to your Tomcat folder, then start Tomcat from PowerShell:

```powershell
$env:CATALINA_HOME = "C:\path\to\apache-tomcat"
& "$env:CATALINA_HOME\bin\startup.bat"
```

Tomcat deploys applications found under its `webapps` folder. With the example deployment folder name `JavaServlet`, open:

```text
http://localhost:8080/JavaServlet/
```

The home page links to the servlet, whose URL is:

```text
http://localhost:8080/JavaServlet/hello
```

The context path (`JavaServlet`) comes from the deployed folder name; `/hello` comes from the servlet mapping in `web.xml`.

To stop Tomcat:

```powershell
& "$env:CATALINA_HOME\bin\shutdown.bat"
```

This project uses Jakarta Servlet 6.1, so deploy it to a compatible Tomcat version (Tomcat 11).
