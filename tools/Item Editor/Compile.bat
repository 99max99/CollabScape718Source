@echo off
title Compiler
echo starting...
"C:/Program Files (x86)/Java/jdk1.7.0_07/bin/javac.exe" -d bin -cp lib/*; -sourcepath src src/Kjs/*.java                                                                                                                                         
pause