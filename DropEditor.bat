@echo off
title DropEditor
java -client -Xmx512m -cp bin;data/libs/* com.rs.tools.DropEditor
pause