#!/bin/bash
APPDIR=$HOME"/SoftwareDevelopment/workspace/github/javaee/taskmanager"
WARDIR=$HOME"/SoftwareDevelopment/workspace/github/javaee/taskmanager/taskmanager-jsf/target"
GLASSFISHDIR=$HOME"/SoftwareDevelopment/jEEServers/glassfish4/bin"

cd $APPDIR
pwd
mvn clean install -DskipTests=true

cd $GLASSFISHDIR

./asadmin --user admin --passwordfile=$APPDIR/scripttools/gfpass deploy --force=true $WARDIR/taskmanagerapp.war

