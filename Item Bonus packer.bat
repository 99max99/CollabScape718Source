@echo off
title Packing bonuses..
java -client -Xmx512m -cp bin;data/libs/* com.rs.tools.ItemBonusesPacker
pause