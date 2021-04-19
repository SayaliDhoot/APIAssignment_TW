title ApiAssignmentTest
@echo off
cls
echo Start
cd %CD%
call mvn clean test

echo complete

timeout /t 2

START /MAX %CD%\test-output\index.html

echo Press anything to exit 
