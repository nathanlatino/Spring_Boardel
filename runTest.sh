#!/bin/bash

Xvfb :99 &

/Katalon_Studio_Linux_64-5.7.1/katalon -noSplash  -runMode=console -projectPath="$(pwd)/boardelKatalon/boardelKatalon.prj" -retry=0 -testSuitePath="Test Suites/full_test" -executionProfile="default"  -browserType="Chrome (headless)" -Djava.awt.headless
