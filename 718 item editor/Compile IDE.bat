@echo off
echo Compiling IDE
javac -d bin -sourcepath src src/com/alex/io/*.java
javac -d bin -sourcepath src src/com/alex/loaders/images/*.java
javac -d bin -sourcepath src src/com/alex/loaders/items/*.java
javac -d bin -sourcepath src src/com/alex/store/*.java
javac -d bin -sourcepath src src/com/alex/tools/itemsDefsEditor/*.java
javac -d bin -sourcepath src src/com/alex/util/bzip2/*.java
javac -d bin -sourcepath src src/com/alex/util/crc32/*.java
javac -d bin -sourcepath src src/com/alex/util/gzip/*.java
javac -d bin -sourcepath src src/com/alex/util/whirlpool/*.java
javac -d bin -sourcepath src src/com/alex/utils/*.java

javac -d bin -sourcepath src src/org/apache/tools/bzip2/*.java
@echo Finished.
pause