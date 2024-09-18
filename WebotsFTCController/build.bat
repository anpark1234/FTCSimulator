@echo off
mkdir bin
javac -Xlint -classpath "%WEBOTS_HOME%\lib\controller\java\Controller.jar";robotcore;lib\Jamepad.jar;src;. src\FTCController.java -d bin > build.log 2> build.err
copy src\telemetry.html bin
ant -f build_webots_controller.xml