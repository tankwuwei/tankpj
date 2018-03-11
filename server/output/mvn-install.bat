@echo off

cd..
svn update *


echo ================================================================
echo ========1: engine
echo ========2: common
echo ========3: dbserver
echo ========4: centerserver
echo ========5: gameserver
echo ========6: agent
echo ========7: loginserver
echo ========8: gameclient
echo ========9: all
echo ================================================================


choice /c 123456789 /n /m "    请选择需要打包的工程序号：    "


pause


if %errorlevel%==1 goto engine
if %errorlevel%==2 goto common
if %errorlevel%==3 goto dbserver
if %errorlevel%==4 goto centerserver
if %errorlevel%==5 goto gameserver
if %errorlevel%==6 goto agent
if %errorlevel%==7 goto loginserver
if %errorlevel%==8 goto gameclient
if %errorlevel%==9 goto all



:engine
cd engine
goto mvn-install

:common
cd common
goto mvn-install

:dbserver
cd dbserver
goto mvn-install

:centerserver
cd centerserver
goto mvn-install

:gameserver
cd gameserver
goto mvn-install

:agent
cd agent
goto mvn-install

:loginserver
cd loginserver
goto mvn-install

:gameclient
cd gameclient
goto mvn-install


:all
goto mvn-install



:mvn-install
cmd /k mvn install --settings F:\软件\maven_settings.xml